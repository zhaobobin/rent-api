package com.rent.service.order.impl;

import com.rent.common.dto.product.SpiltBillRuleConfigDto;
import com.rent.dao.order.OrderSettlementDao;
import com.rent.dao.order.SplitBillDao;
import com.rent.model.order.SplitBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author zhaowenchao
 */
@Service("GenerateOrderSettleSplitBillImplService")
public class GenerateOrderSettleSplitBillImplService extends
    GenerateSplitBillImplService {

    private OrderSettlementDao orderSettlementDao;

    @Autowired
    public GenerateOrderSettleSplitBillImplService(SplitBillDao splitBillDao, OrderSettlementDao orderSettlementDao) {
        super(splitBillDao);
        this.orderSettlementDao = orderSettlementDao;
    }


    /**
     * 打给商家的钱 = 买断金额*94%+剩余租金*租赁佣金比例
     * 剩余租金 = 总租金-已付租金
     * 已付租金 = 正常租金+提前支付的租金
     *
     * @param splitBill
     * @param configDto
     * @param recordId
     */
    @Override
    public void fillSplitBillInfo(SplitBill splitBill, SpiltBillRuleConfigDto configDto, Long recordId) {

        BigDecimal transAmount = splitBill.getUserPayAmount().multiply(BigDecimal.ONE.subtract(configDto.getScale())).setScale(2, BigDecimal.ROUND_HALF_UP);
        splitBill.setTransAmount(transAmount);
        //更新买断订单分账时间
        orderSettlementDao.updateStateSplitBill(recordId);
    }



}