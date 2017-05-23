package com.jvv.reapal.integration.dto
/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 16:43
 * @version 1.0
 */
class DebitCardDTO extends AbstractDTO implements Serializable {

    String merchant_id

    String card_no

    String owner

    String cert_type = "01"

    String cert_no

    String phone

    String cvv2

    String validthru

    String order_no

    String transtime

    String currency = "156"

    int total_fee

    String title

    String body

    String member_id
    String terminal_type
    String terminal_info
    String member_ip
    String seller_email
    String notify_url
    String token_id

    String bg_ret_url
    int bank_card_type

}
