        
package com.rent.common.converter.product;

import com.rent.common.dto.product.ProductLabelDto;
import com.rent.model.product.ProductLabel;


/**
 * 商品租赁标签Service
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
public class ProductLabelConverter {

    /**
     * dto转do
     * @param dto
     * @return
     */
    public static ProductLabel dto2Model(ProductLabelDto dto) {
        if (dto == null) {
            return null;
        }
        ProductLabel model = new ProductLabel();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setLabel(dto.getLabel());
        model.setItemId(dto.getItemId());
        return model;
    }
}