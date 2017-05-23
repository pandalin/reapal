package com.jvv.reapal.dao

import com.jvv.reapal.model.entity.ReaPalUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

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
interface ReaPalUserDao extends JpaRepository<ReaPalUser,Integer>{

}