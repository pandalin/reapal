package com.jvv.reapal.facade.req

import com.jvv.reapal.facade.constants.Add
import org.hibernate.validator.constraints.NotEmpty

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 16:57
 * @version 1.0
 */
class ConfirmPayReq extends AbstractReq{

    @NotEmpty(message = "订单号不能为空")
    String order_no
    @NotEmpty(message = "短信验证码不能为空")
    String check_code
    @NotEmpty(message = "用户ID不能为空")
    String member_id
}
