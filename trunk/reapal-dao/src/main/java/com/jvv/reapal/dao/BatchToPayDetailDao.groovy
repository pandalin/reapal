package com.jvv.reapal.dao

import com.jvv.reapal.model.entity.BatchToPayDetail
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
 * @date 2017/5/24
 * @time 11:13
 * @version 1.0
 */
@Repository
interface BatchToPayDetailDao extends JpaRepository<BatchToPayDetail, Integer> {

    @Query("from BatchToPayDetail bd where bd.pay_id = :pay_id AND bd.order_no = :order_no")
    BatchToPayDetail findBatchToPayDetailByPayIdAndOrderNo(
            @Param("pay_id") int pay_id, @Param("order_no") String order_no)

    @Query("from BatchToPayDetail bd where bd.notify_status = 0")
    List<BatchToPayDetail> findBatchToPayDetailUnNotify()

    @Query("from BatchToPayDetail bd where bd.order_status = '提现中' ")
    List<BatchToPayDetail> findBatchToPayDetailUnReturn()
}