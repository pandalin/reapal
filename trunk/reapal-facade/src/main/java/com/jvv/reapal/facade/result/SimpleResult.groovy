package com.jvv.reapal.facade.result

import com.jvv.reapal.facade.enums.Status

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 15:12
 * @version 1.0
 */
class SimpleResult implements Serializable{

    private Status status

    private String code

    private String message

    private String description

    void setToSuccess() {
        setToSuccess(CommonResultCode.EXECUTE_SUCCESS)
    }

    void setToSuccess(ResultCodeable resultCode) {
        setToSuccess(resultCode,resultCode.message)
    }

    void setToSuccess(ResultCodeable resultCode,String description) {
        status = Status.SUCCESS
        code = resultCode.code
        message = resultCode.message
        setDescription(description)
    }

    void setToFail(ResultCodeable resultCode) {
        status = Status.FAIL
        code = resultCode.code
        message = resultCode.message
    }

    void setToFail(ResultCodeable resultCode,String description) {
        status = Status.FAIL
        code = resultCode.code
        message = resultCode.message
        setDescription(description)
    }

    Status getStatus() {
        return status
    }

    void setStatus(Status status) {
        this.status = status
    }

    String getCode() {
        return code
    }

    void setCode(String code) {
        this.code = code
    }

    String getMessage() {
        return message
    }

    void setMessage(String message) {
        this.message = message
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }
}
