package com.jvv.reapal.service

import com.jvv.reapal.model.entity.Merchant

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 19:37
 * @version 1.0
 */
interface MerchantService {

    Merchant loadFromCache(String partnerId)

}