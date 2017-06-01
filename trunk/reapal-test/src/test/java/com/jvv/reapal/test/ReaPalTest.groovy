package com.jvv.reapal.test

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.jvv.reapal.common.utils.HttpBuilder
import com.jvv.reapal.facade.req.BatchToPayDetailReq
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.time.DateFormatUtils
import org.joda.time.DateTime
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
        params.put("card_no","6214242710498301")
        params.put("owner","韩梅梅")
        params.put("cert_no","210302196001012114")
        params.put("phone","13220482188")
        params.put("total_fee","100")
        params.put("title","手机")
        params.put("body","华为手机")
        params.put("member_id","12345")
        params.put("bank_card_type","0")
//        params.put("cvv2","521")
//        params.put("validthru","0420")
        params.put("terminal_type","mobile")
        params.put("bg_ret_url","http://192.168.1.207:88/notify/test")
        params.put("order_no",DateFormatUtils.format(DateTime.now().toDate(),"yyyyMMddSSS") + RandomStringUtils.randomNumeric(9))
//        params.put("order_no","CQ591add06b58e20-64483030")
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
    void getBindCard() {
        String memberId = "12345"
        params.put("member_id",memberId)
        get("/reapal/getBindCard")
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
        String bank_id = '11'
        String memberId = '12345'
        params.put("bank_id",bank_id)
        params.put("member_id",memberId)
        get("/reapal/unBindCard")
    }

    @Test
    void batchToPay() {
        List<BatchToPayDetailReq> list = Lists.newArrayList()


        BatchToPayDetailReq req = new BatchToPayDetailReq()
        req.bank_no = '111111111'
        req.bank_name="农业银行"
        req.order_no = "30000000000003"
//        req.account_name = "张三"
        req.phone="18375693021"
//        req.cert_no = "500234198412231155"
//        req.certificate = "身份证"
        req.remark="测试提现11"
        req.money = 10
        req.member_id = "0000da8d-1e28-11e5-8f47-ecf4bbd34484"
        req.bg_ret_url = "http://192.168.1.207:88/notify/test"
        list.add(req)

        req = new BatchToPayDetailReq()
        req.bank_no = '2222222222'
        req.bank_name="建设银行"
        req.order_no = "30000000000004"
//        req.account_name = "李四"
        req.phone="18375693022"
//        req.cert_no = "500234198412231156"
//        req.certificate = "身份证"
        req.remark="测试提现22"
        req.money = 20
        req.member_id = "00014b01-2572-11e5-8f47-ecf4bbd34484"
        req.bg_ret_url = "http://192.168.1.207:88/notify/test"
        list.add(req)

        String str = JSON.toJSONString(list)
        println HttpBuilder.postJson(URL + "/reapal/batchToPay",str)
    }

    @Test
    void test() {
        /*BatchToPayDetailReq req = new BatchToPayDetailReq()
        req.bank_name = '1231231'
        List<BatchToPayDetailReq> list = Lists.newArrayList()
        list.add(req)

        String str = JSON.toJSONString(list)
        println str
        List<BatchToPayDetailReq> jsonArray = JSON.parse(str)
        println jsonArray.get(0).bank_name*/


        List<String> l = Lists.newArrayList()
        l.add("aa")
        l.add("bb")
        l.add("cc")
        l.eachWithIndex {it,i->
            if (it.is("bb")) {
                return "2222"
            }
            println it + "--" +i
        }

        def b = false
        for (it in l) {
            if (it.is("bb")) {
                b = true
                break
            }
        }
        if (b) {
            println "======="
        }
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
