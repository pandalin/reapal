package com.jvv.reapal.facade.enums

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 15:15
 * @version 1.0
 */
enum Status {

    /**
     * 成功，处理成功
     */
    SUCCESS("success", "成功"),

    /**
     * 失败，处理失败
     */
    FAIL("fail", "失败"),

    /**
     * 处理中，异步处理中，可以约定回调通知
     */
    PROCESSING("processing", "处理中"),

    /**
     * 未知，需要上层发起查询
     */
    UNKNOWN("UNKNOWN", "未知")

    /**
     * 枚举值
     */
    private final String code

    /**
     * 枚举描述
     */
    private final String message

    /**
     * 构造一个<code>Status</code>枚举对象
     *
     * @param code
     * @param message
     */
    private Status(String code, String message) {
        this.code = code
        this.message = message
    }

    String getCode() {
        return code
    }

    String getMessage() {
        return message
    }
}