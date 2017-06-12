package com.jvv.reapal.model.entity

import javax.persistence.*

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 13:25
 * @version 1.0
 */

@Entity
@Table(name = "reapal_debitcard_order")
class DebitCardOrder implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "merchant_id",nullable = false,length = 16)
    String merchant_id
    @Column(name = "member_id",length = 64)
    String member_id
    @Column(name = "bind_id",length = 64)
    String bind_id
    @Column(name = "order_no",nullable = false,unique = true,length = 100)
    String order_no
    @Column(name = "total_fee",length = 16)
    int total_fee
    @Column(name = "transtime",length = 30)
    String transtime
    @Column(name = "title",length = 256)
    String title
    @Column(name = "body",length = 512)
    String body
    @Column(name = "terminal_type",length = 64)
    String terminal_type
    @Column(name = "terminal_info",length = 100)
    String terminal_info
    @Column(name = "member_ip",length = 30)
    String member_ip
    @Column(name = "notify_url",length = 200)
    String notify_url
    @Column(name = "token_id",length = 64)
    String token_id

    @Column(name = "trade_no",length = 50)
    String trade_no//融宝交易流水号
    @Column(name = "close_date_time",length = 30)
    String close_date_time//支付完成时间
    @Column(name = "order_return_time",length = 30)
    String timestamp
    @Column(name = "order_status",length = 20)
    String order_status
    @Column(name = "bg_ret_url",length = 200)
    String bg_ret_url
    @Column(name = "notify_status",length = 1)
    String notify_status//0-未通知,1-已通知
    @Column(name = "fail_reason",length = 500)
    String fail_reason
}
