package com.jvv.reapal.facade.req

import org.hibernate.validator.constraints.NotEmpty

import javax.validation.constraints.DecimalMin

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/24
 * @time 9:26
 * @version 1.0
 */
class BatchToPayDetailReq extends AbstractReq {
    String no
    @NotEmpty(message = "银行账号不能为空")
    String bank_no//银行账号
//    @NotEmpty(message = "开户名不能为空")
    String account_name//开户名
    @NotEmpty(message = "开户行不能为空")
    String bank_name//开户行
    String branch_name//分行
    String subbranch//支行
    String pay_type = '私'//公”或者“私”
    @DecimalMin(value = "1.00", message = "金额不能为空")
    BigDecimal money//金额
    String currency = 'CNY'//币种
    String province//省
    String city//市
//    @NotEmpty(message = "手机号不能为空")
    String phone//手机号，当所属行业为金融投资类和资金托管类时必须上送
//    @NotEmpty(message = "证件类型不能为空")
    String certificate = "身份证"//证件类型，当所属行业为金融投资类和资金托管类时必须上送，目前支持：身份证、户口簿、军官证、士兵证、护照、台胞证
//    @NotEmpty(message = "证件号码不能为空")
    String cert_no//证件号码，当所属行业为金融投资类和资金托管类时必须上送
    String user_agree_no//用户协议号
    @NotEmpty(message = "商户订单号不能为空")
    String order_no//商户订单号
    @NotEmpty(message = "备注不能为空")
    String remark//备注
    @NotEmpty(message = "用户ID不能为空")
    String member_id
    @NotEmpty(message = "回调地址不能为空")
    String bg_ret_url

    @Override
    String toString() {

        StringBuilder sb = new StringBuilder()
        sb.append(no).append(",")
        sb.append(bank_no ? bank_no : '').append(",")
        sb.append(account_name ? account_name : '').append(",")
        sb.append(bank_name ? bank_name : '').append(",")
        sb.append(branch_name ? branch_name : '').append(",")
        sb.append(subbranch ? subbranch : '').append(",")
        sb.append(pay_type ? pay_type : '').append(",")
        sb.append(money ? money : '').append(",")
        sb.append(currency ? currency : '').append(",")
        sb.append(province ? province : '').append(",")
        sb.append(city ? city : '').append(",")
        sb.append(phone ? phone : '').append(",")
        sb.append(certificate ? certificate : '').append(",")
        sb.append(cert_no ? cert_no : '').append(",")
        sb.append(user_agree_no ? user_agree_no : '').append(",")
        sb.append(order_no ? order_no : '').append(",")
        sb.append(remark ? remark : '')

        return sb.toString()
    }
}