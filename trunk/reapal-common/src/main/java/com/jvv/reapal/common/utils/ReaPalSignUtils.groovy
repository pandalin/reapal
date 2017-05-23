package com.jvv.reapal.common.utils

import com.google.common.collect.Lists
import com.jvv.reapal.common.constants.ApiConstants
import com.jvv.reapal.facade.result.CommonResultCode
import groovy.util.logging.Slf4j
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.util.Assert

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 18:17
 * @version 1.0
 */
@Slf4j
class ReaPalSignUtils {

    static String buildSign(Map<String, String> params, String key) {
        Assert.notEmpty(params,CommonResultCode.NULL_ARGUMENT_EXCEPTION.message)
        return DigestUtils.md5Hex(createLinkString(params) + key)
    }

    /**
     * 把数组所有元素，按字母排序，然后按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要签名的参数
     * @return 签名的字符串
     */
    static String createLinkString(Map<String, String> params) {
        List<String> keys = Lists.newArrayList(params.keySet().iterator())
        Collections.sort(keys)
        StringBuilder signStr = new StringBuilder()
        keys.each {key ->
            String value = params.get(key)
            if (org.apache.commons.lang3.StringUtils.isEmpty(value)
                    || key.equalsIgnoreCase(ApiConstants.SIGN)
                    || key.equalsIgnoreCase(ApiConstants.SIGN_TYPE)) {
                return
            }
            signStr.append(key).append("=").append(value).append("&")}
        return signStr.deleteCharAt(signStr.length() - 1).toString()
    }

}
