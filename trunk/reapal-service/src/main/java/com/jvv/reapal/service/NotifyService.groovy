package com.jvv.reapal.service

import com.jvv.reapal.model.entity.BatchToPay
import com.jvv.reapal.model.entity.BatchToPayDetail
import com.jvv.reapal.model.entity.DebitCardOrder

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/9
 * @time 17:31
 * @version 1.0
 */
interface NotifyService {

    void notifyOrder(DebitCardOrder debitCardOrder)

    def notifyJwwUserRealNameState(String userId, String realName, String cert_no)

    def notifyBatchToPay(BatchToPay batchToPay, BatchToPayDetail batchToPayDetail)
}