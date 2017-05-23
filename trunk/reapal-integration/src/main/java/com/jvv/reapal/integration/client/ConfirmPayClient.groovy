package com.jvv.reapal.integration.client

import com.jvv.reapal.integration.dto.ConfirmPayDTO
import com.jvv.reapal.integration.resp.ConfirmPayResp
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 17:05
 * @version 1.0
 */
@Component("confirmPayClient")
class ConfirmPayClient extends AbstractClient {

    ConfirmPayResp confirmPay(ConfirmPayDTO confirmPayDTO) {
        confirmPayDTO.merchant_id = getMerchantId()
        ConfirmPayResp confirmPayResp = super.reaPal(URL_PAY,confirmPayDTO,ConfirmPayResp.class)
        return confirmPayResp
    }

    ConfirmPayResp confirmPaySearch(ConfirmPayDTO confirmPayDTO) {
        confirmPayDTO.merchant_id = getMerchantId()
        ConfirmPayResp confirmPayResp = super.reaPal(URL_ORDER_SEARCH,confirmPayDTO,ConfirmPayResp.class)
        return confirmPayResp
    }


}
