        
package com.rent.dao.product;

import com.rent.common.dto.product.ShopEnterpriseCertificatesDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ShopEnterpriseCertificates;

import java.util.List;


/**
 * ShopEnterpriseCertificatesDao
 *
 * @author youruo
 * @Date 2020-06-17 10:45
 */
public interface ShopEnterpriseCertificatesDao extends IBaseDao<ShopEnterpriseCertificates> {


    /**
     * 获取企业资质图片信息
     * @param seInfoId
     * @param type
     * @return
     */
    String getShopEnterpriseCertificatesSrc(Integer seInfoId,Integer type);

    /**
     * 获取企业资质图片集合
     * @param seInfoId
     * @return
     */
    List<ShopEnterpriseCertificatesDto> selectShopEnterpriseCertificatesBySeInfoId(Integer seInfoId);
}
