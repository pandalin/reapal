package com.jvv.reapal.integration.resp

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/23
 * @time 17:09
 * @version 1.0
 */
class BatchToPayDaTa {

    String data
    String notify_id
    String order_no

    String[] splitData() {
        return data.split(",")
    }

    boolean success() {
        return "成功".equals(splitData()[13])
    }

    String result_status_resp() {
        return splitData()[13]
    }

    String result_msg_resp() {
        return splitData()[14]
    }

    String order_no_resp() {
        return splitData()[12]
    }

    String batch_no_resp() {
        return splitData()[1]
    }
}
