
package com.rent.common.converter.product;

import com.rent.common.dto.product.ProductSkusDto;
import com.rent.model.product.ProductSkus;

/**
 * 商品sku表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:27
 */
public class ProductSkusConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductSkusDto model2Dto(ProductSkus model) {
        if (model == null) {
            return null;
        }
        ProductSkusDto dto = new ProductSkusDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setItemId(model.getItemId());
        dto.setMarketPrice(model.getMarketPrice());
        dto.setInventory(model.getInventory());
        dto.setTotalInventory(model.getTotalInventory());
        dto.setOldNewDegree(model.getOldNewDegree());
        dto.setBuyOutSupport(model.getBuyOutSupport());
        dto.setSalePrice(model.getSalePrice());
        dto.setIsSupportStage(model.getIsSupportStage());
        dto.setDepositPrice(model.getDepositPrice());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ProductSkus dto2Model(ProductSkusDto dto) {
        if (dto == null) {
            return null;
        }
        ProductSkus model = new ProductSkus();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setItemId(dto.getItemId());
        model.setMarketPrice(dto.getMarketPrice());
        model.setInventory(dto.getInventory());
        model.setTotalInventory(dto.getTotalInventory());
        model.setOldNewDegree(dto.getOldNewDegree());
        model.setBuyOutSupport(dto.getBuyOutSupport());
        model.setSalePrice(dto.getSalePrice());
        model.setDepositPrice(dto.getDepositPrice());
        model.setIsSupportStage(dto.getIsSupportStage());
        return model;
    }
}