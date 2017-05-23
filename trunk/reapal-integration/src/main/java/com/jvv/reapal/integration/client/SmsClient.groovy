package com.jvv.reapal.integration.client

import com.jvv.reapal.integration.dto.SmsDTO
import com.jvv.reapal.integration.resp.SmsResp
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 18:20
 * @version 1.0
 */
@Component("smsClient")
class SmsClient extends AbstractClient {

    SmsResp sendSms(String order_no) {

        SmsDTO smsDTO = new SmsDTO()
        smsDTO.merchant_id = getMerchantId()
        smsDTO.order_no = order_no

        SmsResp smsResp = super.reaPal(URL_SMS, smsDTO, SmsResp.class)

        return smsResp
    }
}
