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
 * @date 2017/5/4
 * @time 19:40
 * @version 1.0
 */
@Entity
@Table(name = "reapal_merchant")
class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id
    @Column(name = "partner_id",nullable = false,length = 64)
    String partner_id
    @Column(name = "merchant_name",length = 64)
    String merchant_name
    @Column(name = "security_code",nullable = false,length = 64)
    String security_code
    @Column(name = "status",length = 16)
    String status
}
