package com.jvv.reapal.facade.result

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 15:17
 * @version 1.0
 */
class BizResult<T> extends SimpleResult{

    private T data

    T getData() {
        return data
    }

}
