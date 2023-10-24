
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.product.ShopEnterpriseInfosConverter;
import com.rent.common.dto.product.ShopEnterpriseInfosDto;
import com.rent.dao.product.ShopDao;
import com.rent.dao.product.ShopEnterpriseCertificatesDao;
import com.rent.dao.product.ShopEnterpriseInfosDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.ShopEnterpriseInfos;
import com.rent.service.product.ShopEnterpriseInfosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 店铺资质表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:46
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ShopEnterpriseInfosServiceImpl implements ShopEnterpriseInfosService {

    private final ShopEnterpriseInfosDao shopEnterpriseInfosDao;

    @Override
    public ShopEnterpriseInfosDto queryShopEnterpriseInfosDetailByshopId(String shopId) {
        ShopEnterpriseInfos shopEnterpriseInfos = shopEnterpriseInfosDao.getOne(new QueryWrapper<ShopEnterpriseInfos>().eq("shop_id",shopId).isNull("delete_time").orderByDesc("create_time").last("limit 0,1"));
        return ShopEnterpriseInfosConverter.model2Dto(shopEnterpriseInfos);
    }

    @Override
    public Boolean toShopEnterpriseExamineConform(Integer id, Integer status, String reason) {
        try {
            // 企业资质审核
            ShopEnterpriseInfos shopEnterpriseInfos = this.shopEnterpriseInfosDao.getOne(new QueryWrapper<ShopEnterpriseInfos>()
                    .eq("id", id)
                    .isNull("delete_time")
            );
            if (shopEnterpriseInfos == null) {
                throw new HzsxBizException("-1","shopEnterpriseInfos not exist");
            }
            shopEnterpriseInfos.setUpdateTime(new Date());
            shopEnterpriseInfos.setStatus(status);
            shopEnterpriseInfos.setReason(reason);
            this.shopEnterpriseInfosDao.updateById(shopEnterpriseInfos);
        } catch (Exception e) {
            log.error("ShopEnterpriseCertificatesServiceImpl.toShopEnterpriseExamineConform 异常" + e.getMessage());
            throw new HzsxBizException("-1","ShopEnterpriseCertificatesServiceImpl.toShopEnterpriseExamineConform 异常");
        }
        return true;
    }
}