
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.product.OpeIndexShopBannerConverter;
import com.rent.common.dto.product.OpeIndexShopBannerDto;
import com.rent.common.dto.product.OpeIndexShopBannerReqDto;
import com.rent.dao.product.OpeIndexShopBannerDao;
import com.rent.model.product.OpeIndexShopBanner;
import com.rent.service.product.OpeIndexShopBannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商家详情轮播配置Service
 *
 * @author youruo
 * @Date 2020-07-23 17:37
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpeIndexShopBannerServiceImpl implements OpeIndexShopBannerService {

    private final OpeIndexShopBannerDao opeIndexShopBannerDao;

    @Override
    public Boolean addOpeIndexShopBanner(OpeIndexShopBannerDto request) {
        OpeIndexShopBanner model = OpeIndexShopBannerConverter.dto2Model(request);
        model.setCreateTime(new Date());
        if (opeIndexShopBannerDao.save(model)) {
            return Boolean.TRUE;
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Boolean modifyOpeIndexShopBanner(OpeIndexShopBannerDto request) {
        request.setUpdateTime(new Date());
        return opeIndexShopBannerDao.updateById(OpeIndexShopBannerConverter.dto2Model(request));
    }

    @Override
    public Boolean deleteOpeIndexShopBanner(Long id) {
        Date now = new Date();
        OpeIndexShopBanner banner = new OpeIndexShopBanner();
        banner.setId(id);
        banner.setDeleteTime(now);
        banner.setUpdateTime(now);
        opeIndexShopBannerDao.updateById(banner);
        return Boolean.TRUE;
    }

    @Override
    public List<OpeIndexShopBannerDto> queryOpeIndexShopBannerList(String shopId) {
        List<OpeIndexShopBanner> request = this.opeIndexShopBannerDao.list(new QueryWrapper<OpeIndexShopBanner>().eq("shop_id", shopId).isNull("delete_time").orderByAsc("index_sort"));
        return OpeIndexShopBannerConverter.modelList2DtoList(request);
    }

    @Override
    public Page<OpeIndexShopBannerDto> queryOpeIndexShopBannerPage(OpeIndexShopBannerReqDto request) {
        Page<OpeIndexShopBanner> page = opeIndexShopBannerDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<OpeIndexShopBanner>()
                        .isNull("delete_time")
                        .eq("shop_id", request.getShopId())
                        .orderByAsc("index_sort")
        );
        return new Page<OpeIndexShopBannerDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                OpeIndexShopBannerConverter.modelList2DtoList(page.getRecords()));
    }


}