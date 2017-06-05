package com.jvv.reapal.service.impl

import com.alibaba.fastjson.JSON
import com.jvv.reapal.common.utils.IdCardUtils
import com.jvv.reapal.common.utils.StringUtils
import com.jvv.reapal.dao.DebitCardDao
import com.jvv.reapal.dao.DebitCardOrderDao
import com.jvv.reapal.dao.ReaPalUserDao
import com.jvv.reapal.facade.info.BankCardInfo
import com.jvv.reapal.facade.info.ConfirmPayInfo
import com.jvv.reapal.facade.info.DebitCardInfo
import com.jvv.reapal.facade.result.BizResult
import com.jvv.reapal.facade.result.CommonResultCode
import com.jvv.reapal.facade.result.SimpleResult
import com.jvv.reapal.integration.client.ConfirmPayClient
import com.jvv.reapal.integration.client.DebitCardClient
import com.jvv.reapal.integration.client.SmsClient
import com.jvv.reapal.integration.dto.ConfirmPayDTO
import com.jvv.reapal.integration.dto.ConfirmPayNotifyDTO
import com.jvv.reapal.integration.dto.DebitCardDTO
import com.jvv.reapal.integration.resp.*
import com.jvv.reapal.model.entity.DebitCard
import com.jvv.reapal.model.entity.DebitCardOrder
import com.jvv.reapal.model.entity.ReaPalUser
import com.jvv.reapal.model.enums.OrderNotifyStatus
import com.jvv.reapal.model.enums.OrderStatus
import com.jvv.reapal.service.NotifyService
import com.jvv.reapal.service.ReaPalService
import org.codehaus.groovy.runtime.InvokerHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.util.Assert

import javax.annotation.Resource
import javax.transaction.Transactional

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 9:56
 * @version 1.0
 */
@Service("reaPalService")
class ReaPalServiceImpl implements ReaPalService {

    @Resource
    private DebitCardDao debitCardDao
    @Resource
    private DebitCardOrderDao debitCardOrderDao
    @Resource
    private ReaPalUserDao reaPalUserDao
    @Resource
    private SmsClient smsClient
    @Resource
    private ConfirmPayClient confirmPayClient
    @Resource
    private DebitCardClient debitCardClient
    @Resource
    private NotifyService notifyService

    @Transactional
    DebitCard saveDebitCard(DebitCard debitCard) {
        return debitCardDao.saveAndFlush(debitCard)
    }

    @Override
    @Transactional
    DebitCardOrder saveDebitCardOrder(DebitCardOrder debitCardOrder) {
        return debitCardOrderDao.saveAndFlush(debitCardOrder)
    }

    @Transactional
    DebitCardOrder updateDebitOrder(DebitCardOrder debitCardOrder) {
        return debitCardOrderDao.saveAndFlush(debitCardOrder)
    }

    @Override
    @Async
    void notify(DebitCardOrder debitCardOrder) {
        notifyService.notifyOrder(debitCardOrder)
    }

    @Override
    List<DebitCardOrder> getDebitCardOrderUnNotifyed() {
        return debitCardOrderDao.getDebitCardOrderUnNotifyed()
    }

    @Override
    List<DebitCardOrder> getDebitCardOrderUnComplete() {
        return debitCardOrderDao.getDebitCardOrderUnComplete()
    }

    @Override
    @Transactional
    SimpleResult unBindCard(String bank_id,String member_id) {
        SimpleResult result = new SimpleResult()

        DebitCard debitCard = debitCardDao.findOne(Integer.valueOf(bank_id))
        Assert.notNull(debitCard,"无效的银行卡")
        UnBindCardResp unBindCardResp = debitCardClient.unBindCard(debitCard.bind_id,member_id)
        if (unBindCardResp.success()) {
            reaPalUserDao.deleteReaPalUser(debitCard.member_id)
            debitCardDao.delete(debitCard.id)

            result.setToSuccess()
        } else {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION, unBindCardResp.result_msg)
        }

        return result
    }

    @Override
    BizResult<BankCardInfo> getBindCard(String member_id) {
        BizResult<BankCardInfo> result = new BizResult<BankCardInfo>()
        DebitCard debitCard = debitCardDao.findBindedDebitCardByMemberId(member_id)
        if (debitCard == null) {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,"未绑定银行卡")
            return result
        }
        BankCardInfo bankCardInfo = new BankCardInfo()
        InvokerHelper.setProperties(bankCardInfo,debitCard.properties)
        bankCardInfo.card_no = StringUtils.hideCardNo(bankCardInfo.card_no)
        bankCardInfo.bank_id = debitCard.id
        result.data = bankCardInfo
        result.setToSuccess()
        return result
    }

    @Override
    SimpleResult sendSms(String order_no, String member_id) {

        SimpleResult result = new SimpleResult()

        DebitCardOrder debitCardOrder = debitCardOrderDao.getDebitCardOrder(order_no,member_id)
        if (debitCardOrder == null) {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,"无效的订单")
            return result
        }
        if (!debitCardOrder.order_status.equals(OrderStatus.wait.status)) {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,"只有待支付的订单才能发短信")
            return result
        }

        SmsResp smsResp = smsClient.sendSms(order_no)
        if (smsResp.success()) {
            result.setToSuccess()
        } else {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION, smsResp.result_msg)
        }

        return result
    }

    @Override
    @Transactional
    SimpleResult confirmPayNotify(ConfirmPayNotifyDTO confirmPayNotifyDTO) {
        SimpleResult result = new SimpleResult()
        String decryData = confirmPayClient.decryptData(confirmPayNotifyDTO.encryptkey,confirmPayNotifyDTO.data)
        ConfirmPayResp confirmPayResp = JSON.parseObject(decryData,ConfirmPayResp.class)
        DebitCardOrder debitCardOrder = debitCardOrderDao.getDebitCardOrderByOrderNoAndTradeNo(confirmPayResp.order_no,confirmPayResp.trade_no)
        if (debitCardOrder != null) {
            debitCardOrder.close_date_time = confirmPayResp.close_date_time
            debitCardOrder.timestamp = confirmPayResp.timestamp
            if ("TRADE_FINISHED".equals(confirmPayResp.status)) {
                debitCardOrder.order_status = OrderStatus.completed.status
            } else {
                debitCardOrder.order_status = OrderStatus.failed.status
            }
            updateDebitOrder(debitCardOrder)
            DebitCard debitCard = debitCardDao.findDebitCardByMemberId(debitCardOrder.member_id)
            debitCard.card_status = 1
            debitCardDao.saveAndFlush(debitCard)

            notify(debitCardOrder)
        }
        result.setToSuccess()
        return result
    }

    @Override
    BizResult<ConfirmPayInfo> confirmPay(ConfirmPayDTO confirmPayDTO, String member_id) {
        BizResult<ConfirmPayInfo> result = new BizResult<ConfirmPayInfo>()

        DebitCardOrder debitCardOrder = debitCardOrderDao.getDebitCardOrder(confirmPayDTO.order_no,member_id)
        if (debitCardOrder == null) {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,"无效订单")
            return result
        }
        if (!OrderStatus.wait.status.equals(debitCardOrder.order_status)) {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,"订单状态异常")
            return result
        }
        ConfirmPayResp confirmPayResp = confirmPayClient.confirmPay(confirmPayDTO)
        //接收成功
        if ("3083".equals(confirmPayResp.result_code) || confirmPayResp.success()) {
            debitCardOrder.trade_no = confirmPayResp.trade_no
            debitCardOrder.order_status = OrderStatus.processing.status
            updateDebitOrder(debitCardOrder)
            result.setToSuccess()
        } else {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,confirmPayResp.result_msg)
        }
        return result
    }

    @Override
    BizResult<DebitCardInfo> payContract(DebitCardDTO debitCardDTO) {
        BizResult<DebitCardInfo> result = new BizResult<DebitCardInfo>()

        DebitCard debitCard = debitCardDao.findBindedDebitCardByMemberId(debitCardDTO.getMember_id())
        if (debitCard == null) {
            result.setToFail(CommonResultCode.UNBINDCARD_EXCEPTION,CommonResultCode.UNBINDCARD_EXCEPTION.message)
            return result
        }
        DebitCardResp debitCardResp = debitCardClient.payContract(debitCardDTO,debitCard)
        if (debitCardResp.success()) {

            saveDebitCardOrder(debitCardDTO,debitCard.bind_id)

            DebitCardInfo debitCardInfo = new DebitCardInfo()
            InvokerHelper.setProperties(debitCardInfo,debitCardDTO.properties)
            debitCardInfo.card_no = com.jvv.reapal.common.utils.StringUtils.hideCardNo(debitCard.card_no)
            debitCardInfo.bank_id = debitCard.id
            debitCardInfo.bank_name = debitCardResp.bank_name
            result.data = debitCardInfo

            result.setToSuccess()

            smsClient.sendSms(debitCardDTO.order_no)
        } else {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,debitCardResp.result_msg)
        }
        return result
    }

    @Override
    @Transactional
    BizResult<DebitCardInfo> bindCard(DebitCardDTO debitCardDTO) {
        BizResult<DebitCardInfo> result = new BizResult<DebitCardInfo>()

        DebitCard userDebitCard = debitCardDao.findBindedDebitCardByMemberId(debitCardDTO.member_id)
        if (userDebitCard == null) {
            JwwUserResp jwwUserResp = debitCardClient.getJwwUserByCertNo(debitCardDTO.cert_no,debitCardDTO.member_id,JwwUserResp.class)
            if (jwwUserResp.success()) {
                if (jwwUserResp.realNameState()) {
                    if (!debitCardDTO.owner.equals(jwwUserResp.realName) || !debitCardDTO.cert_no.equals(jwwUserResp.cert_no)) {
                        result.setToFail(CommonResultCode.BIZ_EXCEPRION,"信息与实名信息不符")
                        return result
                    }
                    int age = IdCardUtils.getAgeByIdCard(jwwUserResp.cert_no)
                    if (!(age >= 18 && age <= 63)) {
                        result.setToFail(CommonResultCode.BIZ_EXCEPRION,"限制年龄只能在18-63岁之间")
                        return result
                    }
                }
            } else {
                result.setToFail(CommonResultCode.BIZ_EXCEPRION,jwwUserResp.result_msg)
                return result
            }
            DebitCardResp debitCardResp = debitCardClient.bindCard(debitCardDTO)
            if (debitCardResp.success()) {

                reaPalUserDao.deleteReaPalUser(debitCardDTO.member_id)

                ReaPalUser reaPalUser = new ReaPalUser()
                InvokerHelper.setProperties(reaPalUser,debitCardDTO.properties)
                reaPalUserDao.saveAndFlush(reaPalUser)

                DebitCard debitCard1 = debitCardDao.findDebitCardByCardNoAndMemberId(debitCardDTO.card_no,debitCardDTO.member_id)
                if (debitCard1 != null) {
                    debitCardDao.delete(debitCard1.id)
                }
                DebitCard debitCard = new DebitCard()
                InvokerHelper.setProperties(debitCard,debitCardDTO.properties)
                InvokerHelper.setProperties(debitCard,debitCardResp.properties)
                debitCard.card_status = 0
                saveDebitCard(debitCard)

                saveDebitCardOrder(debitCardDTO,debitCard.bind_id)

                DebitCardInfo debitCardInfo = new DebitCardInfo()
                InvokerHelper.setProperties(debitCardInfo,debitCardDTO.properties)
                debitCardInfo.card_no = com.jvv.reapal.common.utils.StringUtils.hideCardNo(debitCard.card_no)
                debitCardInfo.bank_id = debitCard.id
                debitCardInfo.bank_name = debitCard.bank_name
                result.data = debitCardInfo

                notifyService.notifyJwwUserRealNameState(reaPalUser.member_id,reaPalUser.owner,reaPalUser.cert_no)

                result.setToSuccess()

            } else {
                result.setToFail(CommonResultCode.BIZ_EXCEPRION,debitCardResp.result_msg)
            }
        } else {
            result = payContract(debitCardDTO)
        }

        return result
    }

    @Override
    SimpleResult confirmPaySearch(String order_no) {
        SimpleResult result = new SimpleResult()
        DebitCardOrder debitCardOrder = debitCardOrderDao.getDebitCardOrderByOrderNo(order_no)
        if (debitCardOrder == null) {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,"无效订单")
            return result
        }
        if (OrderStatus.wait.status.equals(debitCardOrder.order_status)) {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,"订单未支付")
            return result
        }
        ConfirmPayDTO confirmPayDTO = new ConfirmPayDTO()
        confirmPayDTO.order_no = order_no
        ConfirmPayResp confirmPayResp = confirmPayClient.confirmPaySearch(confirmPayDTO)
        if (confirmPayResp.success()) {
            debitCardOrder.timestamp = confirmPayResp.timestamp
            debitCardOrder.order_status = confirmPayResp.status
            updateDebitOrder(debitCardOrder)
            notify(debitCardOrder)
        }
        result.setToSuccess()
        return result
    }

    def saveDebitCardOrder(DebitCardDTO debitCardDTO, String bind_id) {
        DebitCardOrder debitCardOrder = new DebitCardOrder()
        InvokerHelper.setProperties(debitCardOrder,debitCardDTO.properties)
        debitCardOrder.bind_id = bind_id
        debitCardOrder.order_status = OrderStatus.wait.status
        debitCardOrder.notify_status = OrderNotifyStatus.W_STATUS.status
        saveDebitCardOrder(debitCardOrder)
    }
}
