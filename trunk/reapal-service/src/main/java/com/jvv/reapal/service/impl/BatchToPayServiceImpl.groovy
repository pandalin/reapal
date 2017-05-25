package com.jvv.reapal.service.impl

import com.alibaba.fastjson.JSON
import com.google.common.collect.Lists
import com.jvv.reapal.common.utils.DateUtils
import com.jvv.reapal.dao.BatchToPayDao
import com.jvv.reapal.dao.BatchToPayDetailDao
import com.jvv.reapal.facade.req.BatchToPayDetailReq
import com.jvv.reapal.facade.result.CommonResultCode
import com.jvv.reapal.facade.result.SimpleResult
import com.jvv.reapal.integration.client.BatchToPayClient
import com.jvv.reapal.integration.dto.BatchToPayDTO
import com.jvv.reapal.integration.dto.BatchToPaySearchDTO
import com.jvv.reapal.integration.dto.ConfirmPayNotifyDTO
import com.jvv.reapal.integration.resp.BatchToPayDaTa
import com.jvv.reapal.integration.resp.BatchToPayResp
import com.jvv.reapal.integration.resp.BatchToPaySearchResp
import com.jvv.reapal.integration.resp.JwwUserResp
import com.jvv.reapal.model.entity.BatchToPay
import com.jvv.reapal.model.entity.BatchToPayDetail
import com.jvv.reapal.model.enums.BatchToPayOrderStatus
import com.jvv.reapal.service.BatchToPayService
import com.jvv.reapal.service.NotifyService
import org.apache.commons.collections.CollectionUtils
import org.codehaus.groovy.runtime.InvokerHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.annotation.Resource

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/24
 * @time 9:43
 * @version 1.0
 */
@Service("batchToPayService")
class BatchToPayServiceImpl implements BatchToPayService{

    @Resource
    private BatchToPayDao batchToPayDao
    @Resource
    private BatchToPayDetailDao batchToPayDetailDao
    @Resource
    private BatchToPayClient batchToPayClient
    @Resource
    private NotifyService notifyService

    @Override
    @Transactional
    SimpleResult batchToPay(List<BatchToPayDetailReq> detailReqList) {

        SimpleResult result = new SimpleResult()

        BatchToPayDTO batchToPayDTO = new BatchToPayDTO()

        StringBuffer content= new StringBuffer()

        batchToPayDTO.batch_count = detailReqList.size().toString()
        BigDecimal amount = BigDecimal.ZERO
        def b = false
        String errorMsg
        String bg_ret_url
        for (it in detailReqList) {
            JwwUserResp jwwUserResp = batchToPayClient.getJwwUserInfo(it.member_id,JwwUserResp.class)
            if (jwwUserResp.success()) {
                if (!jwwUserResp.realNameState()) {
                    b = true
                    errorMsg = "用户[" + it.member_id + "]未实名"
                    break
                }
                it.cert_no = jwwUserResp.cert_no
                it.account_name = jwwUserResp.realName
            } else {
                b = true
                errorMsg = jwwUserResp.result_msg
                break

            }
            it.no = it.order_no
            bg_ret_url = it.bg_ret_url
            content.append(it.toString()).append("|")
            amount = amount.add(it.money)
            batchToPayDTO.batch_amount = amount.toString()
        }
        if (b) {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,errorMsg)
            return result
        }

        content = content.deleteCharAt(content.length()-1)
        batchToPayDTO.content = content.toString()

        BatchToPayResp batchToPayResp = batchToPayClient.batchToPay(batchToPayDTO)
        if (batchToPayResp.success()) {

            BatchToPay batchToPay = new BatchToPay()
            InvokerHelper.setProperties(batchToPay,batchToPayDTO.properties)
            batchToPay.bg_ret_url = bg_ret_url
            batchToPay = batchToPayDao.save(batchToPay)

            List<BatchToPayDetail> details = Lists.newArrayList()
            BatchToPayDetail payDetail
            detailReqList.each {
                payDetail = new BatchToPayDetail()
                InvokerHelper.setProperties(payDetail,it.properties)
                payDetail.pay_id = batchToPay.id
                payDetail.member_id = it.member_id
                payDetail.order_status = BatchToPayOrderStatus.wait.status
                details.add(payDetail)
            }
            batchToPayDetailDao.save(details)

            result.setToSuccess()
        } else {
            result.setToFail(CommonResultCode.BIZ_EXCEPRION,batchToPayResp.result_msg)
        }

        return result
    }

    @Override
    SimpleResult batchToPayNotify(ConfirmPayNotifyDTO confirmPayNotifyDTO) {
        SimpleResult result = new SimpleResult()
        String decryData = batchToPayClient.decryptData(confirmPayNotifyDTO.encryptkey,confirmPayNotifyDTO.data)
        BatchToPayDaTa batchToPayDaTa = JSON.parseObject(decryData,BatchToPayDaTa.class)

        String order_no = batchToPayDaTa.order_no_resp()
        String batch_no = batchToPayDaTa.batch_no_resp()

        BatchToPay batchToPay = batchToPayDao.findBatchToPayByBatchNo(batch_no)
        BatchToPayDetail batchToPayDetail = batchToPayDetailDao.findBatchToPayDetailByPayIdAndOrderNo(batchToPay.id,order_no)

        if (!batchToPayDaTa.success()) {
            batchToPayDetail.fail_reason = batchToPayDaTa.result_msg_resp()
        }
        batchToPayDetail.order_status = batchToPayDaTa.result_status_resp()
        batchToPayDetail.order_return_time = DateUtils.currentStr()
        batchToPayDetail.order_return_no = batchToPayDaTa.order_no
        batchToPayDetail.notify_id = batchToPayDaTa.notify_id
        batchToPayDetailDao.saveAndFlush(batchToPayDetail)

        notifyService.notifyBatchToPay(batchToPay,batchToPayDetail)

        result.setToSuccess()
        return result
    }

    @Override
    def batchToPaySearch(BatchToPay batchToPay,BatchToPayDetail batchToPayDetail) {

        BatchToPaySearchDTO batchToPaySearchDTO = new BatchToPaySearchDTO()
        batchToPaySearchDTO.batch_no = batchToPay.batch_no
        batchToPaySearchDTO.trans_time = batchToPay.trans_time
        batchToPaySearchDTO.detail_no = batchToPayDetail.no
        BatchToPaySearchResp batchToPaySearchResp = batchToPayClient.batchToPaySearch(batchToPaySearchDTO)
        if (batchToPaySearchResp.success()) {
            batchToPayDetail.order_status = batchToPaySearchResp.result_status_resp()
            batchToPayDetail.fail_reason = batchToPaySearchResp.result_msg_resp()
            batchToPayDetail = batchToPayDetailDao.saveAndFlush(batchToPayDetail)
            notifyService.notifyBatchToPay(batchToPay,batchToPayDetail)
        }

    }
}
