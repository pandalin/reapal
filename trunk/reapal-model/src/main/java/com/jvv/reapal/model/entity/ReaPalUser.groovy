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
 * @time 11:22
 * @version 1.0
 */
@Entity
@Table(name = "reapal_user")
class ReaPalUser implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "merchant_id",nullable = false,length = 16)
    String merchant_id
    @Column(name = "member_id",length = 64,unique = true)
    String member_id
    @Column(name = "owner",nullable = false,length = 64)
    String owner
    @Column(name = "cert_type",length = 2)
    String cert_type
    @Column(name = "cert_no",length = 64)
    String cert_no
    @Column(name = "phone",length = 16)
    String phone
}
