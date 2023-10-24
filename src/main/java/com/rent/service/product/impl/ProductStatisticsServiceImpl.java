package com.rent.service.product.impl;

import com.alibaba.fastjson.JSON;
import com.rent.common.dto.product.ProductCountsDto;
import com.rent.common.dto.product.ShopProductSerachReqDto;
import com.rent.dao.product.ProductDao;
import com.rent.service.product.ProductStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2020-08-05 17:23
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductStatisticsServiceImpl implements ProductStatisticsService {

    private final ProductDao productDao;

    @Override
    public ProductCountsDto selectProductCounts() {
        Map<String, Object>  map = productDao.selectProductCounts();
        ProductCountsDto product = JSON.parseObject(JSON.toJSONString(map), ProductCountsDto.class);
        return product;
    }

    @Override
    public ProductCountsDto selectBusinessPrdouctCounts(ShopProductSerachReqDto model) {
        Map<String, Object>  map = productDao.selectBusinessPrdouctCounts(model);
        ProductCountsDto product = JSON.parseObject(JSON.toJSONString(map), ProductCountsDto.class);
        return product;
    }
}
