package com.jvv.reapal.notify.controller

import com.alibaba.fastjson.JSON
import com.google.common.collect.Maps
import com.jvv.reapal.facade.api.ReaPalFacadeApi
import com.jvv.reapal.facade.enums.Status
import com.jvv.reapal.facade.req.ReaPalNotifyReq
import com.jvv.reapal.facade.result.SimpleResult
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 18:35
 * @version 1.0
 */
@RestController
@Slf4j
class ReaPalNotifyController {

    @Resource
    private ReaPalFacadeApi reaPalFacadeApi

    @RequestMapping("/notify/confirmPay")
    String confirmPayNotify(ReaPalNotifyReq payNotifyReq) {
        SimpleResult result = reaPalFacadeApi.confirmPayNotify(payNotifyReq)
        return result.status.code
    }

    @RequestMapping("/notify/batchToPay")
    String batchToPay(ReaPalNotifyReq payNotifyReq) {
        log.info("===================>批量提现融宝回调数据结果:{}",JSON.toJSONString(payNotifyReq))
        SimpleResult result = reaPalFacadeApi.batchToPayNotify(payNotifyReq)
        return result.status.code
    }

    @RequestMapping("/notify/test")
    ResponseEntity<String> notifyTest(String order_no,String order_status,String member_id) {
        return new ResponseEntity<String>(Status.SUCCESS.code,HttpStatus.OK)
    }

    @RequestMapping("/notify/test1")
    ResponseEntity<Map<String,String>> notifyTest1(String order_no,String order_status,String member_id) {
        Map<String,String> map = Maps.newHashMap()
        map.put("order_no","1235")
        map.put("member_id",member_id)
        return new ResponseEntity<Map<String,String>>(map,HttpStatus.OK)
    }

}
