package com.jvv.reapal.dao

import com.jvv.reapal.model.entity.Merchant
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
 * @date 2017/5/5
 * @time 9:43
 * @version 1.0
 */
@Repository
interface MerchantDao extends JpaRepository<Merchant,Integer> {

    @Query("from Merchant r where r.partner_id=:partner_id")
    Merchant findMerchantByPartnerId(@Param("partner_id")String partner_id)
}