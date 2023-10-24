        
package com.rent.service.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.OpeIndexShopBannerDto;
import com.rent.common.dto.product.OpeIndexShopBannerReqDto;

import java.util.List;

/**
 * 商家详情轮播配置Service
 *
 * @author youruo
 * @Date 2020-07-23 17:37
 */
public interface OpeIndexShopBannerService {

        /**
         * 新增商家详情轮播配置
         *
         * @param request 条件
         * @return boolean
         */
        Boolean addOpeIndexShopBanner(OpeIndexShopBannerDto request);

        /**
         * 根据 ID 修改商家详情轮播配置
         *
         * @param request 条件
         * @return String
         */
        Boolean modifyOpeIndexShopBanner(OpeIndexShopBannerDto request);

        /**
         * 删除营销图配置
         * @param id
         * @return
         */
        Boolean deleteOpeIndexShopBanner(Long id);

        /**
         * <p>
         * 根据条件查询一条记录
         * </p>
         *
         * @return OpeIndexShopBanner
         */
        List<OpeIndexShopBannerDto> queryOpeIndexShopBannerList(String shopId);

        /**
         * <p>
         * 根据条件列表
         * </p>
         *
         * @param request 实体对象
         * @return OpeIndexShopBanner
         */
        Page<OpeIndexShopBannerDto> queryOpeIndexShopBannerPage(OpeIndexShopBannerReqDto request);


}