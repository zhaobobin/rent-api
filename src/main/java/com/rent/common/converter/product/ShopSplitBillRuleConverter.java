        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ShopSplitBillRuleDto;
import com.rent.common.dto.product.SplitBillConfigListDto;
import com.rent.model.product.ShopSplitBillAccount;
import com.rent.model.product.ShopSplitBillRule;
import com.rent.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * 店铺分账规则Service
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
public class ShopSplitBillRuleConverter {



    public static List<SplitBillConfigListDto> models2ListDtoList(List<ShopSplitBillAccount> modelList) {
        if(CollectionUtil.isEmpty(modelList)){
            return Collections.emptyList();
        }
        List<SplitBillConfigListDto> listDtoList = new ArrayList<>(modelList.size());
        for (ShopSplitBillAccount model : modelList) {
            SplitBillConfigListDto dto = new SplitBillConfigListDto();
            dto.setId(model.getId());
            dto.setShopId(model.getShopId());
            dto.setShopName(model.getShopName());
            dto.setShopFirmInfo(model.getShopFirmInfo());
            dto.setAddUser(model.getAddUser());
            dto.setCreateTime(model.getCreateTime());
            dto.setCreateTime(model.getCreateTime());
            dto.setStatus(model.getStatus());
            dto.setAddUser(model.getAddUser());
            if(StringUtil.isNotEmpty(model.getCycle())){
                dto.setCycle(Arrays.asList(model.getCycle().split(",")));
            }else {
                dto.setCycle(Collections.EMPTY_LIST);
            }
            listDtoList.add(dto);
        }
        return listDtoList;
    }

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ShopSplitBillRuleDto model2Dto(ShopSplitBillRule model) {
        if (model == null) {
            return null;
        }
        ShopSplitBillRuleDto dto = new ShopSplitBillRuleDto();
        dto.setId(model.getId());
        dto.setAccountId(model.getAccountId());
        dto.setType(model.getType());
        dto.setDelayNum(model.getDelayNum());
        dto.setDelayType(model.getDelayType());
        dto.setScale(model.getScale());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ShopSplitBillRule dto2Model(ShopSplitBillRuleDto dto) {
        if (dto == null) {
            return null;
        }
        ShopSplitBillRule model = new ShopSplitBillRule();
        model.setId(dto.getId());
        model.setAccountId(dto.getAccountId());
        model.setType(dto.getType());
        model.setDelayNum(dto.getDelayNum());
        model.setDelayType(dto.getDelayType());
        model.setScale(dto.getScale());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ShopSplitBillRuleDto> modelList2DtoList(List<ShopSplitBillRule> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ShopSplitBillRuleConverter::model2Dto));
    }

    /**
     * dtoList转doList
     *
     * @param dtoList
     * @return
     */
    public static List<ShopSplitBillRule> dtoList2ModelList(List<ShopSplitBillRuleDto> dtoList) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(dtoList.iterator(), ShopSplitBillRuleConverter::dto2Model));
    }


}