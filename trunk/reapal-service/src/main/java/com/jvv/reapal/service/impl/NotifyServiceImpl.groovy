package com.jvv.reapal.service.impl

import com.google.common.collect.Maps
import com.jvv.reapal.dao.DebitCardDao
import com.jvv.reapal.facade.enums.Status
import com.jvv.reapal.integration.client.DebitCardClient
import com.jvv.reapal.model.entity.DebitCardOrder
import com.jvv.reapal.model.enums.OrderNotifyStatus
import com.jvv.reapal.service.NotifyService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import javax.annotation.Resource

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/9
 * @time 17:32
 * @version 1.0
 */
@Service("notifyService")
class NotifyServiceImpl implements NotifyService{

    @Resource
    private DebitCardDao debitCardDao
    @Resource
    private DebitCardClient debitCardClient

    @Override
    @Async
    void notifyOrder(DebitCardOrder debitCardOrder) {
        String bg_ret_url = debitCardOrder.bg_ret_url
        Map<String,String> requestMap = Maps.newHashMap()
        requestMap.put("order_no",debitCardOrder.order_no)
        requestMap.put("order_status",debitCardOrder.order_status)
        requestMap.put("member_id",debitCardOrder.member_id)
        requestMap.put("trade_no",debitCardOrder.trade_no)
        requestMap.put("total_fee",String.valueOf(debitCardOrder.total_fee))
        String returnStr = debitCardClient.post(bg_ret_url, requestMap)

        if (Status.SUCCESS.code.equals(returnStr)) {
            debitCardOrder.notify_status = OrderNotifyStatus.S_STATUS.status
            debitCardDao.saveAndFlush(debitCardOrder)
        }
    }

    @Override
    @Async
    def notifyJwwUserRealNameState(String userId, String realName, String cert_no) {
        Map<String,String> requestMap = Maps.newHashMap()
        requestMap.put("userId",userId)
        requestMap.put("realName",realName)
        requestMap.put("cert_no",cert_no)
        debitCardClient.postToJww(requestMap)
    }
}
