package com.jvv.reapal.model.enums

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/25
 * @time 11:58
 * @version 1.0
 */
enum BatchToPayOrderStatus {
    wait("提现中","等待买家付款"),

    private String status
    private String message

    private BatchToPayOrderStatus(String status,String message) {
        this.status = status
        this.message = message
    }

    String getStatus() {
        return status
    }

    String getMessage() {
        return message
    }
}