package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.marketing.CubesConfigConverter;
import com.rent.common.dto.marketing.CubesConfigAddReqDto;
import com.rent.common.dto.marketing.CubesConfigListDto;
import com.rent.common.dto.product.ListProductDto;
import com.rent.dao.marketing.CubesConfigDao;
import com.rent.model.marketing.CubesConfig;
import com.rent.service.marketing.CubesConfigService;
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
public class CubesConfigServiceImpl implements CubesConfigService {

    private CubesConfigDao cubesConfigDao;
    private ProductService productService;

    @Autowired
    public CubesConfigServiceImpl(CubesConfigDao cubesConfigDao, ProductService productService) {
        this.cubesConfigDao = cubesConfigDao;
        this.productService = productService;
    }


    @Override
    public List<CubesConfigListDto> list(String channelId, String type) {
        List<CubesConfig> list = cubesConfigDao.list(new QueryWrapper<CubesConfig>()
                .isNull("delete_time")
                .orderByDesc("create_time"));
        List<CubesConfigListDto> result = CubesConfigConverter.modelList2DtoList(list);
        if("API".equals(type)){
            for(CubesConfigListDto cubesConfigListDto : result){
                List<ListProductDto> products =  productService.getProductInByIdsV1(cubesConfigListDto.getProductIds());
                cubesConfigListDto.setProducts(products);
            }
        }
        return result;
    }

    @Override
    public CubesConfigListDto detail(Long id) {
        CubesConfig cubesConfig = cubesConfigDao.getById(id);
        return CubesConfigConverter.model2Dto(cubesConfig);
    }

    @Override
    public Boolean add(CubesConfigAddReqDto dto) {
//        List<BannerConfig> list = bannerConfigDao.list(new QueryWrapper<BannerConfig>()
//                .eq("channel_id",dto.getChannelId()));
//        if(list.size() == 5){
//            throw new HzsxBizException("-1","最多添加5张Banner图片");
//        }
        CubesConfig cubesConfig = new CubesConfig();
        cubesConfig.setPaperwork(dto.getPaperwork());
        cubesConfig.setPaperworkCopy(dto.getPaperworkCopy());
        cubesConfig.setUrl(dto.getUrl());
        cubesConfig.setJumpUrl(dto.getJumpUrl());
        cubesConfig.setChannelId("000");
        cubesConfig.setCreateTime(new Date());
        cubesConfig.setProductIds(String.join(",",dto.getProductIds()));
        return cubesConfigDao.save(cubesConfig);
    }

    @Override
    public Boolean update(CubesConfigAddReqDto dto) {
        CubesConfig cubesConfig = cubesConfigDao.getById(dto.getId());
        cubesConfig.setUpdateTime(new Date());
        cubesConfig.setUrl(dto.getUrl());
        cubesConfig.setJumpUrl(dto.getJumpUrl());
        cubesConfig.setPaperwork(dto.getPaperwork());
        cubesConfig.setPaperworkCopy(dto.getPaperworkCopy());
        cubesConfig.setProductIds(String.join(",",dto.getProductIds()));
        return cubesConfigDao.updateById(cubesConfig);
    }


    @Override
    public Boolean delete(CubesConfigAddReqDto dto) {
        CubesConfig cubesConfig = cubesConfigDao.getById(dto.getId());
        cubesConfig.setDeleteTime(new Date());
        return cubesConfigDao.updateById(cubesConfig);
    }


}