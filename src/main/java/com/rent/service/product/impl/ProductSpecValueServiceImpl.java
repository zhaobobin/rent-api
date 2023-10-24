
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ProductSpecValueConverter;
import com.rent.common.dto.product.ProductSpecValueDto;
import com.rent.common.dto.product.ProductSpecValueReqDto;
import com.rent.common.dto.product.ShopProductSpecAllReqDto;
import com.rent.dao.order.PlatformSpecDao;
import com.rent.dao.product.ProductSkuValuesDao;
import com.rent.dao.product.ProductSpecDao;
import com.rent.dao.product.ProductSpecValueDao;
import com.rent.model.product.PlatformSpec;
import com.rent.model.product.ProductSkuValues;
import com.rent.model.product.ProductSpec;
import com.rent.model.product.ProductSpecValue;
import com.rent.service.product.ProductSpecValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品规格属性value表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:33
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSpecValueServiceImpl implements ProductSpecValueService {

    private final ProductSpecValueDao productSpecValueDao;
    private final ProductSpecDao productSpecDao;
    private final PlatformSpecDao platformSpecDao;
    private final ProductSkuValuesDao productSkuValuesDao;

    @Override
    public List<ShopProductSpecAllReqDto> selectProductSpecById(List<Long> ids) {
        List<ShopProductSpecAllReqDto> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            ids.forEach(id -> {
                ShopProductSpecAllReqDto shopProductSpecAllReqDto = new ShopProductSpecAllReqDto();
                ProductSpecValue productSpecValue = this.productSpecValueDao.getById(String.valueOf(id));
                ProductSpec productSpec = productSpecDao.getById(productSpecValue.getProductSpecId());
                PlatformSpec platformSpec = platformSpecDao.getById(productSpec.getSpecId());
                if (null != platformSpec) {
                    shopProductSpecAllReqDto.setPlatformSpecName(platformSpec.getName());
                }
                shopProductSpecAllReqDto.setPlatformSpecValue(productSpecValue.getName());
                shopProductSpecAllReqDto.setPlatformSpecId(productSpecValue.getId().intValue());
                shopProductSpecAllReqDto.setOpeSpecId(productSpec.getSpecId().intValue());
                result.add(shopProductSpecAllReqDto);
            });
            if(CollectionUtils.isNotEmpty(result)){
                Collections.sort(result, Comparator.comparing(ShopProductSpecAllReqDto::getOpeSpecId));
            }
        }
        return result;
    }

    @Override
    public String getProductSpecById(List<Long> ids) {
        StringBuffer sb = new StringBuffer();

        if (CollectionUtils.isNotEmpty(ids)) {
            ids.forEach(id -> {
                ShopProductSpecAllReqDto shopProductSpecAllReqDto = new ShopProductSpecAllReqDto();
                ProductSpecValue productSpecValue = this.productSpecValueDao.getById(String.valueOf(id));
                ProductSpec productSpec = productSpecDao.getById(productSpecValue.getProductSpecId());
                PlatformSpec platformSpec = platformSpecDao.getById(productSpec.getSpecId());
                if (null != platformSpec) {
                    shopProductSpecAllReqDto.setPlatformSpecName(platformSpec.getName());
                }
                shopProductSpecAllReqDto.setPlatformSpecValue(productSpecValue.getName());
                sb.append(productSpecValue.getName()).append("/").toString();
            });
        }
        return sb.toString();
    }

    @Override
    public List<String> getSpecName(Integer skuId) {
        List<ProductSkuValues> productSkuValues = productSkuValuesDao.list(new QueryWrapper<ProductSkuValues>().eq("sku_id", skuId).isNull("delete_time"));
        if (CollectionUtils.isNotEmpty(productSkuValues)) {
            List<Long> specValueIds = productSkuValues.stream().map(ProductSkuValues::getSpecValueId).collect(Collectors.toList());
            List<ProductSpecValue> specValues = this.productSpecValueDao.list(new QueryWrapper<ProductSpecValue>()
                    .in("id", specValueIds)
            );
            if (CollectionUtils.isNotEmpty(specValues)) {
                return specValues.stream().map(ProductSpecValue::getName).collect(Collectors.toList());
            }
        }
        return new ArrayList<>(0);
    }

    @Override
    public Long addProductSpecValue(ProductSpecValueDto request) {
        ProductSpecValue model = ProductSpecValueConverter.dto2Model(request);
        model.setCreateTime(new Date());
        if (productSpecValueDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Long rapairaddProductSpecValue(ProductSpecValueDto request) {
        ProductSpecValueDto dto = queryProductSpecValueDetail(ProductSpecValueReqDto.builder().name(request.getName()).productSpecId(request.getProductSpecId()).build());
        if(null != dto){
            return dto.getId();
        }else{
           return this.addProductSpecValue(request);
        }
    }

    @Override
    public ProductSpecValueDto queryProductSpecValueDetail(ProductSpecValueReqDto request) {
        ProductSpecValue productSpecValue = productSpecValueDao.getOne(new QueryWrapper<>(ProductSpecValueConverter.reqDto2Model(request)).isNull("delete_time").orderByDesc("id").last("limit 1"));
        return ProductSpecValueConverter.model2Dto(productSpecValue);
    }

}