package com.jvv.reapal.facade.req

import org.hibernate.validator.constraints.NotEmpty

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/23
 * @time 16:39
 * @version 1.0
 */
class GetBankCardReq extends AbstractReq{

    @NotEmpty(message = "会员ID不能为空")
    String member_id
}
