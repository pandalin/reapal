package com.jvv.reapal.web.controller

import com.jvv.reapal.facade.api.ReaPalFacadeApi
import com.jvv.reapal.facade.info.BankCardInfo
import com.jvv.reapal.facade.info.ConfirmPayInfo
import com.jvv.reapal.facade.info.DebitCardInfo
import com.jvv.reapal.facade.req.*
import com.jvv.reapal.facade.result.BizResult
import com.jvv.reapal.facade.result.SimpleResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

/**
 * Created by IntelliJ IDEA
 * <p>〈签约〉 </p>
 * 〈签约〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 16:24
 * @version 1.0
 */
@RestController
class ReaPalController extends AbstractReaPalController{

    @Resource
    private ReaPalFacadeApi reaPalFacadeApi

    /**
     * 支付签约
     * @param debitCardReq
     * @param result
     * @return
     */
    @RequestMapping("/reapal/pay/contract")
    ResponseEntity<BizResult<DebitCardInfo>> payContract(HttpServletRequest request, DebitCardReq debitCardReq) {
        debitCardReq.member_ip = super.getIp(request)
        BizResult<DebitCardInfo> result = reaPalFacadeApi.payContract(debitCardReq)
        return new ResponseEntity<BizResult<DebitCardInfo>>(result,HttpStatus.OK)
    }

    /**
     * 储蓄卡签约
     * @param debitCardReq
     * @param result
     * @return
     */
    @RequestMapping("/reapal/debitCardSign")
    ResponseEntity<BizResult<DebitCardInfo>> signCard(HttpServletRequest request, DebitCardReq debitCardReq) {
        debitCardReq.member_ip = super.getIp(request)
        BizResult<DebitCardInfo> result = reaPalFacadeApi.bindCard(debitCardReq)
        return new ResponseEntity<BizResult<DebitCardInfo>>(result,HttpStatus.OK)
    }

    /**
     * 确认支付
     * @param request
     * @param debitCardReq
     * @param partnerId
     * @param sign
     * @return
     */
    @RequestMapping("/reapal/confirmPay")
    ResponseEntity<BizResult<ConfirmPayInfo>> confirmPay(ConfirmPayReq confirmPayReq) {
        BizResult<ConfirmPayInfo> result = reaPalFacadeApi.confirmPay(confirmPayReq)
        return new ResponseEntity<BizResult<ConfirmPayInfo>>(result,HttpStatus.OK)
    }

    @RequestMapping("/reapal/sendSms")
    ResponseEntity<SimpleResult> sendSms(SendSmsReq sendSmsReq) {
        SimpleResult result = reaPalFacadeApi.sendSms(sendSmsReq)
        return new ResponseEntity<SimpleResult>(result,HttpStatus.OK)
    }

    @RequestMapping("/reapal/unBindCard")
    ResponseEntity<SimpleResult> unBindCard(UnBindCardReq unBindCardReq) {
        SimpleResult result = reaPalFacadeApi.unbindCard(unBindCardReq)
        return new ResponseEntity<SimpleResult>(result,HttpStatus.OK)
    }

    @RequestMapping("/reapal/getBindCard")
    ResponseEntity<BizResult<BankCardInfo>> unBindCard(GetBankCardReq getBankCardReq) {
        BizResult<BankCardInfo> result = reaPalFacadeApi.getBindCard(getBankCardReq)
        return new ResponseEntity<BizResult<BankCardInfo>>(result,HttpStatus.OK)
    }

}
