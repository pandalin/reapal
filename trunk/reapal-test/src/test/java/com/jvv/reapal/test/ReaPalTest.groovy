package com.jvv.reapal.test

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.jvv.reapal.common.utils.HttpBuilder
import org.apache.commons.codec.digest.DigestUtils
import org.junit.Before
import org.junit.Test

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/11
 * @time 17:37
 * @version 1.0
 */
class ReaPalTest {

    def static final  URL = "http://192.168.1.207:88"

    def static final SECURITY_CODE = "e3f330121c714e1890adc489152b9aa9"

    private static Map<String,Object> params = Maps.newHashMap()

    @Before
    void init() {
        params.put("partnerId","20170505123001000001")
        params.put("publickey",SECURITY_CODE)
    }

    @Test
    void bindCard() {
        params.put("card_no","6227003761320048165")
        params.put("owner","陈圣林")
        params.put("cert_no","500234198412231155")
        params.put("phone","18716201367")
        params.put("total_fee","100")
        params.put("title","手机")
        params.put("body","华为手机")
        params.put("member_id","afe2dda6-39eb-11e7-8393-6c92bf3160a3")
        params.put("bank_card_type","0")
//        params.put("cvv2","521")
//        params.put("validthru","0420")
        params.put("terminal_type","mobile")
        params.put("bg_ret_url","http://192.168.1.207:9898/notify/test")
//        params.put("order_no",DateFormatUtils.format(DateTime.now().toDate(),"yyyyMMddSSS") + RandomStringUtils.randomNumeric(9))
        params.put("order_no","CQ591add06b58e20-64483030")
        get("/reapal/debitCardSign")
    }

    @Test
    void confirmPay() {
        String orderNo = "20170516585555770984"
        String memberId = "12345"
        params.put("order_no",orderNo)
        params.put("member_id",memberId)
        params.put("check_code","123456")
        get("/reapal/confirmPay")
    }

    @Test
    void sendSms() {
        String orderNo = "20170512579905677358"
        String memberId = "12345"
        params.put("order_no",orderNo)
        params.put("member_id",memberId)
        get("/reapal/sendSms")
    }

    @Test
    void unbindCard() {
        String bank_id
        String memberId
        params.put("bank_id",bank_id)
        params.put("member_id",memberId)
        get("/reapal/unBindCard")
    }

    def initParams() {
        List<String> keyList = Lists.newArrayList(params.keySet().iterator())
        Collections.sort(keyList)
        def signStr = ""
        keyList.each { key -> signStr += params.get(key) }
        params.put("sign",DigestUtils.md5Hex(signStr))
    }

    def get(method) {
        initParams()
        println HttpBuilder.post(URL + method,params)
    }

}
