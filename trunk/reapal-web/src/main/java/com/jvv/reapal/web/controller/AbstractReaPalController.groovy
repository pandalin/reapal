package com.jvv.reapal.web.controller

import com.jvv.reapal.common.utils.IpUtils

import javax.servlet.http.HttpServletRequest

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 9:39
 * @version 1.0
 */
abstract class AbstractReaPalController {

    String getIp(HttpServletRequest request) {
        return IpUtils.getIp(request)
    }
}
