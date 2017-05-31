package com.jvv.reapal.dao

import com.jvv.reapal.model.entity.ReaPalUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

import javax.transaction.Transactional

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/10
 * @time 16:26
 * @version 1.0
 */
@Repository
interface ReaPalUserDao extends JpaRepository<ReaPalUser, Integer> {

    @Modifying
    @Transactional
    @Query("delete ReaPalUser rp where rp.member_id = :member_id")
    int deleteReaPalUser(@Param("member_id") String member_id)
}