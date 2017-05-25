package com.jvv.reapal.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/24
 * @time 10:43
 * @version 1.0
 */
@Entity
@Table(name = "reapal_topay_detail")
class BatchToPayDetail implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id
    @Column(name = "member_id",nullable = false,length = 64)
    String member_id
    @Column(name = "pay_id",nullable = false,length = 64)
    int pay_id
    @Column(name = "no",length = 64)
    String no
    @Column(name = "bank_no",length = 64)
    String bank_no//银行账号
    @Column(name = "account_name",length = 64)
    String account_name//开户名
    @Column(name = "bank_name",length = 10)
    String bank_name//开户行
    @Column(name = "branch_name",length = 20)
    String branch_name//分行
    @Column(name = "subbranch",length = 20)
    String subbranch//支行
    @Column(name = "pay_type",length = 3)
    String pay_type//公”或者“私”
    @Column(name = "money",length = 12)
    BigDecimal money//金额
    @Column(name = "currency",length = 5)
    String currency//币种
    @Column(name = "province",length = 30)
    String province//省
    @Column(name = "city",length = 30)
    String city//市
    @Column(name = "phone",length = 20)
    String phone//手机号，当所属行业为金融投资类和资金托管类时必须上送
    @Column(name = "certificate",length = 20)
    String certificate//证件类型，当所属行业为金融投资类和资金托管类时必须上送，目前支持：身份证、户口簿、军官证、士兵证、护照、台胞证
    @Column(name = "cert_no",length = 64)
    String cert_no//证件号码，当所属行业为金融投资类和资金托管类时必须上送
    @Column(name = "user_agree_no",length = 20)
    String user_agree_no//用户协议号
    @Column(name = "order_no",length = 64)
    String order_no//商户订单号
    @Column(name = "remark",length = 200)
    String remark//备注
    @Column(name = "order_status",length = 10)
    String order_status//订单状态
    @Column(name = "fail_reason",length = 200)
    String fail_reason//失败原因
    @Column(name = "order_return_time",length = 30)
    String order_return_time
    @Column(name = "notify_id",length = 50)
    String notify_id
    @Column(name = "order_return_no",length = 50)
    String order_return_no
    @Column(name = "notify_status",length = 3)
    int notify_status
}
