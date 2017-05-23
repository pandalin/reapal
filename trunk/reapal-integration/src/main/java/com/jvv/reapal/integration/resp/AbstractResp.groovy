package com.jvv.reapal.integration.resp

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 17:00
 * @version 1.0
 */
class AbstractResp {

    String merchant_id

    String result_code

    String result_msg

    boolean success() {
        return "0000".equals(result_code)
    }
}
