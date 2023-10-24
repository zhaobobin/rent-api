
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductDto;
import com.rent.model.product.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:06
 */
public class ProductConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductDto model2Dto(Product model) {
        if (model == null) {
            return null;
        }
        ProductDto dto = new ProductDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setName(model.getName());
        dto.setProductId(model.getProductId());
        dto.setCategoryId(model.getCategoryId());
        dto.setShopId(model.getShopId());
        dto.setDetail(model.getDetail());
        dto.setType(model.getType());
        dto.setStatus(model.getStatus());
        dto.setShopStatus(model.getShopStatus());
        dto.setProvince(model.getProvince());
        dto.setCity(model.getCity());
        dto.setMinRentCycle(model.getMinRentCycle());
        dto.setMaxRentCycle(model.getMaxRentCycle());
        dto.setMinAdvancedDays(model.getMinAdvancedDays());
        dto.setMaxAdvancedDays(model.getMaxAdvancedDays());
        dto.setAuditState(model.getAuditState());
        dto.setAuditRefuseReason(model.getAuditRefuseReason());
        dto.setSalesVolume(model.getSalesVolume());
        dto.setMonthlySalesVolume(model.getMonthlySalesVolume());
        dto.setOldNewDegree(model.getOldNewDegree());
        dto.setSale(model.getSale());
        dto.setBuyOutSupport(model.getBuyOutSupport());
        dto.setFreightType(model.getFreightType());
        dto.setReturnRule(model.getReturnRule());
        dto.setReturnfreightType(model.getReturnFreightType());
        dto.setIsOnLine(model.getIsOnLine());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ProductDto> modelList2DtoList(List<Product> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ProductConverter::model2Dto));
    }
}