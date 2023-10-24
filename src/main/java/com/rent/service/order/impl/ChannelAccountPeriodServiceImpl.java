package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.order.ChannelAccountPeriodConverter;
import com.rent.common.converter.order.ChannelSplitBillConverter;
import com.rent.common.converter.order.OrderByStagesConverter;
import com.rent.common.converter.order.UserOrderCashesConverter;
import com.rent.common.dto.backstage.SplitBillDetailRentDto;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.resquest.ChannelAccountPeriodReqDto;
import com.rent.common.dto.product.ChannelSplitBillDto;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.order.EnumAccountPeriodOperator;
import com.rent.common.enums.order.EnumAccountPeriodStatus;
import com.rent.common.util.StringUtil;
import com.rent.dao.components.AlipayFreezeDao;
import com.rent.dao.order.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.AlipayFreeze;
import com.rent.model.order.ChannelAccountPeriod;
import com.rent.model.order.ChannelSplitBill;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;
import com.rent.service.order.ChannelAccountPeriodService;
import com.rent.service.order.OpeUserOrdersService;
import com.rent.service.product.ChannelSplitBillService;
import com.rent.service.product.ShopFundService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelAccountPeriodServiceImpl implements ChannelAccountPeriodService {

    private final ChannelAccountPeriodDao channelAccountPeriodDao;
    private final ChannelSplitBillDao channelSplitBillDao;
    private final ChannelAccountPeriodProgressDao channelAccountPeriodProgressDao;
    private final UserOrdersDao userOrdersDao;
    private final OrderByStagesDao orderByStagesDao;
    private final OpeUserOrdersService opeUserOrdersService;
    private final UserOrderCashesDao userOrderCashesDao;
    private final ShopFundService shopFundService;
    private final ChannelSplitBillService channelSplitBillService;
    private final AlipayFreezeDao alipayFreezeDao;

    @Override
    public Page<ChannelAccountPeriodDto> queryChannelAccountPeriodPage(ChannelAccountPeriodReqDto reqDto) {
        Date begin = DateUtil.getDayBegin(reqDto.getSettleDate());
        Date end = DateUtil.getDayEnd(reqDto.getSettleDate());
        Page<ChannelAccountPeriod> page = channelAccountPeriodDao.page(new Page<>(reqDto.getPageNumber(), reqDto.getPageSize()),
            new QueryWrapper<ChannelAccountPeriod>()
                    .like(StringUtil.isNotEmpty(reqDto.getMarketingChannelName()),"marketing_channel_name",reqDto.getMarketingChannelName())
                    .eq(StringUtil.isNotEmpty(reqDto.getMarketingChannelId()),"marketing_channel_id",reqDto.getMarketingChannelId())
                    .eq(reqDto.getStatus()!=null,"status",reqDto.getStatus())
                    .between(begin!=null,"settle_date",begin,end)
                    .orderByDesc("create_time"));
        return new Page<ChannelAccountPeriodDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                ChannelAccountPeriodConverter.modelList2DtoList(page.getRecords()));
    }


    @Override
    public void submitSettle(AccountPeriodSubmitSettleDto req) {
        ChannelAccountPeriod channelAccountPeriod = channelAccountPeriodDao.getById(req.getAccountPeriodId());
        if(!channelAccountPeriod.getStatus().equals(EnumAccountPeriodStatus.WAITING_SETTLEMENT)){
            throw new HzsxBizException("-1","当前状态不允许审核");
        }
        channelAccountPeriod.setSettleAmount(req.getSettleAmount());
        channelAccountPeriod.setStatus(EnumAccountPeriodStatus.TO_AUDIT);
        channelAccountPeriodDao.updateById(channelAccountPeriod);
        channelAccountPeriodProgressDao.insertProgress(channelAccountPeriod.getId(), EnumAccountPeriodOperator.SUBMIT_SETTLE,req.getBackstageUserName());
    }

    @Override
    public void submitAudit(AccountPeriodSubmitAuditDto req) {
        ChannelAccountPeriod channelAccountPeriod = channelAccountPeriodDao.getById(req.getAccountPeriodId());
        if(!channelAccountPeriod.getStatus().equals(EnumAccountPeriodStatus.TO_AUDIT)){
            throw new HzsxBizException("-1","当前状态不允许审核");
        }
        if(req.getAuditResult()){
            shopFundService.brokerageSettle(channelAccountPeriod.getMarketingChannelId(),channelAccountPeriod.getSettleAmount(),channelAccountPeriod.getId());
            channelSplitBillDao.updateSettled(channelAccountPeriod.getId());
            channelAccountPeriodProgressDao.insertProgress(channelAccountPeriod.getId(),EnumAccountPeriodOperator.AUDIT_PASS,req.getBackstageUserName());
            channelAccountPeriod.setStatus(EnumAccountPeriodStatus.SETTLED);
        }else{
            channelAccountPeriod.setStatus(EnumAccountPeriodStatus.WAITING_SETTLEMENT);
            channelAccountPeriodProgressDao.insertProgress(channelAccountPeriod.getId(),EnumAccountPeriodOperator.AUDIT_DENY,req.getBackstageUserName());
        }
        channelAccountPeriodDao.updateById(channelAccountPeriod);
    }

    @Override
    public ChannelAccountPeriodDetailDto detail(Long id) {
        ChannelAccountPeriod channelAccountPeriod = channelAccountPeriodDao.getById(id);
        ChannelAccountPeriodDetailDto dto = new ChannelAccountPeriodDetailDto();
        if (channelAccountPeriod != null){
            dto.setId(id);
            dto.setMarketingChannelName(channelAccountPeriod.getMarketingChannelName());
            dto.setSettleDate(channelAccountPeriod.getSettleDate());
            dto.setStatus(channelAccountPeriod.getStatus());
            dto.setRealAmount(channelAccountPeriod.getSettleAmount());
            dto.setTotalSettleAmount(channelAccountPeriod.getTotalAmount().subtract(channelAccountPeriod.getTotalBrokerage()));
            dto.setTotalBrokerage(channelAccountPeriod.getTotalBrokerage());
            dto.setProgresses(channelAccountPeriodProgressDao.getProgressList(id));
        }
        return dto;
    }

    @Override
    public void generateAccountPeriod() {
        List<ChannelSplitBill> list = channelSplitBillDao.list(new QueryWrapper<ChannelSplitBill>().isNull("account_period_id"));
        Map<String,List<ChannelSplitBill>> shopSplitBillMap = list.stream().collect(Collectors.groupingBy(ChannelSplitBill::getMarketingId));
        Date now = new Date();
        for(String marketingId : shopSplitBillMap.keySet()){
            ChannelAccountPeriod channelAccountPeriod = ChannelAccountPeriod.init();
            List<ChannelSplitBill> channelSplitBills = shopSplitBillMap.get(marketingId);
            channelAccountPeriod.setMarketingChannelId(marketingId);
            ChannelSplitBillDto channelSplitBillDto = channelSplitBillService.getOne(marketingId);
            channelAccountPeriod.setMarketingChannelName(channelSplitBillDto.getName());
            for(ChannelSplitBill channelSplitBill :channelSplitBills){
                channelAccountPeriod.addTotalSettleAmount(channelSplitBill.getChannelAmount());
                channelAccountPeriod.addTotalAmount(channelSplitBill.getUserPayAmount());
                BigDecimal brokerage = channelSplitBill.getUserPayAmount().subtract(channelSplitBill.getChannelAmount());
                channelAccountPeriod.addTotalBrokerage(brokerage);
            }
            channelAccountPeriod.setStatus(EnumAccountPeriodStatus.WAITING_SETTLEMENT);
            channelAccountPeriod.setCreateTime(now);
            channelAccountPeriodDao.save(channelAccountPeriod);
            for(ChannelSplitBill channelSplitBill : channelSplitBills){
                channelSplitBill.setAccountPeriodId(channelAccountPeriod.getId());
            }
            channelSplitBillDao.updateBatchById(channelSplitBills);
            channelAccountPeriodProgressDao.insertProgress(channelAccountPeriod.getId(), EnumAccountPeriodOperator.GENERATE);
        }
    }

    @Override
    public Page<ChannelSplitBillRentDto> listRent(AccountPeriodItemReqDto request) {
        Page<ChannelSplitBill> page = channelSplitBillDao.queryPage(request);
        List<ChannelSplitBillRentDto> list = ChannelSplitBillConverter.modelList2RentDtoList(page.getRecords());
        if(CollectionUtil.isEmpty(list)){
            Page<ChannelSplitBillRentDto> emptyPage = new Page<>(request.getPageNumber(), request.getPageSize());
            return emptyPage;
        }
        for (ChannelSplitBillRentDto channelSplitBillRentDto : list) {
            UserOrders userOrders = userOrdersDao.selectOneByOrderId(channelSplitBillRentDto.getOrderId());
            channelSplitBillRentDto.setOrderStatus(userOrders.getStatus());
            OrderByStages orderByStages = orderByStagesDao.queryOrderByOrderIdAndPeriod(channelSplitBillRentDto.getOrderId(),channelSplitBillRentDto.getPeriod());
            channelSplitBillRentDto.setUserPayTime(orderByStages.getStatementDate());
            channelSplitBillRentDto.setTotalPeriod(orderByStages.getTotalPeriods());
        }
        return new Page<ChannelSplitBillRentDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(list);
    }

    @Override
    public SplitBillDetailRentDto rentDetail(String orderId) {
        SplitBillDetailRentDto orderDetailDto = new SplitBillDetailRentDto();
        //订单信息
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        orderDetailDto.setOrderInfoDto(opeUserOrdersService.assemblyOrderInfo(userOrders));
        //商家信息
        orderDetailDto.setShopInfoDto(opeUserOrdersService.assemblyOrderShopInfo(userOrders));
        //商品信息
        orderDetailDto.setProductInfo(opeUserOrdersService.assemblyOrderProductInfo(userOrders));
        //账单信息
        UserOrderCashesDto userOrderCashesDto = UserOrderCashesConverter.model2Dto(userOrderCashesDao.selectOneByOrderId(orderId));
        AlipayFreeze alipayFreezeDto = alipayFreezeDao.selectOneByOrderId(orderId,EnumAliPayStatus.SUCCESS);
        if (null != alipayFreezeDto) {
            userOrderCashesDto.setCreditDeposit(alipayFreezeDto.getCreditAmount());
        }
        orderDetailDto.setUserOrderCashesDto(userOrderCashesDto);
        //分期信息
        List<OrderByStagesDto> orderByStagesDtoList = OrderByStagesConverter.modelList2DtoList(orderByStagesDao.queryOrderByOrderId(orderId));
        packDetailStagesSpiltBillInfo(orderByStagesDtoList);
        orderDetailDto.setOrderByStagesDtoList(orderByStagesDtoList);
        return orderDetailDto;
    }

    @Override
    public Page<ChannelSplitBillRentDto> rent(AccountPeriodItemReqDto request) {
        Page<ChannelSplitBill> page = channelSplitBillDao.queryPage(request.getPageNumber(),request.getPageSize(),request.getAccountPeriodId());

        List<ChannelSplitBillRentDto> list = ChannelSplitBillConverter.modelList2RentDtoList(page.getRecords());
        if(CollectionUtil.isEmpty(list)){
            Page<ChannelSplitBillRentDto> emptyPage = new Page<>(request.getPageNumber(), request.getPageSize());
            return emptyPage;
        }
        for (ChannelSplitBillRentDto channelSplitBillRentDto : list) {
            UserOrders userOrders = userOrdersDao.selectOneByOrderId(channelSplitBillRentDto.getOrderId());
            channelSplitBillRentDto.setOrderStatus(userOrders.getStatus());
            OrderByStages orderByStages = orderByStagesDao.queryOrderByOrderIdAndPeriod(channelSplitBillRentDto.getOrderId(),channelSplitBillRentDto.getPeriod());
            channelSplitBillRentDto.setUserPayTime(orderByStages.getStatementDate());
            channelSplitBillRentDto.setTotalPeriod(orderByStages.getTotalPeriods());
        }
        return new Page<ChannelSplitBillRentDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(list);
    }

    private void packDetailStagesSpiltBillInfo(List<OrderByStagesDto> orderByStagesDtoList){
        for (OrderByStagesDto orderByStagesDto : orderByStagesDtoList) {
            ChannelSplitBill channelSplitBill = channelSplitBillDao.getByOrderId(orderByStagesDto.getOrderId(),orderByStagesDto.getCurrentPeriods());
            if(channelSplitBill!=null){
                orderByStagesDto.setToShopAmount(channelSplitBill.getChannelAmount());
                orderByStagesDto.setToOpeAmount(channelSplitBill.getUserPayAmount().subtract(channelSplitBill.getChannelAmount()));
                orderByStagesDto.setSplitBillStatus(channelSplitBill.getStatus());
            }
        }
    }

}
