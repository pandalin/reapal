package com.jvv.reapal.common.utils

import org.apache.commons.lang3.time.DateFormatUtils
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormatterBuilder
import org.springframework.util.Assert

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/16
 * @time 14:48
 * @version 1.0
 */
class IdCardUtils {

    static LocalDateTime getBirthDay(String idCard) {
        Assert.notNull(idCard,"身份证不能为空")
        int length = idCard.length()
        String birthDay
        if (length == 15) {
            birthDay = "19" + idCard.substring(6,12)
        } else {
            birthDay = idCard.substring(6,14)
        }
        LocalDateTime dateTime = DateTimeFormat.forPattern("yyyyMMdd").parseLocalDateTime(birthDay)
        return dateTime
    }

    static int getAgeByIdCard(String idCard) {
        DateTime birthDay = getBirthDay(idCard)
        int year = birthDay.year
        int month = birthDay.monthOfYear
        int day = birthDay.dayOfMonth

        DateTime now = DateTime.now()
        int yearNow = now.year
        int monthNow = now.monthOfYear
        int dayNow = now.dayOfMonth

        int age = yearNow - year
        if (monthNow <= month && monthNow == month && dayNow < day || monthNow < month) {
            age--
        }
        return age
    }

}
