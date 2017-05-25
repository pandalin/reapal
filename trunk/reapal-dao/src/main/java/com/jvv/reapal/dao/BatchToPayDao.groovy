package com.jvv.reapal.dao

import com.jvv.reapal.model.entity.BatchToPay
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
 * @time 11:12
 * @version 1.0
 */
@Repository
interface BatchToPayDao extends JpaRepository<BatchToPay,Integer>{

    @Query("from BatchToPay btp where btp.batch_no = :batch_no")
    BatchToPay findBatchToPayByBatchNo(@Param("batch_no") String batch_no)
}