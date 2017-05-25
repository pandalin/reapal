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
 * @time 10:37
 * @version 1.0
 */
@Entity
@Table(name = "reapal_topay")
class BatchToPay implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id
    @Column(name = "batch_no",nullable = false,length = 64,unique = true)
    String batch_no
    @Column(name = "batch_count",nullable = false,length = 10)
    String batch_count
    @Column(name = "batch_amount",nullable = false,length = 12)
    String batch_amount
    @Column(name = "trans_time",nullable = false,length = 30)
    String trans_time
    @Column(name = "pay_type",length = 1)
    String pay_type
    @Column(name = "notify_url",length = 200)
    String notify_url
    @Column(name = "bg_ret_url",length = 200)
    String bg_ret_url
}
