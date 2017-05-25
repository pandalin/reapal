package com.jvv.reapal.service

import com.jvv.reapal.facade.req.BatchToPayDetailReq
import com.jvv.reapal.facade.result.SimpleResult
import com.jvv.reapal.integration.dto.ConfirmPayNotifyDTO
import com.jvv.reapal.model.entity.BatchToPay
import com.jvv.reapal.model.entity.BatchToPayDetail

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/24
 * @time 9:42
 * @version 1.0
 */
interface BatchToPayService {

    SimpleResult batchToPay(List<BatchToPayDetailReq> detailReqList)

    SimpleResult batchToPayNotify(ConfirmPayNotifyDTO confirmPayNotifyDTO)

    def batchToPaySearch(BatchToPay batchToPay,BatchToPayDetail batchToPayDetail)
}