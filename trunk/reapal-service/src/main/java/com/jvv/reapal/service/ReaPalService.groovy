package com.jvv.reapal.service

import com.jvv.reapal.facade.info.BankCardInfo
import com.jvv.reapal.facade.info.ConfirmPayInfo
import com.jvv.reapal.facade.info.DebitCardInfo
import com.jvv.reapal.facade.result.BizResult
import com.jvv.reapal.facade.result.SimpleResult
import com.jvv.reapal.integration.dto.ConfirmPayDTO
import com.jvv.reapal.integration.dto.ConfirmPayNotifyDTO
import com.jvv.reapal.integration.dto.DebitCardDTO
import com.jvv.reapal.model.entity.DebitCardOrder

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 9:55
 * @version 1.0
 */
interface ReaPalService {

    void notify(DebitCardOrder debitCardOrder)

    List<DebitCardOrder> getDebitCardOrderUnNotifyed()

    List<DebitCardOrder> getDebitCardOrderUnComplete()

    SimpleResult sendSms(String order_no, String member_id)

    SimpleResult confirmPayNotify(ConfirmPayNotifyDTO confirmPayNotifyDTO)

    BizResult<ConfirmPayInfo> confirmPay(ConfirmPayDTO confirmPayDTO, String member_id)

    BizResult<DebitCardInfo> payContract(DebitCardDTO debitCardDTO)

    BizResult<DebitCardInfo> bindCard(DebitCardDTO debitCardDTO)

    SimpleResult confirmPaySearch(String order_no)

    DebitCardOrder saveDebitCardOrder(DebitCardOrder debitCardOrder)

    SimpleResult unBindCard(String bank_id,String member_id)

    BizResult<BankCardInfo> getBindCard(String member_id)

}