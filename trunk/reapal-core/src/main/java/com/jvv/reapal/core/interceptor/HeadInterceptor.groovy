package com.jvv.reapal.core.interceptor

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by IntelliJ IDEA
 * <p>〈〉 </p>
 * 〈〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 19:05
 * @version 1.0
 */
@Component("headInterceptor")
@Slf4j
class HeadInterceptor implements HandlerInterceptor{

    private ThreadLocal<Long> requestTime = new ThreadLocal<>()

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        log.info("===================>请求地址{}开始",request.getRequestURI())
        requestTime.set(System.currentTimeMillis())
        return true
    }

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        long statTime = requestTime.get().longValue()
        long endTime = System.currentTimeMillis()
        log.info("===================>请求地址{}结束,耗时===>{}ms",request.getRequestURI(),endTime - statTime)
    }
}
