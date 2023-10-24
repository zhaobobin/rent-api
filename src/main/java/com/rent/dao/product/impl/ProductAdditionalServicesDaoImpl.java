package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductAdditionalServicesDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductAdditionalServicesDao;
import com.rent.mapper.product.ProductAdditionalServicesMapper;
import com.rent.model.product.ProductAdditionalServices;
import com.rent.model.product.ShopAdditionalServices;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * ProductAdditionalServicesDao
 *
 * @author youruo
 * @Date 2020-06-16 15:07
 */
@Repository
public class ProductAdditionalServicesDaoImpl
    extends AbstractBaseDaoImpl<ProductAdditionalServices, ProductAdditionalServicesMapper>
    implements ProductAdditionalServicesDao {

    @Override
    public Boolean deleteProductAdditionalServices(Integer shopAdditionalServicesId) {
        List<ProductAdditionalServices> list = this.baseMapper.selectList(
            new QueryWrapper<ProductAdditionalServices>().eq("shop_additional_services_id", shopAdditionalServicesId)
                .isNull("delete_time"));
        if (CollectionUtils.isNotEmpty(list)) {
            Date now = new Date();
            list.forEach(item -> {
                item.setDeleteTime(now);
                item.setUpdateTime(now);
                this.baseMapper.updateById(item);
            });
        }
        return Boolean.TRUE;
    }

    @Override
    public List<ProductAdditionalServices> batchSaveProductAdditional(List<ProductAdditionalServicesDto> servicesDtos, List<ShopAdditionalServices> afterAdditionals, String newestProductId) {
        List<ProductAdditionalServices> servicesList = Lists.newArrayList();
        ProductAdditionalServicesDto dto = servicesDtos.get(0);
        ProductAdditionalServices services = new ProductAdditionalServices();
        BeanUtils.copyProperties(dto, services);
        Date now = new Date();
        services.setCreateTime(now);
        services.setUpdateTime(now);
        services.setProductId(newestProductId);
        afterAdditionals.forEach(item -> {
            Integer id = item.getId();
            services.setShopAdditionalServicesId(item.getId());
            this.save(services);
            servicesList.add(services);
        });
        return servicesList;
    }
}
