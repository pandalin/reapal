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
 * @date 2017/5/8
 * @time 11:13
 * @version 1.0
 */
@Entity
@Table(name = "reapal_debit_card")
class DebitCard implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id
    @Column(name = "member_id",nullable = false,length = 64)
    String member_id
    @Column(name = "card_no",nullable = false,length = 64)
    String card_no
    @Column(name = "bank_card_type")
    int bank_card_type
    @Column(name = "bind_id",length = 64)
    String bind_id
    @Column(name = "bank_name",length = 64)
    String bank_name
    @Column(name = "bank_code",length = 100)
    String bank_code
    @Column(name = "phone",length = 16)
    String phone
    @Column(name = "certificate",length = 1)
    String certificate//0-未认证，1-已认证
    @Column(name = "cvv2",length = 3)
    String cvv2//信用卡背后的3位数字
    @Column(name = "validthru",length = 10)
    String validthru//信用卡有效期
    @Column(name = "card_status",length = 5)
    int card_status
}
