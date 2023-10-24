        
package com.rent.common.converter.product;

import com.rent.common.dto.product.ProductSpecValueDto;
import com.rent.common.dto.product.ProductSpecValueReqDto;
import com.rent.model.product.ProductSpecValue;

/**
 * 商品规格属性value表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:33
 */
public class ProductSpecValueConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductSpecValueDto model2Dto(ProductSpecValue model) {
        if (model == null) {
            return null;
        }
        ProductSpecValueDto dto = new ProductSpecValueDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setName(model.getName());
        dto.setProductSpecId(model.getProductSpecId());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ProductSpecValue dto2Model(ProductSpecValueDto dto) {
        if (dto == null) {
            return null;
        }
        ProductSpecValue model = new ProductSpecValue();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setName(dto.getName());
        model.setProductSpecId(dto.getProductSpecId());
        return model;
    }
    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static ProductSpecValue reqDto2Model(ProductSpecValueReqDto dto) {
        if (dto == null) {
            return null;
        }
        ProductSpecValue model = new ProductSpecValue();
            model.setId(dto.getId());
            model.setCreateTime(dto.getCreateTime());
            model.setUpdateTime(dto.getUpdateTime());
            model.setDeleteTime(dto.getDeleteTime());
            model.setName(dto.getName());
            model.setProductSpecId(dto.getProductSpecId());
        return model;
    }


}