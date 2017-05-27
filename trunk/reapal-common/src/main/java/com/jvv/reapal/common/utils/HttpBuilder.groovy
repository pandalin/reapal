package com.jvv.reapal.common.utils

import com.jvv.reapal.facade.result.CommonResultCode
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.springframework.http.MediaType

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/11
 * @time 15:31
 * @version 1.0
 */
class HttpBuilder {

    static http = new HTTPBuilder()

    static String post(url,Map<String,String> params) {

        http.request( url, Method.POST, ContentType.TEXT ) { req ->
            uri.query = params
            headers.Accept = MediaType.APPLICATION_JSON_UTF8_VALUE
            response.success = { resp, reader ->
                return reader.text
            }
            response.failure = { resp ->
                throw new RuntimeException(CommonResultCode.NET_EXCEPRION.message)
            }
        }
    }

    static String postJson(url,jsonStr) {
        http.request( url, Method.POST, ContentType.JSON ) {
            headers.'Content-Type' = 'application/json'
            requestContentType = ContentType.JSON
            body = jsonStr
            response.success = { resp, json ->
                return json
            }
            response.failure = { resp ->
                throw new RuntimeException(CommonResultCode.NET_EXCEPRION.message)
            }
        }
    }
}
