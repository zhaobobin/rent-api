        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ShopFundFlowDto;
import com.rent.common.dto.product.ShopSplitBillAccountDto;
import com.rent.config.outside.OutsideConfig;
import com.rent.model.product.ShopFundFlow;
import com.rent.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author zhaowenchao
 */
public class ShopFundFlowConverter {

    /**
     * model转dto
     * @param model
     * @return
     */
    public static ShopFundFlowDto model2Dto(ShopFundFlow model) {
        if (model == null) {
            return null;
        }
        ShopFundFlowDto dto = new ShopFundFlowDto();
        dto.setId(model.getId());
        dto.setShopId(model.getShopId());
        dto.setOperate(model.getOperate());
        dto.setOperator(model.getOperator());
        dto.setChangeAmount(model.getChangeAmount());
        dto.setBeforeAmount(model.getBeforeAmount());
        dto.setAfterAmount(model.getAfterAmount());
        dto.setFlowNo(model.getFlowNo());
        dto.setRemark(model.getRemark());
        dto.setCreateTime(model.getCreateTime());
        return dto;
    }

    /**
     * dto转model
     * @param dto
     * @return
     */
    public static ShopFundFlow dto2Model(ShopFundFlowDto dto) {
        if (dto == null) {
            return null;
        }
        ShopFundFlow model = new ShopFundFlow();
        model.setId(dto.getId());
        model.setShopId(dto.getShopId());
        model.setOperate(dto.getOperate());
        model.setOperator(dto.getOperator());
        model.setChangeAmount(dto.getChangeAmount());
        model.setBeforeAmount(dto.getBeforeAmount());
        model.setAfterAmount(dto.getAfterAmount());
        model.setFlowNo(dto.getFlowNo());
        model.setFlowNo(dto.getFlowNo());
        model.setCreateTime(dto.getCreateTime());
        return model;
    }

    public static String generateWithDrawRemark(ShopSplitBillAccountDto accountDto,ShopFundFlow flow) {
        JSONObject remark = new JSONObject();
        remark.put("fromAccountName", OutsideConfig.COMPANY);
        remark.put("fromAccountIdentity", OutsideConfig.ALIPAY_ACCOUNT);
        remark.put("toAccountName",accountDto.getName());
        remark.put("toAccountIdentity",accountDto.getIdentity());
        remark.put("amount",flow.getChangeAmount());
        remark.put("serviceFee","0");
        remark.put("remark", OutsideConfig.APP_NAME+"提现-"+ DateUtil.getDate(new Date(),"yyyy年MM月dd日"));
        remark.put("title", OutsideConfig.APP_NAME+"提现");
        remark.put("orderNo",flow.getFlowNo());
        return remark.toJSONString();
    }

    public static String generateWithRechargeRemark(String buyerId,ShopFundFlow flow) {
        JSONObject remark = new JSONObject();
        remark.put("toAccountName", OutsideConfig.COMPANY);
        remark.put("toAccountIdentity", OutsideConfig.ALIPAY_ACCOUNT);
        remark.put("fromAccountIdentity",buyerId);
        remark.put("fromAccountName",buyerId);
        remark.put("amount",flow.getChangeAmount());
        remark.put("serviceFee","0");
        remark.put("remark","充值至"+ OutsideConfig.APP_NAME+"-"+ DateUtil.getDate(new Date(),"yyyy年MM月dd日"));
        remark.put("title","充值至"+ OutsideConfig.APP_NAME);
        remark.put("orderNo",flow.getFlowNo());
        return remark.toJSONString();
    }


    /**
     * modelList转dtoList
     * @param modelList
     * @return
     */
    public static List<ShopFundFlowDto> modelList2DtoList(List<ShopFundFlow> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ShopFundFlowConverter::model2Dto));
    }

}