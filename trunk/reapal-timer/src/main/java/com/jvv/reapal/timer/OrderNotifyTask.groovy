package com.jvv.reapal.timer

import com.jvv.reapal.model.entity.DebitCardOrder
import com.jvv.reapal.service.NotifyService
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
 * @time 15:16
 * @version 1.0
 */
@Component("orderNotifyTask")
@Slf4j
class OrderNotifyTask {

    @Resource private NotifyService notifyService

    @Resource private ReaPalService reaPalService

//    @PostConstruct
    def init() {
        notifyOrder()
    }

    @Scheduled(cron = "0 0/3 * * * ?")
    def notifyOrder() {
        log.info("===================通知PHP订单定时任务===================")
        List<DebitCardOrder> unNotifyList = reaPalService.getDebitCardOrderUnNotifyed()
        if (CollectionUtils.isNotEmpty(unNotifyList)) {
            for (DebitCardOrder debitCardOrder : unNotifyList) {
                notifyService.notifyOrder(debitCardOrder)
            }
        }
    }
}
