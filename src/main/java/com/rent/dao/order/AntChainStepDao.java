package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.AntChainStep;

import java.util.List;
import java.util.Map;


/**
 * @author zhaowenchao
 */
public interface AntChainStepDao extends IBaseDao<AntChainStep> {

    /**
     * 根据订单编号获取
     * @param orderId
     * @return
     */
    AntChainStep getByOrderId(String orderId);

    /**
     *
     * @param orderId
     * @return
     */
    Map<String,AntChainStep> getByOrderIds(List<String> orderId);


    /**
     * 更新蚁盾分值
     * @param orderId
     * @param shieldScore
     */
    void updateShieldScore(String orderId,String shieldScore);

    /**
     * 更新已经上链
     * @param orderId
     */
    void updateSyncToChain(String orderId);

    /**
     * 更新已经投保
     * @param orderId
     */
    void updateInsure(String orderId);

    /**
     * 更新未投保
     * @param orderId
     */
    void updateUnInsure(String orderId);
}
