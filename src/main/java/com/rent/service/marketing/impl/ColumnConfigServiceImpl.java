package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.marketing.ColumnConfigConverter;
import com.rent.common.dto.marketing.ColumnConfigListDto;
import com.rent.common.dto.marketing.ColumnConfigReqDto;
import com.rent.common.dto.product.ListProductDto;
import com.rent.dao.marketing.ColumnConfigDao;
import com.rent.model.marketing.ColumnConfig;
import com.rent.service.marketing.ColumnConfigService;
import com.rent.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xiaotong
 */
@Service
@Slf4j
public class ColumnConfigServiceImpl implements ColumnConfigService {

    private ColumnConfigDao columnConfigDao;
    private ProductService productService;

    @Autowired
    public ColumnConfigServiceImpl(ColumnConfigDao columnConfigDao, ProductService productService) {
        this.columnConfigDao = columnConfigDao;
        this.productService = productService;
    }


    @Override
    public List<ColumnConfigListDto> list(String channelId, String type) {
        List<ColumnConfig> list = columnConfigDao.list(new QueryWrapper<ColumnConfig>()
                .eq("type","COLUMN")
                .orderByDesc("create_time"));
        List<ColumnConfigListDto> result = ColumnConfigConverter.modelList2DtoList(list);
        if("API".equals(type)){
            for(ColumnConfigListDto columnConfigListDto : result){
                List<ListProductDto> products =  productService.getProductInByIds(columnConfigListDto.getProductIds());
                columnConfigListDto.setProducts(products);
            }
        }
        return result;
    }

    @Override
    public ColumnConfigListDto detail(Long id) {
        ColumnConfig columnConfig = columnConfigDao.getById(id);
        return ColumnConfigConverter.model2Dto(columnConfig);
    }

    @Override
    public Boolean update(ColumnConfigReqDto dto) {
        ColumnConfig columnConfig = columnConfigDao.getById(dto.getId());
        columnConfig.setName(dto.getName());
        columnConfig.setUrl(dto.getUrl());
        columnConfig.setUpdateTime(new Date());
        columnConfig.setProductIds(String.join(",",dto.getProductIds()));
        return columnConfigDao.updateById(columnConfig);
    }
}