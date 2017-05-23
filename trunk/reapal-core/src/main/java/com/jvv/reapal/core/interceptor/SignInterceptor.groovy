package com.jvv.reapal.core.interceptor

import com.google.common.base.Strings
import com.jvv.reapal.common.constants.ApiConstants
import com.jvv.reapal.common.utils.ServletUtils
import com.jvv.reapal.common.utils.SignUtils
import com.jvv.reapal.core.exception.SignException
import com.jvv.reapal.facade.result.CommonResultCode
import com.jvv.reapal.model.entity.Merchant
import com.jvv.reapal.service.MerchantService
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by IntelliJ IDEA
 * <p>〈验签〉 </p>
 * 〈验签〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 19:05
 * @version 1.0
 */
@Component("signInterceptor")
class SignInterceptor extends HandlerInterceptorAdapter{

    @Resource
    private MerchantService merchantService

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String partnerId = request.getParameter(ApiConstants.PARTNERID)
        String sign = request.getParameter(ApiConstants.SIGN)
        if (Strings.isNullOrEmpty(partnerId) || Strings.isNullOrEmpty(sign)) {
            throw new IllegalArgumentException(CommonResultCode.NULL_ARGUMENT_EXCEPTION.message)
        }
        Merchant merchant = merchantService.loadFromCache(partnerId)
        if (merchant == null) {
            throw new IllegalArgumentException(CommonResultCode.ILLEGAL_ARGUMENT_EXCEPTION.message)
        }

        String security_code = merchant.security_code

        Map<String,String> paramMap = ServletUtils.getParamMap(request)
        boolean success = SignUtils.doSign(sign,paramMap,security_code)
        if (!success) {
            throw new SignException()
        }

        return success
    }

}
