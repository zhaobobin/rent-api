package com.rent.dao.components;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AntChainInsuranceLog;


/**
 * @author zhaowenchao
 */
public interface AntChainInsuranceLogDao extends IBaseDao<AntChainInsuranceLog> {

    /**
     * 查询订单上一次保险记录
     * @param orderId
     * @return
     */
    AntChainInsuranceLog getLastSuccessByOrderId(String orderId);

}
