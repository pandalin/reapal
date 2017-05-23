package com.jvv.reapal.integration.resp

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/8
 * @time 14:27
 * @version 1.0
 */
class JwwUserResp extends AbstractResp{

    String userId
    String realName
    String phone
    String cert_no
    String realNameState

    boolean realNameState() {
        return "card_validate".equals(realNameState)
    }
}
