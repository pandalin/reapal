package com.jvv.reapal.common.utils

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
class IpUtils {

    static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for")
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP")
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("X-Real-IP")
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr()
        }

        // 多级反向代理检测
        if(ip != null && ip.indexOf(",") > 0){
            ip = ip.trim().split(",")[0]
        }

        return ip
    }
}
