package com.jvv.reapal.facade.req

import com.jvv.reapal.facade.constants.Add
import org.hibernate.validator.constraints.NotEmpty

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/12
 * @time 9:13
 * @version 1.0
 */
class UnBindCardReq extends AbstractReq{

    @NotEmpty(message = "绑定的银行卡ID不能为空")
    String bank_id

    @NotEmpty(message = "用户ID不能为空")
    String member_id
}
