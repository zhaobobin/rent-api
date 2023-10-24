
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.product.ShopEnterpriseInfosConverter;
import com.rent.common.dto.product.EnterpriseInfoDto;
import com.rent.common.dto.product.ShopEnterpriseCertificatesDto;
import com.rent.common.dto.product.ShopEnterpriseInfosDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ShopEnterpriseCertificatesDao;
import com.rent.dao.product.ShopEnterpriseInfosDao;
import com.rent.dao.user.DistrictDao;
import com.rent.mapper.product.ShopEnterpriseInfosMapper;
import com.rent.model.product.ShopEnterpriseInfos;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ShopEnterpriseInfosDao
 *
 * @author youruo
 * @Date 2020-06-17 10:46
 */
@Repository
public class ShopEnterpriseInfosDaoImpl extends AbstractBaseDaoImpl<ShopEnterpriseInfos, ShopEnterpriseInfosMapper> implements ShopEnterpriseInfosDao {


    private DistrictDao districtDao;
    private ShopEnterpriseCertificatesDao shopEnterpriseCertificatesDao;

    public ShopEnterpriseInfosDaoImpl(DistrictDao districtDao, ShopEnterpriseCertificatesDao shopEnterpriseCertificatesDao) {
        this.districtDao = districtDao;
        this.shopEnterpriseCertificatesDao = shopEnterpriseCertificatesDao;
    }


    @Override
    public EnterpriseInfoDto getShopEnterpriseInfosByShopId(String shopId) {
        EnterpriseInfoDto enterpriseInfoDto = new EnterpriseInfoDto();
        ShopEnterpriseInfos shop = this.baseMapper.selectOne(new QueryWrapper<ShopEnterpriseInfos>()
                .eq("shop_id", shopId)
                .last("limit 1"));
        ShopEnterpriseInfosDto shopEnterpriseInfos = ShopEnterpriseInfosConverter.model2Dto(shop);
        if (null != shop) {
            Integer seInfoId = shopEnterpriseInfos.getId();
            String city = (null != shopEnterpriseInfos.getLicenseCity() ? shopEnterpriseInfos.getLicenseCity().toString() : null);
            String prince = (null != shopEnterpriseInfos.getLicenseProvince() ? shopEnterpriseInfos.getLicenseProvince().toString() : null);
            shopEnterpriseInfos.setLicenseCityStr(districtDao.getNameByDistrictId(city));
            shopEnterpriseInfos.setLicenseProvinceStr(districtDao.getNameByDistrictId(prince));
            enterpriseInfoDto.setShopEnterpriseInfos(shopEnterpriseInfos);
            List<ShopEnterpriseCertificatesDto> shopEnterpriseCertificates = shopEnterpriseCertificatesDao.selectShopEnterpriseCertificatesBySeInfoId(seInfoId);
            enterpriseInfoDto.setShopEnterpriseCertificates(shopEnterpriseCertificates);
        }
        return enterpriseInfoDto;
    }

    @Override
    public ShopEnterpriseInfos getByShopId(String shopId) {
        return getOne(new QueryWrapper<ShopEnterpriseInfos>().eq("shop_id", shopId).isNull("delete_time"));
    }
}
