package com.jvv.reapal.web.controller

import com.alibaba.fastjson.JSON
import com.jvv.reapal.facade.api.ReaPalFacadeApi
import com.jvv.reapal.facade.req.BatchToPayDetailReq
import com.jvv.reapal.facade.result.SimpleResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.annotation.Resource

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/24
 * @time 11:34
 * @version 1.0
 */
@RestController
class ReaPalBatchToPayController {

    @Resource
    private ReaPalFacadeApi reaPalFacadeApi

    @RequestMapping(value = "/reapal/batchToPay")
    ResponseEntity<SimpleResult> batchToPay(@RequestBody BatchToPayDetailReq[] detailReqs) {
        SimpleResult result = reaPalFacadeApi.batchToPay(Arrays.asList(detailReqs))
        return new ResponseEntity<SimpleResult>(result,HttpStatus.OK)
    }

}
