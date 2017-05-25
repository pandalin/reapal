package com.jvv.reapal.facade.api

import com.jvv.reapal.facade.info.BankCardInfo
import com.jvv.reapal.facade.info.ConfirmPayInfo
import com.jvv.reapal.facade.info.DebitCardInfo
import com.jvv.reapal.facade.req.*
import com.jvv.reapal.facade.result.BizResult
import com.jvv.reapal.facade.result.SimpleResult

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 15:11
 * @version 1.0
 */
interface ReaPalFacadeApi {

    BizResult<DebitCardInfo> bindCard(DebitCardReq debitCardReq)

    SimpleResult unbindCard(UnBindCardReq unBindCardReq)

    BizResult<DebitCardInfo> payContract(DebitCardReq debitCardReq)

    BizResult<ConfirmPayInfo> confirmPay(ConfirmPayReq confirmPayReq)

    SimpleResult confirmPayNotify(ReaPalNotifyReq confirmPayNotifyReq)

    SimpleResult sendSms(SendSmsReq sendSmsReq)

    BizResult<BankCardInfo> getBindCard(GetBankCardReq getBankCardReq)

    SimpleResult batchToPay(List<BatchToPayDetailReq> detailReqList)

    SimpleResult batchToPayNotify(ReaPalNotifyReq reaPalNotifyReq)
}