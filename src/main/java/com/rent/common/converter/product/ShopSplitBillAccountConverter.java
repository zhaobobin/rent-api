
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.backstage.resp.SplitBillShopResp;
import com.rent.common.dto.product.ShopSplitBillAccountDto;
import com.rent.model.product.ShopSplitBillAccount;
import com.rent.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * 店铺分账账户Service
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
public class ShopSplitBillAccountConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ShopSplitBillAccountDto model2Dto(ShopSplitBillAccount model) {
        if (model == null) {
            return null;
        }
        ShopSplitBillAccountDto dto = new ShopSplitBillAccountDto();
        dto.setId(model.getId());
        dto.setShopId(model.getShopId());
        dto.setShopName(model.getShopName());
        dto.setShopFirmInfo(model.getShopFirmInfo());
        dto.setIdentity(model.getIdentity());
        dto.setName(model.getName());
        dto.setAddUser(model.getAddUser());
        dto.setStatus(model.getStatus());
        dto.setAuditUser(model.getAuditUser());
        dto.setAuditRemark(model.getAuditRemark());
        dto.setAuditTime(model.getAuditTime());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        if (StringUtil.isNotEmpty(model.getCycle())) {
            dto.setCycle(Arrays.asList(model.getCycle().split(",")));
        }
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ShopSplitBillAccount dto2Model(ShopSplitBillAccountDto dto) {
        if (dto == null) {
            return null;
        }
        ShopSplitBillAccount model = new ShopSplitBillAccount();
        model.setId(dto.getId());
        model.setShopId(dto.getShopId());
        model.setIdentity(dto.getIdentity());
        model.setName(dto.getName());
        model.setShopName(dto.getShopName());
        model.setShopFirmInfo(dto.getShopFirmInfo());
        model.setAddUser(dto.getAddUser());
        model.setStatus(dto.getStatus());
        model.setAuditUser(dto.getAuditUser());
        model.setAuditRemark(dto.getAuditRemark());
        model.setAuditTime(dto.getAuditTime());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setCycle(String.join(",", dto.getCycle()));
        return model;
    }


    public static List<SplitBillShopResp> modelsToShopDtoList(List<ShopSplitBillAccount> models) {
        if (CollectionUtil.isEmpty(models)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(Iterators.transform(models.iterator(), ShopSplitBillAccountConverter::modelToShopDto));
    }

    public static SplitBillShopResp modelToShopDto(ShopSplitBillAccount model) {
        SplitBillShopResp shopDto = new SplitBillShopResp();
        if (model == null) {
            return null;
        }
        shopDto.setShopFirmInfo(model.getShopFirmInfo());
        shopDto.setShopId(model.getShopId());
        shopDto.setShopName(model.getShopName());
        return shopDto;
    }


}