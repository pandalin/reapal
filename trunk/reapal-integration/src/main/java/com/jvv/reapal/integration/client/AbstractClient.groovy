package com.jvv.reapal.integration.client

import com.alibaba.fastjson.JSON
import com.google.common.collect.Maps
import com.jvv.reapal.common.constants.ApiConstants
import com.jvv.reapal.common.utils.Decipher
import com.jvv.reapal.common.utils.HttpBuilder
import com.jvv.reapal.common.utils.ReaPalSignUtils
import com.jvv.reapal.integration.dto.AbstractDTO
import groovy.util.logging.Slf4j
import org.apache.commons.beanutils.BeanUtils
import org.springframework.beans.factory.annotation.Value

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 17:47
 * @version 1.0
 */
@Slf4j
abstract class AbstractClient {

    @Value('${reapal.version}')
    private String version
    @Value('${reapal.merchantId}')
    private String merchantId
    @Value('${reapal.seller_email}')
    private String seller_email
    @Value('${reapal.appKey}')
    private String appKey
    @Value('${reapal.pay-url}')
    private String payUrl
    @Value('${reapal.topay-url}')
    private String toPayUrl
    @Value('${reapal.pubKeyUrl}')
    private String pubKeyUrl
    @Value('${reapal.privateKey}')
    private String privateKey
    @Value('${reapal.privateKeyPwd}')
    private String privateKeyPwd
    @Value('${notify.url}')
    private String notifyUrl
    @Value('${jww.url}')
    private String jwwUrl

    protected static final String URL_DEBITCARD = "/fast/debit/portal"
    protected static final String URL_CREDIT_CARD = "/fast/credit/portal"
    protected static final String URL_DEBITCARD_SIGN = "/fast/bindcard/portal"
    protected static final String URL_UNBIND_DEBITCARD = "/fast/cancle/bindcard"

    protected static final String URL_PAY_NOTIFY = "/notify/confirmPay"
    protected static final String URL_PAY = "/fast/pay"
    protected static final String URL_SMS = "/fast/sms"
    protected static final String URL_ORDER_SEARCH = "/fast/search"

    protected static final String URL_JWW_USER_REALNAME = "/m/getUserRealNameState"
    protected static final String URL_JWW_USER_CERTNO = "/m/getUserByCertNo"
    protected static final String URL_UPDATE_JWW_USER_REALNAME = "/m/updateUserRealNameState"


    protected static final String URL_BATCHTOPAY = "/agentpay/pay"
    protected static final String URL_TOPAY_NOTIFY = "/notify/batchToPay"

    String getSeller_email() {
        return seller_email
    }

    String getNotifyUrl() {
        return notifyUrl
    }

    String getMerchantId() {
        return merchantId
    }

    public <T> T batchToPay(AbstractDTO abstractDTO, Class<T> respClass) {

        Map<String, String> requestMap = BeanUtils.describe(abstractDTO)
        requestMap.remove("class")
        requestMap.remove("metaClass")
        String sign = ReaPalSignUtils.buildSign(requestMap, appKey)

        requestMap.put(ApiConstants.SIGN, sign)
        requestMap.put(ApiConstants.SIGN_TYPE, ApiConstants.MD5)
        String json = JSON.toJSONString(requestMap)
        log.info("===================>批量代付请求参数,{}",json)
        Map<String, String> maps = Decipher.encryptData(json, pubKeyUrl)
        maps.put("merchant_id",merchantId)
        maps.put("version",version)
        String returnStr = post(toPayUrl + URL_BATCHTOPAY, maps)
        String data = Decipher.decryptData(returnStr, privateKey, privateKeyPwd)
        return JSON.parseObject(data, respClass)
    }

    public <T> T reaPal(String url, AbstractDTO abstractDTO, Class<T> respClass) {

        Map<String, String> requestMap = BeanUtils.describe(abstractDTO)
        requestMap.remove("class")
        requestMap.remove("metaClass")
        requestMap.remove("bg_ret_url")
        requestMap.remove("bank_card_type")
        String sign = ReaPalSignUtils.buildSign(requestMap, appKey)

        requestMap.put(ApiConstants.SIGN, sign)
        requestMap.put(ApiConstants.SIGN_TYPE, ApiConstants.MD5)
        String json = JSON.toJSONString(requestMap)
        log.info("===================>请求参数,{}",json)
        Map<String, String> maps = Decipher.encryptData(json, pubKeyUrl)
        maps.put("merchant_id",merchantId)
        String returnStr = post(payUrl + url, maps)
        String data = Decipher.decryptData(returnStr, privateKey, privateKeyPwd)
        return JSON.parseObject(data, respClass)
    }

    public <T> T getJwwUserByCertNo(String cardNo,String userId, Class<T> respClass) {
        Map<String, String> requestMap = Maps.newHashMap()
        requestMap.put("certNo", cardNo)
        requestMap.put("userId", userId)
        return post(jwwUrl + URL_JWW_USER_CERTNO, requestMap,respClass)
    }

    public <T> T getJwwUserInfo(String userId, Class<T> respClass) {
        Map<String, String> requestMap = Maps.newHashMap()
        requestMap.put("userId", userId)
        Map<String,Object> result = post(jwwUrl + URL_JWW_USER_REALNAME, requestMap,Map.class)
        if (result.get("result") == 1) {
            String data = result.get("data");
            return JSON.parseObject(data,respClass)
        } else {
            throw new RuntimeException(result.get("failureMessage"))
        }
    }

    def postToJww(Map<String, String> requestMap) {
        post(jwwUrl + URL_UPDATE_JWW_USER_REALNAME, requestMap)
    }

    public <T> T post(String url,Map<String, String> requestMap, Class<T> respClass) {
        String returnStr = post(url, requestMap)
        return JSON.parseObject(returnStr, respClass)
    }

    public <T> T post(String url,Map<String, String> requestMap) {
        return HttpBuilder.post(url, requestMap)
    }

    String decryptData(String encryptkey, String data) {
        return Decipher.decryptData(encryptkey,data,privateKey,privateKeyPwd)
    }
}
