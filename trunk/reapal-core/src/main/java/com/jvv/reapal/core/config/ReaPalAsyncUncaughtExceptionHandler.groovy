package com.jvv.reapal.core.config

import groovy.util.logging.Slf4j
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler

import java.lang.reflect.Method

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/27
 * @time 10:50
 * @version 1.0
 */
@Slf4j
class ReaPalAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler{

    @Override
    void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Unexpected error occurred invoking async method {}",method,ex)
    }
}
