package com.rent.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.AccountPeriodRemarkDto;
import com.rent.common.dto.order.resquest.AccountPeriodRemarkReqDto;
import com.rent.dao.order.AccountPeriodRemarkDao;
import com.rent.model.order.AccountPeriodRemark;
import com.rent.service.order.AccountPeriodRemarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountPeriodRemarkServiceImpl implements AccountPeriodRemarkService {

    private final AccountPeriodRemarkDao accountPeriodRemarkDao;


    @Override
    public void add(AccountPeriodRemarkReqDto reqDto) {
        AccountPeriodRemark accountPeriodRemark = new AccountPeriodRemark();
        accountPeriodRemark.setAccountPeriodId(reqDto.getAccountPeriodId());
        accountPeriodRemark.setBackstageUserName(reqDto.getBackstageUserName());
        accountPeriodRemark.setContent(reqDto.getContent());
        accountPeriodRemark.setCreateTime(new Date());
        accountPeriodRemarkDao.save(accountPeriodRemark);
    }

    @Override
    public Page<AccountPeriodRemarkDto> listByAccountPeriodId(AccountPeriodRemarkReqDto request) {
        Page<AccountPeriodRemark> page = accountPeriodRemarkDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<AccountPeriodRemark>().eq("account_period_id",request.getAccountPeriodId()).orderByDesc("create_time"));

        List<AccountPeriodRemark> remarks = page.getRecords();
        List<AccountPeriodRemarkDto> dtoList = new ArrayList<>(remarks.size());
        for (AccountPeriodRemark accountPeriodRemark : remarks) {
            AccountPeriodRemarkDto dto = new AccountPeriodRemarkDto();
            dto.setBackstageUserName(accountPeriodRemark.getBackstageUserName());
            dto.setContent(accountPeriodRemark.getContent());
            dto.setCreateTime(accountPeriodRemark.getCreateTime());
            dtoList.add(dto);
        }
        return new Page<AccountPeriodRemarkDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(dtoList);
    }
}
