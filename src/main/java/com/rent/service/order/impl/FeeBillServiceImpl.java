        
package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.order.FeeBillConverter;
import com.rent.common.dto.order.response.FeeBillPageResp;
import com.rent.common.dto.order.resquest.FeeBillReqDto;
import com.rent.common.enums.order.EnumFeeBillType;
import com.rent.common.enums.order.EnumSettleStatus;
import com.rent.dao.components.AlipayFreezeDao;
import com.rent.dao.order.FeeBillDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.AlipayFreeze;
import com.rent.model.order.FeeBill;
import com.rent.model.order.UserOrders;
import com.rent.service.order.FeeBillService;
import com.rent.service.product.ShopFundService;
import com.rent.service.user.ConfigService;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class FeeBillServiceImpl implements FeeBillService {

    private final UserOrdersDao userOrdersDao;
    private final FeeBillDao feeBillDao;
    private final AlipayFreezeDao alipayFreezeDao;
    private final ShopFundService shopFundService;
    private final ConfigService configService;

    @Override
    public Boolean importAssessmentFee(List<String> outOrderNoList) {
        String configAmount = configService.getConfigByCode(EnumFeeBillType.ASSESSMENT.getConfigCode());
        if(StringUtils.isEmpty(configAmount)){
            throw new HzsxBizException("-1","未找到租押分离费用配置");
        }
        BigDecimal unitAmount = new BigDecimal(configAmount);
        if(unitAmount.compareTo(BigDecimal.ZERO)<0){
            throw new HzsxBizException("-1","租押分离费用配置小于0");
        }
        List<AlipayFreeze> alipayFreezes = alipayFreezeDao.list(new QueryWrapper<AlipayFreeze>().select("order_id").in("out_order_no",outOrderNoList));
        if(CollectionUtil.isEmpty(alipayFreezes)){
            return Boolean.TRUE;
        }
        List<String> orderIdList =  alipayFreezes.stream().map(AlipayFreeze::getOrderId).collect(Collectors.toList());
        List<FeeBill> feeBills = feeBillDao.getAssessmentFeeByOrderId(orderIdList);
        if(CollectionUtil.isNotEmpty(feeBills)){
            throw new HzsxBizException("-1","导入异常！订单:"+feeBills.get(0).getOrderId()+"已计费");
        }
        List<UserOrders> orders = userOrdersDao.list(new QueryWrapper<UserOrders>().in("order_id",orderIdList));
        List<FeeBill> saveFeeBills = new ArrayList<>(orders.size());
        for (UserOrders order : orders) {
            FeeBill feeBill = FeeBillConverter.generateAssessmentFeeBill(order,unitAmount);
            if(feeBill!=null){
                saveFeeBills.add(feeBill);
            }
        }
        feeBillDao.saveBatch(saveFeeBills);
        log.info("importAssessmentFee==============>>>>>>>>>>导入"+saveFeeBills.size()+"条记录");


        List<FeeBill> waitSettlementBill = feeBillDao.list(new QueryWrapper<FeeBill>().eq("type", EnumFeeBillType.ASSESSMENT).isNull("fund_flow_id"));
        Map<String,List<FeeBill>> map = waitSettlementBill.stream().collect(Collectors.groupingBy(FeeBill::getShopId));
        for (String shopId : map.keySet()) {
            Date now = new Date();
            List<FeeBill> shopWaitSettlementBill = map.get(shopId);
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (FeeBill feeBill : shopWaitSettlementBill) {
                totalAmount = totalAmount.add(feeBill.getAmount());
            }
            Long fundFlowId = shopFundService.assessmentFeeSettle(shopId,totalAmount);
            for (FeeBill feeBill : shopWaitSettlementBill) {
                feeBill.setFundFlowId(fundFlowId);
                feeBill.setStatus(EnumSettleStatus.SETTLED);
                feeBill.setUpdateTime(now);
            }
            feeBillDao.updateBatchById(shopWaitSettlementBill);
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean reportBilling(String orderId) {
        String configAmount = configService.getConfigByCode(EnumFeeBillType.CREDIT_REPORT.getConfigCode());
        if(StringUtils.isEmpty(configAmount)){
            throw new HzsxBizException("-1","未找到风控报告费用配置");
        }
        BigDecimal unitAmount = new BigDecimal(configAmount);
        if(unitAmount.compareTo(BigDecimal.ZERO)<0){
            throw new HzsxBizException("-1","风控报告费用配置小于0");
        }
        FeeBill feeBill = feeBillDao.getByOrderIdAndType(orderId,EnumFeeBillType.CREDIT_REPORT);
        if (feeBill != null) {
            return Boolean.TRUE;
        }
        //加缓存，避免重复计费
        if (!RedisUtil.tryLock("reportBilling" + orderId, 40)) {
            log.info("【风控报告计费】已有任务执行");
            return Boolean.TRUE;
        }
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        feeBill = FeeBillConverter.generateReportFeeBill(userOrders,unitAmount);
        feeBillDao.save(feeBill);

        Long fundFlowId = shopFundService.shopFundOrderReportFee(userOrders.getShopId(), feeBill.getAmount(),orderId);
        feeBill.setFundFlowId(fundFlowId);
        feeBill.setStatus(EnumSettleStatus.SETTLED);
        feeBill.setUpdateTime(new Date());
        feeBillDao.updateById(feeBill);
        return Boolean.TRUE;
    }

    @Override
    public Boolean contractBilling(String orderId) {
        String configAmount = configService.getConfigByCode(EnumFeeBillType.CONTRACT.getConfigCode());
        if(StringUtils.isEmpty(configAmount)){
            throw new HzsxBizException("-1","未找到电子合同费用配置");
        }
        BigDecimal unitAmount = new BigDecimal(configAmount);
        if(unitAmount.compareTo(BigDecimal.ZERO)<0){
            throw new HzsxBizException("-1","电子合同费用配置小于0");
        }
        FeeBill feeBill = feeBillDao.getByOrderIdAndType(orderId,EnumFeeBillType.CONTRACT);
        if (feeBill != null) {
            return Boolean.TRUE;
        }
        //加缓存，避免重复计费
        if (!RedisUtil.tryLock("contractBilling" + orderId, 40)) {
            log.info("【电子合同计费】已有任务执行");
            return Boolean.TRUE;
        }
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        feeBill = FeeBillConverter.generateContractFeeBill(userOrders,unitAmount);
        feeBillDao.save(feeBill);

        Long fundFlowId = shopFundService.shopFundContractFee(userOrders.getShopId(), feeBill.getAmount(),orderId);
        feeBill.setFundFlowId(fundFlowId);
        feeBill.setStatus(EnumSettleStatus.SETTLED);
        feeBill.setUpdateTime(new Date());
        feeBillDao.updateById(feeBill);
        return Boolean.TRUE;
    }


    @Override
    public Page<FeeBillPageResp> page(FeeBillReqDto request) {
        return feeBillDao.pageByCondition(request);
    }

}