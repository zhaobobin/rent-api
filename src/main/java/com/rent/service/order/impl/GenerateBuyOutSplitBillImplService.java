package com.rent.service.order.impl;

import com.rent.common.dto.product.SpiltBillRuleConfigDto;
import com.rent.dao.order.SplitBillDao;
import com.rent.dao.order.UserOrderBuyOutDao;
import com.rent.model.order.SplitBill;
import com.rent.model.order.UserOrderBuyOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author zhaowenchao
 */
@Service("GenerateBuyOutSplitBillImplService")
public class GenerateBuyOutSplitBillImplService extends
    GenerateSplitBillImplService {

    private UserOrderBuyOutDao userOrderBuyOutDao;

    @Autowired
    public GenerateBuyOutSplitBillImplService(SplitBillDao splitBillDao, UserOrderBuyOutDao userOrderBuyOutDao) {
        super(splitBillDao);
        this.userOrderBuyOutDao = userOrderBuyOutDao;
    }

    /**
     * 打给商家的钱 = 买断金额*94%
     * @param splitBill
     * @param configDto
     * @param recordId
     */
    @Override
    public void fillSplitBillInfo(SplitBill splitBill, SpiltBillRuleConfigDto configDto, Long recordId) {
        UserOrderBuyOut userOrderBuyOut = userOrderBuyOutDao.getById(recordId);
        //买断金额*94%
        BigDecimal endFundPart = userOrderBuyOut.getEndFund().multiply(configDto.getScale()).setScale(2, BigDecimal.ROUND_HALF_UP);
        //设置打给商家的钱
        splitBill.setTransAmount(endFundPart);
        //更新买断订单分账时间
        userOrderBuyOutDao.updateStateSplitBill(recordId);
    }



}