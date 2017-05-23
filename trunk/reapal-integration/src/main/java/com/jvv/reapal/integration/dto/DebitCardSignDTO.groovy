package com.jvv.reapal.integration.dto

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 13:11
 * @version 1.0
 */
class DebitCardSignDTO extends AbstractDTO implements Serializable{

    String merchant_id

    String member_id

    String bind_id

    String order_no

    String transtime

    String currency = "156"

    int total_fee

    String title

    String body

    String terminal_type
    String terminal_info
    String member_ip
    String seller_email
    String notify_url
    String token_id
}
