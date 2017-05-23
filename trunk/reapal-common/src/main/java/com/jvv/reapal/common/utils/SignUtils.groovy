package com.jvv.reapal.common.utils

import com.google.common.base.Strings
import com.jvv.reapal.common.constants.ApiConstants
import groovy.util.logging.Slf4j
import org.apache.commons.codec.digest.DigestUtils

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 19:53
 * @version 1.0
 */
@Slf4j
class SignUtils {

    static boolean doSign(String reqSign,Map<String,String> paramMap,String secret) {
        String sign = DigestUtils.md5Hex(doSign(paramMap,secret))
        boolean success = reqSign.equals(sign)
        if (!success) {
            log.error("错误的签名结果[{}],正确签名为[{}]",reqSign,sign)
        }
        return success
    }

    static String doSign(Map<String,String> paramMap,String secret) {
        paramMap.put(ApiConstants.SECURITY_CODE_KEY,secret)
        return doSign(paramMap)
    }

    static String doSign(Map<String,String> paramMap) {
        Map<String,String> sortedMap = new TreeMap<String, String>(paramMap)
        if (sortedMap.containsKey(ApiConstants.SIGN)) {
            sortedMap.remove(ApiConstants.SIGN)
        }
        def signStr
        StringBuilder stringToSign = new StringBuilder()
        if (sortedMap.size() > 0) {
            sortedMap.entrySet().each {v->
                if (Strings.isNullOrEmpty(v.getValue())){
                    return
                }
                stringToSign.append(v.getValue())
            }
            signStr = stringToSign.toString()
        }
        return signStr

    }
}
