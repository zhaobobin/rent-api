package com.rent.service.components;

/**
 * 蚂蚁链保险
 * @author zhaowenchao
 */
public interface AntChainInsuranceService {


    /**
     * 蚂蚁链投保
     * @param orderId
     * @param month
     * @param channelId
     * @return
     */
    Boolean antChainInsure(String orderId,Integer month,String channelId);

    /**
     * 蚂蚁链退保
     * @param orderId
     * @param channelId
     * @return
     */
    Boolean cancelInsurance(String orderId,String channelId);
}
