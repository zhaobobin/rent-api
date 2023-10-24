
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductAuditLogDto;
import com.rent.model.product.ProductAuditLog;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品审核日志表Service
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
public class ProductAuditLogConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductAuditLogDto model2Dto(ProductAuditLog model) {
        if (model == null) {
            return null;
        }
        ProductAuditLogDto dto = new ProductAuditLogDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setAuditStatus(model.getAuditStatus());
        dto.setFeedBack(model.getFeedBack());
        dto.setOperator(model.getOperator());
        dto.setItemId(model.getItemId());
        return dto;
    }


    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ProductAuditLogDto> modelList2DtoList(List<ProductAuditLog> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ProductAuditLogConverter::model2Dto));
    }



}