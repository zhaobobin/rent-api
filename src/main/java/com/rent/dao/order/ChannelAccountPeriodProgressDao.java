package com.rent.dao.order;


import com.rent.common.dto.order.AccountPeriodProgressDto;
import com.rent.common.enums.order.EnumAccountPeriodOperator;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.ChannelAccountPeriodProgress;

import java.util.List;


/**
 * @author zhaowenchao
 */
public interface ChannelAccountPeriodProgressDao extends IBaseDao<ChannelAccountPeriodProgress> {

    /**
     * 保存操作记录
     * @param accountPeriod
     * @param status
     */
    void insertProgress(Long accountPeriod, EnumAccountPeriodOperator status);

    /**
     * 保存操作记录
     * @param accountPeriod
     * @param status
     * @param operator
     */
    void insertProgress(Long accountPeriod,EnumAccountPeriodOperator status,String operator);


    /**
     * 获取操作记录
     * @param accountPeriod
     * @return
     */
    List<AccountPeriodProgressDto> getProgressList(Long accountPeriod);


}
