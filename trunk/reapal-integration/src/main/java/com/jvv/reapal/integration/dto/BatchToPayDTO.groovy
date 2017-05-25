package com.jvv.reapal.integration.dto

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/23
 * @time 15:41
 * @version 1.0
 */
class BatchToPayDTO extends AbstractDTO{

    String charset="UTF-8"
    String trans_time
    String notify_url
    String batch_no
    String batch_count
    String pay_type="1"
    String batch_amount
    //序号,银行账户,开户名,开户行,分行,支行,公/私,金额,币种,省,市,手机号,证件类型,证件号,用户协议号,商户订单号,备注
    String content

}
