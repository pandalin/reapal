package com.jvv.reapal.timer

import com.jvv.reapal.model.entity.DebitCardOrder
import com.jvv.reapal.service.ReaPalService
import groovy.util.logging.Slf4j
import org.apache.commons.collections.CollectionUtils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import javax.annotation.Resource

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/9
 * @time 15:31
 * @version 1.0
 */
@Component("orderSearchTask")
@Slf4j
class OrderSearchTask {

    @Resource
    private ReaPalService reaPalService

    @PostConstruct
    def init() {
        searchOrder()
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    def searchOrder() {
        log.info("===================支付订单未通知定时任务===================")
        List<DebitCardOrder> unCompleteOrderList = reaPalService.getDebitCardOrderUnComplete()
        if (CollectionUtils.isNotEmpty(unCompleteOrderList)) {
            for (DebitCardOrder debitCardOrder : unCompleteOrderList) {
                reaPalService.confirmPaySearch(debitCardOrder.order_no)
            }
        }
    }
}
