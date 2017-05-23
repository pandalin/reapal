package com.jvv.reapal.common.utils

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/10
 * @time 17:07
 * @version 1.0
 */
class StringUtils {

    static String hideCardNo(String card_no) {
        return card_no.substring(0,4) + "****" + card_no.substring(card_no.length() - 4,card_no.length())
    }

    static String getUUId() {
        return UUID.randomUUID().toString().replaceAll("-","")
    }
}
