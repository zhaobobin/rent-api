        
package com.rent.dao.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.AccountPeriodItemReqDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.ChannelSplitBill;

/**
 * SplitBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public interface ChannelSplitBillDao extends IBaseDao<ChannelSplitBill> {


    /**
     * 更新状态为已结算
     * @param accountPeriodId
     */
    void updateSettled(Long accountPeriodId);

    /**
     * 分页查询账期列表
     * @param request
     * @return
     */
    Page<ChannelSplitBill> queryPage(AccountPeriodItemReqDto request);

    /**
     * 分页查询账期列表
     * @param pageNumber
     * @param pageSize
     * @param accountPeriodId
     * @return
     */
    Page<ChannelSplitBill> queryPage(Integer pageNumber, Integer pageSize, Long accountPeriodId);

    /**
     * 获取一个订单的分账信息
     * @param orderId
     * @param period
     * @return
     */
    ChannelSplitBill getByOrderId(String orderId, Integer period);
}
