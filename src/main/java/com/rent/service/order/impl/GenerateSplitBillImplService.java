        
package com.rent.service.order.impl;

import com.rent.common.constant.SplitBillRecordConstant;
import com.rent.common.dto.product.SpiltBillRuleConfigDto;
import com.rent.common.enums.order.EnumSerialModalName;
import com.rent.common.util.SequenceUtil;
import com.rent.dao.order.SplitBillDao;
import com.rent.model.order.SplitBill;
import com.rent.service.order.GenerateSplitBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhaowenchao
 */
public abstract class GenerateSplitBillImplService implements GenerateSplitBillService {

    protected SplitBillDao splitBillDao;
    @Autowired
    public GenerateSplitBillImplService(SplitBillDao splitBillDao) {
        this.splitBillDao = splitBillDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generate(SpiltBillRuleConfigDto configDto, Long recordId,
                         String shopId, BigDecimal userPayAmount, Date userPayTime, String type, String orderId, Integer period, String uid) {
        SplitBill splitBill = new SplitBill();
        String spiltBillOrderId = SequenceUtil.getTypeSerialNo(EnumSerialModalName.SPLIT_BILL);
        splitBill.setOrderId(orderId);
        splitBill.setPeriod(period);
        splitBill.setUid(uid);
        splitBill.setShopId(shopId);
        splitBill.setType(type);
        splitBill.setOrderNo(spiltBillOrderId);
        splitBill.setSplitBillRuleId(configDto.getRuleId());
        splitBill.setIdentity(configDto.getIdentity());
        splitBill.setName(configDto.getName());
        splitBill.setScale(configDto.getScale());
        splitBill.setUserPayAmount(userPayAmount);
        splitBill.setUserPayTime(userPayTime);
        splitBill.setAppVersion(configDto.getAppVersion());
        fillSplitBillInfo(splitBill,configDto,recordId);
        splitBill.setStatus(SplitBillRecordConstant.STATUS_WAITING_SETTLEMENT);
        Date now = new Date();
        splitBill.setCreateTime(now);
        splitBill.setUpdateTime(now);
        splitBillDao.save(splitBill);
    }

    /**
     * 填充分账信息 -transAmount
     * @param splitBill
     * @param configDto
     * @param recordId
     */
    public abstract void fillSplitBillInfo(SplitBill splitBill,SpiltBillRuleConfigDto configDto,Long recordId);


}