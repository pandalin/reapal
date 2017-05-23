package com.jvv.reapal.model.enums

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 19:05
 * @version 1.0
 */
enum OrderNotifyStatus {
    W_STATUS(0,"未通知"),
    S_STATUS(1,"已通知"),

    private int status
    private String message

    private OrderNotifyStatus(int status, String message) {
        this.status = status
        this.message = message
    }

    int getStatus() {
        return status
    }

    String getMessage() {
        return message
    }
}