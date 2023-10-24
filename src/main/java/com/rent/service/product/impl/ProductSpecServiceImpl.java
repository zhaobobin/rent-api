
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.product.ProductSpecConverter;
import com.rent.common.dto.product.*;
import com.rent.dao.product.ProductSpecDao;
import com.rent.dao.product.ProductSpecValueDao;
import com.rent.model.product.ProductSpec;
import com.rent.service.product.ProductSkuValuesService;
import com.rent.service.product.ProductSpecService;
import com.rent.service.product.ProductSpecValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品属性规格表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:32
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSpecServiceImpl implements ProductSpecService {

    private final ProductSpecDao productSpecDao;
    private final ProductSpecValueService productSpecValueService;
    private final ProductSkuValuesService productSkuValuesService;
    private final ProductSpecValueDao productSpecValueDao;

    @Override
    public void insertSpec(String productId, Integer skuId, List<ShopProductSpecAllReqDto> specAll) {
        if (StringUtils.isNotEmpty(productId) && CollectionUtils.isNotEmpty(specAll)) {
            Date now = new Date();
            //去重
            specAll = specAll.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ShopProductSpecAllReqDto::getOpeSpecId))), ArrayList::new));
            specAll.forEach(item -> {
                Integer id = repairAddProductSpe(ProductSpecDto.builder()
                        .itemId(productId)
                        .specId(item.getOpeSpecId())
                        .build());
                //添加商品规格value
                Long specValueId = productSpecValueService.rapairaddProductSpecValue(ProductSpecValueDto.builder()
                        .name(item.getPlatformSpecValue())
                        .productSpecId(Long.parseLong(id.toString()))
                        .build());
                //添加库存规格属性
                productSkuValuesService.addProductSkuValues(ProductSkuValuesDto.builder()
                        .skuId(Long.parseLong(skuId.toString()))
                        .createTime(now)
                        .specValueId(specValueId)
                        .build());
            });
        }
    }

    @Override
    public void deleteSpec(String productId) {
        QueryWrapper<ProductSpec> wh = new QueryWrapper<>();
        wh.eq("item_id", productId);
        wh.isNull("delete_time");
        ProductSpec ss = new ProductSpec();
        ss.setDeleteTime(new Date());
        productSpecDao.update(ss, wh);
    }

    @Override
    public Integer addProductSpec(ProductSpecDto request) {
        ProductSpec model = ProductSpecConverter.dto2Model(request);
        model.setCreateTime(new Date());
        productSpecDao.save(model);
        return model.getId();
    }

    @Override
    public Integer repairAddProductSpe(ProductSpecDto request) {
        ProductSpecDto dto = this.queryProductSpecDetail(ProductSpecReqDto.builder().itemId(request.getItemId()).specId(request.getSpecId()).build());
        if (null != dto) {
            return dto.getId();
        } else {
            return this.addProductSpec(request);
        }
    }

    @Override
    public ProductSpecDto queryProductSpecDetail(ProductSpecReqDto request) {
        ProductSpec productSpec = productSpecDao.getOne(new QueryWrapper<>(ProductSpecConverter.reqDto2Model(request)).isNull("delete_time").orderByDesc("id").last("limit 1"));
        return ProductSpecConverter.model2Dto(productSpec);
    }


    @Override
    public List<SpecsDto> queryspecs(String itemId) {
        List<SpecsDto> specsdtoList = new ArrayList<>();
        List<Map> opeSpecs = this.productSpecDao.selectProductopeSpecId(itemId);
        if (CollectionUtils.isNotEmpty(opeSpecs)) {
            opeSpecs.forEach(map -> {
                SpecsDto specsDto = new SpecsDto();
                Number num = (Number) map.get("id");
                specsDto.setId(null != num ? num.intValue() : null);
                Number opeSpecId = (Number) map.get("opeSpecId");
                specsDto.setOpeSpecId(null != opeSpecId ? opeSpecId.intValue() : null);
                specsDto.setName(map.get("name") == null ? "" : map.get("name").toString());
                List<Integer> specs = this.productSpecDao.selectProductopeSpecInfo(itemId, opeSpecId.intValue());
                List<ProductSpecValueDto> productSpecValue = productSpecValueDao.selectBySpecIds(specs);
                specsDto.setValues(productSpecValue);
                specsdtoList.add(specsDto);
            });
            if (CollectionUtils.isNotEmpty(specsdtoList)) {
                Collections.sort(specsdtoList, Comparator.comparing(SpecsDto::getOpeSpecId));
            }
        }
        return specsdtoList;
    }
}