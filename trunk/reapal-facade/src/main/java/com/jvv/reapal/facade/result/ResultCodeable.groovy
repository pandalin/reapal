package com.jvv.reapal.facade.result

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 16:19
 * @version 1.0
 */
interface ResultCodeable {

    /**
     * 结果码
     * @return
     */
    String getCode();

    /**
     * 结果码描述信息
     * @return
     */
    String getMessage();

}