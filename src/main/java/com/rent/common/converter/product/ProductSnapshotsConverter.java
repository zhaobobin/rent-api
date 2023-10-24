        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductSnapshotsDto;
import com.rent.model.product.ProductSnapshots;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品快照表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:30
 */
public class ProductSnapshotsConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductSnapshotsDto model2Dto(ProductSnapshots model) {
        if (model == null) {
            return null;
        }
        ProductSnapshotsDto dto = new ProductSnapshotsDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setItemId(model.getItemId());
        dto.setVersion(model.getVersion());
        dto.setData(model.getData());
        dto.setShopId(model.getShopId());
        dto.setStatus(model.getStatus());
        dto.setProductName(model.getProductName());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ProductSnapshotsDto> modelList2DtoList(List<ProductSnapshots> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ProductSnapshotsConverter::model2Dto));
    }
}