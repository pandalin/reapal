package com.jvv.reapal.integration.client

import com.jvv.reapal.common.utils.DateUtils
import com.jvv.reapal.common.utils.StringUtils
import com.jvv.reapal.integration.dto.DebitCardDTO
import com.jvv.reapal.integration.dto.DebitCardSignDTO
import com.jvv.reapal.integration.dto.UnBindCardDTO
import com.jvv.reapal.integration.resp.DebitCardResp
import com.jvv.reapal.integration.resp.UnBindCardResp
import com.jvv.reapal.model.entity.DebitCard
import com.jvv.reapal.model.enums.BankCardType
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.time.DateFormatUtils
import org.codehaus.groovy.runtime.InvokerHelper
import org.joda.time.DateTime
import org.springframework.stereotype.Component
import org.springframework.util.Assert

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 16:50
 * @version 1.0
 */
@Component("debitCardClient")
class DebitCardClient extends AbstractClient{

    def setDefaultDebitDTO(DebitCardDTO debitCardDTO) {
        debitCardDTO.merchant_id = getMerchantId()
        debitCardDTO.seller_email = getSeller_email()
        debitCardDTO.notify_url = getNotifyUrl() + URL_PAY_NOTIFY
        debitCardDTO.token_id = StringUtils.getUUId()
        debitCardDTO.transtime = DateUtils.currentStr()
        debitCardDTO.terminal_info = RandomStringUtils.randomNumeric(15) + "_" + RandomStringUtils.random(10,'abcdefghigklmnopqrstuvwxyz')
    }

    DebitCardResp bindCard(DebitCardDTO debitCardDTO) {
        setDefaultDebitDTO(debitCardDTO)
        DebitCardResp debitCardResp
        if (BankCardType.T_DEBIT_CARD.code == debitCardDTO.bank_card_type) {
            debitCardResp = super.reaPal(URL_DEBITCARD,debitCardDTO,DebitCardResp.class)
        } else {
            Assert.notNull(debitCardDTO.cvv2,"信用卡后的3位数字不能为空")
            Assert.state(debitCardDTO.cvv2.length() == 3,"只能填写信用卡后3位数字")
            Assert.notNull(debitCardDTO.validthru,"卡有效期不能为空")
            debitCardResp = super.reaPal(URL_CREDIT_CARD,debitCardDTO,DebitCardResp.class)
        }
        return debitCardResp
    }

    DebitCardResp payContract(DebitCardDTO debitCardDTO, DebitCard userDebitCard) {
        setDefaultDebitDTO(debitCardDTO)
        DebitCardSignDTO debitCardSignDTO = new DebitCardSignDTO()
        InvokerHelper.setProperties(debitCardSignDTO,debitCardDTO.properties)
        debitCardSignDTO.bind_id = userDebitCard.bind_id
        DebitCardResp debitCardResp = super.reaPal(URL_DEBITCARD_SIGN,debitCardSignDTO,DebitCardResp.class)

        return debitCardResp
    }

    UnBindCardResp unBindCard(String bind_id,String member_id) {
        UnBindCardDTO unBindCardDTO = new UnBindCardDTO()
        unBindCardDTO.merchant_id = getMerchantId()
        unBindCardDTO.bind_id = bind_id
        unBindCardDTO.member_id = member_id
        UnBindCardResp unBindCardResp = super.reaPal(URL_UNBIND_DEBITCARD,unBindCardDTO,UnBindCardResp.class)
        return unBindCardResp
    }
}
