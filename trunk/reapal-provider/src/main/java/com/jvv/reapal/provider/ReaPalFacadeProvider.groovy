package com.jvv.reapal.provider

import com.jvv.reapal.facade.api.ReaPalFacadeApi
import com.jvv.reapal.facade.constants.Add
import com.jvv.reapal.facade.constants.Modify
import com.jvv.reapal.facade.info.ConfirmPayInfo
import com.jvv.reapal.facade.info.DebitCardInfo
import com.jvv.reapal.facade.req.ConfirmPayNotifyReq
import com.jvv.reapal.facade.req.ConfirmPayReq
import com.jvv.reapal.facade.req.DebitCardReq
import com.jvv.reapal.facade.req.SendSmsReq
import com.jvv.reapal.facade.req.UnBindCardReq
import com.jvv.reapal.facade.result.BizResult
import com.jvv.reapal.facade.result.SimpleResult
import com.jvv.reapal.integration.dto.ConfirmPayDTO
import com.jvv.reapal.integration.dto.ConfirmPayNotifyDTO
import com.jvv.reapal.integration.dto.DebitCardDTO
import com.jvv.reapal.service.ReaPalService
import org.codehaus.groovy.runtime.InvokerHelper
import org.springframework.stereotype.Component

import javax.annotation.Resource
import javax.validation.groups.Default

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 15:39
 * @version 1.0
 */
@Component("reaPalFacadeApi")
class ReaPalFacadeProvider extends AbstractProvider implements ReaPalFacadeApi {

    @Resource
    private ReaPalService reaPalService

    @Override
    BizResult<DebitCardInfo> bindCard(DebitCardReq debitCardReq) {
        check(debitCardReq,Default.class,Add.class)
        DebitCardDTO debitCardDTO = new DebitCardDTO()
        InvokerHelper.setProperties(debitCardDTO,debitCardReq.properties)
        return reaPalService.bindCard(debitCardDTO)
    }

    @Override
    SimpleResult unbindCard(UnBindCardReq unBindCardReq) {
        check(unBindCardReq)
        return reaPalService.unBindCard(unBindCardReq.bank_id,unBindCardReq.member_id)
    }

    @Override
    BizResult<DebitCardInfo> payContract(DebitCardReq debitCardReq) {
        check(debitCardReq,Default.class,Modify.class)
        DebitCardDTO debitCardDTO = new DebitCardDTO()
        InvokerHelper.setProperties(debitCardDTO,debitCardReq.properties)
        return reaPalService.payContract(debitCardDTO)
    }

    @Override
    BizResult<ConfirmPayInfo> confirmPay(ConfirmPayReq confirmPayReq) {
        check(confirmPayReq)
        ConfirmPayDTO confirmPayDTO = new ConfirmPayDTO()
        InvokerHelper.setProperties(confirmPayDTO,confirmPayReq.properties)
        return reaPalService.confirmPay(confirmPayDTO,confirmPayReq.member_id)
    }

    @Override
    SimpleResult confirmPayNotify(ConfirmPayNotifyReq confirmPayNotifyReq) {
        ConfirmPayNotifyDTO confirmPayNotifyDTO = new ConfirmPayNotifyDTO()
        InvokerHelper.setProperties(confirmPayNotifyDTO,confirmPayNotifyReq.properties)
        return reaPalService.confirmPayNotify(confirmPayNotifyDTO)
    }

    @Override
    SimpleResult sendSms(SendSmsReq sendSmsReq) {
        return reaPalService.sendSms(sendSmsReq.order_no,sendSmsReq.member_id)
    }
}
