package com.rent.dao.components;


import com.rent.common.enums.components.AntiReqType;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AntChainLog;

/**
 * @author zhaowenchao
 */
public interface AntChainLogDao extends IBaseDao<AntChainLog> {

    /**
     * 根据订单编号和类型获取到成功的记录
     * @param orderId
     * @param type
     * @return
     */
    AntChainLog getSyncSuccessLog(String orderId, AntiReqType type);

}
