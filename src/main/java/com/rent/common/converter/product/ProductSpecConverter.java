        
package com.rent.common.converter.product;

import com.rent.common.dto.product.ProductSpecDto;
import com.rent.common.dto.product.ProductSpecReqDto;
import com.rent.model.product.ProductSpec;

/**
 * 商品属性规格表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:32
 */
public class ProductSpecConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductSpecDto model2Dto(ProductSpec model) {
        if (model == null) {
            return null;
        }
        ProductSpecDto dto = new ProductSpecDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setSpecId(model.getSpecId());
        dto.setItemId(model.getItemId());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ProductSpec dto2Model(ProductSpecDto dto) {
        if (dto == null) {
            return null;
        }
        ProductSpec model = new ProductSpec();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setSpecId(dto.getSpecId());
        model.setItemId(dto.getItemId());
        return model;
    }


    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static ProductSpec reqDto2Model(ProductSpecReqDto dto) {
        if (dto == null) {
            return null;
        }
        ProductSpec model = new ProductSpec();
            model.setId(dto.getId());
            model.setCreateTime(dto.getCreateTime());
            model.setUpdateTime(dto.getUpdateTime());
            model.setDeleteTime(dto.getDeleteTime());
            model.setSpecId(dto.getSpecId());
            model.setItemId(dto.getItemId());
        return model;
    }


}