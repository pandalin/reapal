package com.jvv.reapal.integration.client

import com.jvv.reapal.common.utils.DateUtils
import com.jvv.reapal.common.utils.StringUtils
import com.jvv.reapal.integration.dto.BatchToPayDTO
import com.jvv.reapal.integration.dto.BatchToPaySearchDTO
import com.jvv.reapal.integration.resp.BatchToPayResp
import com.jvv.reapal.integration.resp.BatchToPaySearchResp
import org.apache.commons.lang3.time.DateFormatUtils
import org.joda.time.DateTime
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/23
 * @time 17:08
 * @version 1.0
 */
@Component("batchToPayClient")
class BatchToPayClient extends AbstractClient{

    BatchToPayResp batchToPay(BatchToPayDTO batchToPayDTO) {
        batchToPayDTO.trans_time = DateUtils.currentStr()
        batchToPayDTO.notify_url = getNotifyUrl() + URL_TOPAY_NOTIFY
        batchToPayDTO.batch_no = StringUtils.getUUId()
        BatchToPayResp batchPayResp = super.batchToPay(URL_BATCHTOPAY,batchToPayDTO,BatchToPayResp.class)
        return batchPayResp
    }

    BatchToPaySearchResp batchToPaySearch(BatchToPaySearchDTO batchToPaySearchDTO) {
        BatchToPaySearchResp batchToPaySearchResp = super.batchToPay(URL_SINGLEPAYQUERY,batchToPaySearchDTO,BatchToPaySearchResp.class)
        return batchToPaySearchResp
    }


}
