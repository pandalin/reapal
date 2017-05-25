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
class BatchToPaySearchResp {

    String batch_no
    String charset
    String detail_no
    String content

    String merchant_id

    String result_code

    String result_msg

    boolean success() {
        return "0001".equals(result_code)
    }

    String[] splitData() {
        return content.split(",")
    }

    String result_status_resp() {
        return splitData()[11]
    }

    String result_msg_resp() {
        return splitData()[12]
    }

}
