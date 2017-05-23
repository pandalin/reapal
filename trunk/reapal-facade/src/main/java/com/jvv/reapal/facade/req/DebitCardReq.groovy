package com.jvv.reapal.facade.req

import com.jvv.reapal.facade.constants.Add
import com.jvv.reapal.facade.constants.Modify
import org.hibernate.validator.constraints.NotEmpty

import javax.validation.constraints.Min

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 17:08
 * @version 1.0
 */
class DebitCardReq extends AbstractReq implements Serializable {

    @NotEmpty(groups = [Add.class], message = "银行卡号不能为空")
    String card_no

    @NotEmpty(groups = [Add.class], message = "用户姓名不能为空")
    String owner

    @NotEmpty(groups = [Add.class], message = "证件号码不能为空")
    String cert_no

    @NotEmpty(groups = [Add.class], message = "银行预留手机号不能为空")
    String phone

//    @NotEmpty(groups = [Add.class], message = "银行卡类型不能为空")
    Integer bank_card_type

//    @NotEmpty(groups = [Add.class], message = "信用卡背后的3位数字")
    String cvv2

//    @NotEmpty(groups = [Add.class], message = "卡有效期")
    String validthru

    @NotEmpty(groups = [Add.class, Modify.class], message = "商户订单号不能为空")
    String order_no

    @Min(groups = [Add.class, Modify.class], value = 0L, message = "交易金额必须大于0")
    Long total_fee

    @NotEmpty(groups = [Add.class, Modify.class], message = "商品名称不能为空")
    String title

    @NotEmpty(groups = [Add.class, Modify.class], message = "商品描述不能为空")
    String body

    @NotEmpty(groups = [Add.class, Modify.class], message = "用户ID不能为空")
    String member_id

    @NotEmpty(groups = [Add.class, Modify.class], message = "终端类型不能为空")
    String terminal_type

//    @NotEmpty(groups = [Add.class, Modify.class], message = "终端信息不能为空")
    String terminal_info

    @NotEmpty(groups = [Add.class, Modify.class], message = "回调地址不能为空")
    String bg_ret_url

    String member_ip


}
