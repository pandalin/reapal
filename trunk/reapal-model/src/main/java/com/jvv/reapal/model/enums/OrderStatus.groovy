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
enum OrderStatus {
    wait("wait","等待买家付款"),
    processing("processing","交易处理中"),
    completed("completed","交易完成"),
    failed("failed","交易失败"),
    closed("closed","订单关闭")

    private String status
    private String message

    private OrderStatus(String status,String message) {
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