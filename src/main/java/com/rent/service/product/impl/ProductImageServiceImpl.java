
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ProductImageConverter;
import com.rent.common.dto.product.ProductImageAddReqDto;
import com.rent.common.dto.product.ProductImageDto;
import com.rent.common.dto.product.ProductImageReqDto;
import com.rent.common.util.CheckDataUtils;
import com.rent.dao.product.ProductImageDao;
import com.rent.model.product.ProductImage;
import com.rent.service.product.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品主图表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageDao productImageDao;

    @Override
    public Long addProductImage(ProductImageDto request) {
        ProductImage model = ProductImageConverter.dto2Model(request);
        if (productImageDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public ProductImageDto queryProductImageDetail(ProductImageReqDto request) {
        ProductImage productImage = productImageDao.getOne(new QueryWrapper<>(ProductImageConverter.reqDto2Model(request)).isNull("delete_time").orderByAsc("create_time").last("limit 1"));
        return ProductImageConverter.model2Dto(productImage);
    }

    @Override
    public void insertProductImagesSort(String productId, List<ProductImageAddReqDto> images) {
        CheckDataUtils.judgeNull(productId);
        Date now = new Date();
        images.sort(Comparator.comparing(h -> String.valueOf(h.getIsMain())));
        images.forEach(item -> {
            addProductImage(ProductImageDto.builder()
                    .createTime(now)
                    .isMain(item.getIsMain())
                    .productId(productId)
                    .src(item.getSrc())
                    .build());
        });
    }

    @Override
    public void deleteProductImage(String productId) {
        QueryWrapper<ProductImage> wh = new QueryWrapper<>();
        wh.eq("product_id", productId);
        ProductImage ss = new ProductImage();
        ss.setDeleteTime(new Date());
        productImageDao.update(ss, wh);
    }

    @Override
    public List<ProductImageAddReqDto> selectProductImageByItemId(String productId) {
        if (StringUtils.isNotEmpty(productId)) {
            List<ProductImageAddReqDto> result = productImageDao.list(new QueryWrapper<ProductImage>()
                    .eq("product_id", productId)
                    .isNull("delete_time")
                    .orderByAsc("id")).stream().map(productImage -> ProductImageAddReqDto.builder()
                            .src(productImage.getSrc())
                            .isMain(productImage.getIsMain())
                            .build()).collect(Collectors.toList());
            return result;
        }
        return new ArrayList<>();
    }

    @Override
    public void insertProductImages(List<String> images, String productId) {
        CheckDataUtils.judgeNull(images, productId);
        for (int i = 0; i < images.size(); i++) {
            ProductImage productImage = new ProductImage();
            if (i == 0) {
                productImage.setProductId(productId);
                productImage.setIsMain(1);
                productImage.setSrc(images.get(i));
                productImage.setCreateTime(new Date());
            } else {
                productImage.setSrc(images.get(i));
                productImage.setCreateTime(new Date());
                productImage.setIsMain(0);
                productImage.setProductId(productId);
            }
            this.productImageDao.save(productImage);
        }
    }


}