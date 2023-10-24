package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.order.AccountPeriodProgressDto;
import com.rent.common.enums.order.EnumAccountPeriodOperator;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.ChannelAccountPeriodProgressDao;
import com.rent.mapper.order.ChannelAccountPeriodProgressMapper;
import com.rent.model.order.ChannelAccountPeriodProgress;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 账期备注
 * @author zhaowenchao
 */
@Repository
public class ChannelAccountPeriodProgressDaoImpl extends AbstractBaseDaoImpl<ChannelAccountPeriodProgress, ChannelAccountPeriodProgressMapper> implements ChannelAccountPeriodProgressDao {

    @Override
    public void insertProgress(Long accountPeriod, EnumAccountPeriodOperator status) {
        insertProgress(accountPeriod,status,"系统");
    }

    @Override
    public void insertProgress(Long accountPeriod, EnumAccountPeriodOperator status, String operator) {
        ChannelAccountPeriodProgress progress = new ChannelAccountPeriodProgress();
        progress.setAccountPeriodId(accountPeriod);
        progress.setOperator(operator);
        progress.setStatus(status);
        progress.setCreateTime(new Date());
        save(progress);
    }

    @Override
    public List<AccountPeriodProgressDto> getProgressList(Long accountPeriod) {
        List<ChannelAccountPeriodProgress> list = list(new QueryWrapper<ChannelAccountPeriodProgress>().eq("account_period_id",accountPeriod).orderByDesc("create_time"));
        List<AccountPeriodProgressDto> dtoList = new ArrayList<>(list.size());
        for (ChannelAccountPeriodProgress accountPeriodProgress : list) {
            AccountPeriodProgressDto dto = new AccountPeriodProgressDto();
            dto.setStatus(accountPeriodProgress.getStatus());
            dto.setCreateTime(accountPeriodProgress.getCreateTime());
            dto.setOperator(accountPeriodProgress.getOperator());
            dtoList.add(dto);
        }
        return dtoList;
    }


}
