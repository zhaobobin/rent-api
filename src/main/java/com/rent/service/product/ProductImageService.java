
package com.rent.service.product;

import com.rent.common.dto.product.ProductImageAddReqDto;
import com.rent.common.dto.product.ProductImageDto;
import com.rent.common.dto.product.ProductImageReqDto;

import java.util.List;

/**
 * 商品主图表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:16
 */
public interface ProductImageService {

    /**
     * 新增商品主图表
     *
     * @param request 条件
     * @return boolean
     */
    Long addProductImage(ProductImageDto request);


    /**
     * <p>
     * 根据条件查询一条记录
     * </p>
     *
     * @param request 实体对象
     * @return ProductImage
     */
    ProductImageDto queryProductImageDetail(ProductImageReqDto request);

    /**
     * 插入商品图片--批量-排序
     *
     * @param productId
     * @param images
     */
    void insertProductImagesSort(String productId, List<ProductImageAddReqDto> images);

    /**
     * 删除商品图片
     * @param productId
     */
    void deleteProductImage(String productId);


    /**
     * 查询商品主图
     * @param productId
     * @return
     */
    List<ProductImageAddReqDto> selectProductImageByItemId(String productId);

    /**
     * 插入商品图片
     *
     * @param images    图片oos连接
     * @param productId 商品id
     */
    void insertProductImages(List<String> images, String productId);


}