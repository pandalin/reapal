package com.jvv.reapal.provider

import com.jvv.reapal.common.utils.ValidatorUtils
import com.jvv.reapal.facade.req.AbstractReq

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 10:41
 * @version 1.0
 */
abstract class AbstractProvider {

    void check(AbstractReq abstractReq, Class<?>... groups) {
        ValidatorUtils.check(abstractReq, groups)
    }
}
