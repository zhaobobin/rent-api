        
package com.rent.service.order.impl;

import com.rent.common.dto.product.SpiltBillRuleConfigDto;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.SplitBillDao;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.SplitBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author zhaowenchao
 */
@Service("GenerateRentMonthSplitBillImplService")
public class GenerateRentMonthSplitBillImplService extends
    GenerateSplitBillImplService {

    private OrderByStagesDao orderByStagesDao;
    @Autowired
    public GenerateRentMonthSplitBillImplService(SplitBillDao splitBillDao, OrderByStagesDao orderByStagesDao) {
        super(splitBillDao);
        this.orderByStagesDao = orderByStagesDao;
    }

    @Override
    public void fillSplitBillInfo(SplitBill splitBill, SpiltBillRuleConfigDto configDto, Long recordId) {
        OrderByStages orderByStages = orderByStagesDao.getById(recordId);
        BigDecimal paidAmount = orderByStages.getCurrentPeriodsRent();
        BigDecimal transAmount = paidAmount.multiply(configDto.getScale()).setScale(2,BigDecimal.ROUND_HALF_UP);
        splitBill.setTransAmount(transAmount);

        orderByStagesDao.updateSplitBillTime(recordId);
    }
}