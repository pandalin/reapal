package com.jvv.reapal.core.exception

import com.jvv.reapal.facade.result.CommonResultCode
import com.jvv.reapal.facade.result.SimpleResult
import groovy.util.logging.Slf4j
import org.hibernate.HibernateException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.NoHandlerFoundException

/**
 * Created by IntelliJ IDEA
 * <p>〈全局异常处理〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 18:09
 * @version 1.0
 */
@ControllerAdvice
@Slf4j
class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    ResponseEntity<SimpleResult> exception(Exception exception) {

        SimpleResult result = new SimpleResult()
        if (exception instanceof SignException) {
            log.error("签名异常", exception)
            result.setToFail(CommonResultCode.ILLEGAL_ARGUMENT_EXCEPTION, "签名异常")
        } else if (exception instanceof IllegalArgumentException) {
            log.error("参数异常", exception)
            result.setToFail(CommonResultCode.ILLEGAL_ARGUMENT_EXCEPTION, exception.message)
        } else if (exception.cause instanceof HibernateException) {
            log.error("数据库错误", exception)
            result.setToFail(CommonResultCode.DB_EXCEPRION, CommonResultCode.DB_EXCEPRION.message)
        } else if (exception instanceof NullPointerException) {
            log.error("空指针异常", exception)
            result.setToFail(CommonResultCode.SYS_EXCEPRION, CommonResultCode.SYS_EXCEPRION.message)
        } else if (exception instanceof RuntimeException) {
            log.error("业务错误", exception)
            result.setToFail(CommonResultCode.BIZ_EXCEPRION, exception.message)
        } else if (exception instanceof NoHandlerFoundException) {
            log.error("非法请求", exception)
            result.setToFail(CommonResultCode.UNKNOWN_EXCEPRION, CommonResultCode.UNKNOWN_EXCEPRION.message)
        } else {
            log.error("系统异常", exception)
            result.setToFail(CommonResultCode.SYS_EXCEPRION, exception.message)
        }

        return new ResponseEntity<SimpleResult>(result, HttpStatus.OK)
    }
}
