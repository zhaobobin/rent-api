package com.rent.service.components;

import com.rent.common.dto.components.dto.SendMsgDto;

import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-20 下午 5:13:02
 * @since 1.0
 */
public interface SendSmsService {

    /**
     * 实名认证发送短信
     * @param sendMsgDto
     * @return
     */
    Boolean realName(SendMsgDto sendMsgDto);

    Boolean businessDelivery(SendMsgDto sendMsgDto);

    Boolean closeOrderBySms(SendMsgDto sendMsgDto);

    Boolean payedOrder(SendMsgDto sendMsgDto);

    Boolean payedBuyOutOrder(SendMsgDto sendMsgDto);

    Boolean payedBuyOutOrderToBusiness(String userName, String buyOutOrderId, String phone, Date createDate);

    Boolean payFailOrder(SendMsgDto sendMsgDto);

    Boolean platformCloseOrder(SendMsgDto sendMsgDto);

    Boolean riskCloseOrder(SendMsgDto sendMsgDto);


    /**
     * 续租支付成功-通知用户 支付宝租物租、抖音租物租的短信
     * @param sendMsgDto
     * @return
     */
    Boolean payedReletOrder(SendMsgDto sendMsgDto);

    /**
     * 续租支付成功-通知商家
     * @param sendMsgDto
     * @return
     */
    Boolean payedReletBusiness(SendMsgDto sendMsgDto);

    /**
     * 商家修改密码
     * @param sendMsgDto
     * @return
     */
    Boolean businessUpdatePassword(SendMsgDto sendMsgDto);
}
