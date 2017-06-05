package com.jvv.reapal.dao

import com.jvv.reapal.model.entity.DebitCardOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 13:32
 * @version 1.0
 */
@Repository
interface DebitCardOrderDao extends JpaRepository<DebitCardOrder,Integer>{

    @Query("from DebitCardOrder do where do.order_no = :orderNo AND do.member_id = :memberId")
    DebitCardOrder getDebitCardOrder(@Param("orderNo") String orderNo, @Param("memberId")  String memberId)
    @Query("from DebitCardOrder do where do.order_no = :orderNo AND do.trade_no = :tradeNo")
    DebitCardOrder getDebitCardOrderByOrderNoAndTradeNo(@Param("orderNo") String orderNo, @Param("tradeNo") String tradeNo)
    @Query("from DebitCardOrder do where (do.order_status='completed' OR do.order_status='failed') AND do.notify_status='0'")
    List<DebitCardOrder> getDebitCardOrderUnNotifyed()
    @Query("from DebitCardOrder do where do.order_no = :orderNo")
    DebitCardOrder getDebitCardOrderByOrderNo(@Param("orderNo") String orderNo)
    @Query(value = "select * from reapal_debitcard_order do where do.order_status='processing' AND now() > date_format(DATE_ADD(do.transtime,INTERVAL 5 MINUTE),'%Y-%m-%d %h:%i:%s') AND now() < date_format(DATE_ADD(do.transtime,INTERVAL 1 DAY),'%Y-%m-%d %h:%i:%s')",nativeQuery = true)
    List<DebitCardOrder> getDebitCardOrderUnComplete()
}