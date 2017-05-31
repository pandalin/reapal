package com.jvv.reapal.dao

import com.jvv.reapal.model.entity.DebitCard
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
 * @time 9:58
 * @version 1.0
 */
@Repository
interface DebitCardDao extends JpaRepository<DebitCard,Integer> {

    @Query("from DebitCard rdc where rdc.card_no = :card_no and rdc.member_id = :member_id")
    DebitCard findDebitCardByCardNoAndMemberId(@Param("card_no")String card_no,@Param("member_id") String member_id)
    @Query("from DebitCard rdc where rdc.bind_id = :bind_id AND rdc.member_id = :member_id")
    DebitCard findMemberDebitCardByBindId(@Param("bind_id")String bind_id,@Param("member_id")String member_id)
    @Query("from DebitCard rdc where rdc.member_id = :member_id")
    DebitCard findDebitCardByMemberId(@Param("member_id")String member_id)
    @Query("from DebitCard rdc where rdc.member_id = :member_id and rdc.card_status=1")
    DebitCard findBindedDebitCardByMemberId(@Param("member_id")String member_id)
}