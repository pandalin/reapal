package com.jvv.reapal.timer

import com.jvv.reapal.dao.BatchToPayDao
import com.jvv.reapal.dao.BatchToPayDetailDao
import com.jvv.reapal.model.entity.BatchToPay
import com.jvv.reapal.model.entity.BatchToPayDetail
import com.jvv.reapal.service.BatchToPayService
import com.jvv.reapal.service.NotifyService
import groovy.util.logging.Slf4j
import org.apache.commons.collections.CollectionUtils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import javax.annotation.Resource

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/25
 * @time 11:07
 * @version 1.0
 */
@Component("batchToPayNotifyTask")
@Slf4j
class BatchToPayNotifyTask {

    @Resource
    private BatchToPayDao batchToPayDao

    @Resource
    private BatchToPayDetailDao batchToPayDetailDao

    @Resource
    private NotifyService notifyService

    @Resource
    private BatchToPayService batchToPayService

    /**
     * 每6小时执行1次
     */
    @Scheduled(cron = "0 0 */6 * * ?")
    def batchToPayNotify() {
        log.info("===================通知长青定时任务开始执行===================")
        List<BatchToPayDetail> unNotifyList = batchToPayDetailDao.findBatchToPayDetailUnNotify()
        if (CollectionUtils.isNotEmpty(unNotifyList)) {
            for (it in unNotifyList) {
                BatchToPay batchToPay = batchToPayDao.findOne(it.pay_id)
                log.info("===================>通知订单号,{}",it.order_no)
                notifyService.notifyBatchToPay(batchToPay,it)
            }
        }
    }

    @Scheduled(cron = "0 0 */2 * * ?")
    def batchToPaySearch() {
        log.info("===================融宝提现未返回状态定时任务开始执行===================")
        List<BatchToPayDetail> unReturnList = batchToPayDetailDao.findBatchToPayDetailUnReturn()
        if (CollectionUtils.isNotEmpty(unReturnList)) {
            for (it in unReturnList) {
                BatchToPay batchToPay = batchToPayDao.findOne(it.pay_id)
                batchToPayService.batchToPaySearch(batchToPay.batch_no,it.order_no)
            }
        }
    }
}
