package com.jvv.reapal.common.utils

import org.apache.commons.lang3.time.DateFormatUtils
import org.joda.time.DateTime

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/24
 * @time 17:22
 * @version 1.0
 */
class DateUtils {

    static String currentStr() {
        return DateFormatUtils.format(current(),DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.pattern + " " + DateFormatUtils.ISO_8601_EXTENDED_TIME_FORMAT.pattern)
    }

    static Date current() {
        return DateTime.now().toDate()
    }
}
