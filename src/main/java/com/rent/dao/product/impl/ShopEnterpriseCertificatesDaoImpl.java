    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.product.ShopEnterpriseCertificatesConverter;
import com.rent.common.dto.product.ShopEnterpriseCertificatesDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ShopEnterpriseCertificatesDao;
import com.rent.mapper.product.ShopEnterpriseCertificatesMapper;
import com.rent.model.product.ShopEnterpriseCertificates;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ShopEnterpriseCertificatesDao
 *
 * @author youruo
 * @Date 2020-06-17 10:45
 */
@Repository
public class ShopEnterpriseCertificatesDaoImpl extends AbstractBaseDaoImpl<ShopEnterpriseCertificates, ShopEnterpriseCertificatesMapper> implements ShopEnterpriseCertificatesDao {


    @Override
    public String getShopEnterpriseCertificatesSrc(Integer seInfoId, Integer type) {
        ShopEnterpriseCertificates shopEnterpriseCertificates =  this.baseMapper.selectOne(new QueryWrapper<ShopEnterpriseCertificates>()
                .eq("se_info_id",seInfoId)
                .eq("type",type)
                .isNull("delete_time")
                .last("limit 1")
        );
        if(null != shopEnterpriseCertificates){
            return shopEnterpriseCertificates.getImage();
        }
        return null;
    }

    @Override
    public List<ShopEnterpriseCertificatesDto> selectShopEnterpriseCertificatesBySeInfoId(Integer seInfoId) {
        List<ShopEnterpriseCertificates> model =  this.baseMapper.selectList(new QueryWrapper<ShopEnterpriseCertificates>()
                .eq("se_info_id",seInfoId)
                .isNull("delete_time")
        );
        return ShopEnterpriseCertificatesConverter.modelList2DtoList(model);
    }

}
