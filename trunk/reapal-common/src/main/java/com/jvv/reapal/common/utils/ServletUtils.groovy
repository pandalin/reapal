package com.jvv.reapal.common.utils

import com.google.common.collect.Maps
import org.springframework.util.Assert

import javax.servlet.ServletRequest

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 14:22
 * @version 1.0
 */
class ServletUtils {

    static Map<String, String> getParamMap(ServletRequest request) {
        Assert.notNull(request, "Request must not be null")
        Map<String, String> params = Maps.newHashMap()
        Enumeration<String> enumeration = request.getParameterNames()
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement()
            String[] values = request.getParameterValues(name)
            if (values == null || values.length == 0) {
                continue
            }
            String value = values[0]
            if (value != null) {
                params.put(name, value)
            }
        }
        return params
    }
}
