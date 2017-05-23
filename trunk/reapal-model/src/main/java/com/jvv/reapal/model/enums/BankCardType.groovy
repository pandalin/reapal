package com.jvv.reapal.model.enums

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/11
 * @time 11:43
 * @version 1.0
 */
enum BankCardType {
    T_DEBIT_CARD(0,"等待买家付款"),
    T_CREDIT_CARD(1,"交易处理中")

    private int code
    private String message

    private BankCardType(int code,String message) {
        this.code = code
        this.message = message
    }

    int getCode() {
        return code
    }

    String getMessage() {
        return message
    }
}