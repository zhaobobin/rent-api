package com.rent.service.export.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.export.AccountPeriodBuyOutDto;
import com.rent.common.dto.export.AccountPeriodRentDto;
import com.rent.common.dto.export.FeeBillDto;
import com.rent.common.dto.order.AccountPeriodItemReqDto;
import com.rent.common.dto.order.FeeBillDetailReqDto;
import com.rent.common.enums.export.ExportFileName;
import com.rent.config.annotation.ExportFile;
import com.rent.dao.order.FeeBillDao;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.SplitBillDao;
import com.rent.model.order.OrderByStages;
import com.rent.service.export.AccountPeriodExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhaowenchao
 */
@Service
@Slf4j
public class AccountPeriodExportServiceImpl implements AccountPeriodExportService {

    private SplitBillDao splitBillDao;
    private OrderByStagesDao orderByStagesDao;
    private FeeBillDao feeBillDao;

    public AccountPeriodExportServiceImpl(SplitBillDao splitBillDao, OrderByStagesDao orderByStagesDao, FeeBillDao feeBillDao) {
        this.splitBillDao = splitBillDao;
        this.orderByStagesDao = orderByStagesDao;
        this.feeBillDao = feeBillDao;
    }


    @Override
    @ExportFile(fileName= ExportFileName.ACCOUNT_PERIOD_BUY_OUT,exportDtoClazz= AccountPeriodBuyOutDto.class)
    public List<AccountPeriodBuyOutDto> buyOut(AccountPeriodItemReqDto reqDto) {

        List<AccountPeriodBuyOutDto> list = splitBillDao.buyOutExport(reqDto.getAccountPeriodId());
        if(CollectionUtil.isEmpty(list)){
            return list;
        }
        for (AccountPeriodBuyOutDto accountPeriodBuyOutDto : list) {
            if(reqDto.getShopId()!=null && !accountPeriodBuyOutDto.getShopId().equals(reqDto.getShopId())){
                return new ArrayList<>();
            }
            accountPeriodBuyOutDto.setBrokerage(accountPeriodBuyOutDto.getEndFund().subtract(accountPeriodBuyOutDto.getTransAmount()));
        }
        return list;

    }


    @Override
    @ExportFile(fileName= ExportFileName.ACCOUNT_PERIOD_RENT,exportDtoClazz= AccountPeriodRentDto.class)
    public List<AccountPeriodRentDto> rent(AccountPeriodItemReqDto reqDto) {
        List<AccountPeriodRentDto> list = splitBillDao.rentExport(reqDto.getAccountPeriodId());
        if(CollectionUtil.isEmpty(list)){
            return list;
        }
        List<String> orderIdList = list.stream().map(AccountPeriodRentDto::getOrderId).collect(Collectors.toList());
        List<OrderByStages> orderStagesList = orderByStagesDao.list(new QueryWrapper<OrderByStages>()
                .select("order_id,total_periods,total_rent,current_periods,status")
                .eq("current_periods",1)
                .in("order_id",orderIdList));
        Map<String,OrderByStages> orderStagesMap = orderStagesList.stream().collect(Collectors.toMap(OrderByStages::getOrderId, Function.identity()));
        for (AccountPeriodRentDto dto : list) {
            if(reqDto.getShopId()!=null && !dto.getShopId().equals(reqDto.getShopId())){
                return new ArrayList<>();
            }
            OrderByStages orderStages = orderStagesMap.get(dto.getOrderId());
            dto.setTotalRent(orderStages.getTotalRent());
            String stageInfo = dto.getPeriod()+"/"+orderStages.getTotalPeriods();
            dto.setStageInfo(stageInfo);
            dto.setBrokerage(dto.getSettleAmount().subtract(dto.getTransAmount()));
        }
        return list;
    }

    @Override
    @ExportFile(fileName=ExportFileName.FEE_BILL,exportDtoClazz= FeeBillDto.class)
    public List<FeeBillDto> feeBillDetail(FeeBillDetailReqDto request) {
        return feeBillDao.feeBillDetail(request);
    }
}
