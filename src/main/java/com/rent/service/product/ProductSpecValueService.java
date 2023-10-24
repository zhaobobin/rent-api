
package com.rent.service.product;

import com.rent.common.dto.product.ProductSpecValueDto;
import com.rent.common.dto.product.ProductSpecValueReqDto;
import com.rent.common.dto.product.ShopProductSpecAllReqDto;

import java.util.List;

/**
 * 商品规格属性value表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:33
 */
public interface ProductSpecValueService {

    /**
     * 获取商品规格属性
     *
     * @param ids
     * @return
     */
    List<ShopProductSpecAllReqDto> selectProductSpecById(List<Long> ids);

    String getProductSpecById(List<Long> ids);

    /**
     * 获取库存商品规格名称+
     *
     * @param skuId
     * @return
     */
    List<String> getSpecName(Integer skuId);

    /**
     * 新增商品规格属性value表
     *
     * @param request 条件
     * @return boolean
     */
    Long addProductSpecValue(ProductSpecValueDto request);

    Long rapairaddProductSpecValue(ProductSpecValueDto request);

    /**
     * <p>
     * 根据条件查询一条记录
     * </p>
     *
     * @param request 实体对象
     * @return ProductSpecValue
     */
    ProductSpecValueDto queryProductSpecValueDetail(ProductSpecValueReqDto request);



}