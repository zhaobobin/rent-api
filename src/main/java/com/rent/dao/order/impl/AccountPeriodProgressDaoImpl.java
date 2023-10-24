package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.order.AccountPeriodProgressDto;
import com.rent.common.enums.order.EnumAccountPeriodOperator;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.AccountPeriodProgressDao;
import com.rent.mapper.order.AccountPeriodProgressMapper;
import com.rent.model.order.AccountPeriodProgress;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账期备注
 *
 * @author zhaowenchao
 */
@Repository
public class AccountPeriodProgressDaoImpl
    extends AbstractBaseDaoImpl<AccountPeriodProgress, AccountPeriodProgressMapper>
    implements AccountPeriodProgressDao {

    @Override
    public void insertProgress(Long accountPeriod, EnumAccountPeriodOperator status) {
        insertProgress(accountPeriod, status, "系统");
    }

    @Override
    public void insertProgress(Long accountPeriod, EnumAccountPeriodOperator status, String operator) {
        AccountPeriodProgress progress = new AccountPeriodProgress();
        progress.setAccountPeriodId(accountPeriod);
        progress.setOperator(operator);
        progress.setStatus(status);
        progress.setCreateTime(new Date());
        save(progress);
    }

    @Override
    public List<AccountPeriodProgressDto> getProgressList(Long accountPeriod) {
        List<AccountPeriodProgress> list = list(
            new QueryWrapper<AccountPeriodProgress>().eq("account_period_id", accountPeriod)
                .orderByDesc("create_time"));
        List<AccountPeriodProgressDto> dtoList = new ArrayList<>(list.size());
        for (AccountPeriodProgress accountPeriodProgress : list) {
            AccountPeriodProgressDto dto = new AccountPeriodProgressDto();
            dto.setStatus(accountPeriodProgress.getStatus());
            dto.setCreateTime(accountPeriodProgress.getCreateTime());
            dto.setOperator(accountPeriodProgress.getOperator());
            dtoList.add(dto);
        }
        return dtoList;
    }

}
