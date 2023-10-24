    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.PlatformServiceMarksDao;
import com.rent.dao.product.ProductServiceMarksDao;
import com.rent.mapper.product.ProductServiceMarksMapper;
import com.rent.model.product.PlatformServiceMarks;
import com.rent.model.product.ProductServiceMarks;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductServiceMarksDao
 *
 * @author youruo
 * @Date 2020-06-22 10:39
 */
@Repository
public class ProductServiceMarksDaoImpl extends AbstractBaseDaoImpl<ProductServiceMarks, ProductServiceMarksMapper> implements
    ProductServiceMarksDao {

    @Autowired
    private PlatformServiceMarksDao platformServiceMarksDao;

    @Override
    public String getServiceMark(String itemId) {
        List<ProductServiceMarks> productServiceMarks = this.baseMapper.selectList(new QueryWrapper<ProductServiceMarks>()
                .eq("item_id", itemId)
                .isNull("delete_time")
        );

        String servicemarks = "免押金";
        if (CollectionUtils.isNotEmpty(productServiceMarks)) {
            for (ProductServiceMarks productServiceMark : productServiceMarks) {
                List<PlatformServiceMarks> platformServiceMarks = platformServiceMarksDao.list(new QueryWrapper<PlatformServiceMarks>()
                        .eq("id", productServiceMark.getInfoId())
                        .isNull("delete_time")
                );
                if (CollectionUtils.isNotEmpty(platformServiceMarks)) {
                    for (PlatformServiceMarks platformServiceMark : platformServiceMarks) {
                        //**服务标类型 0为包邮 1为免押金 2为免赔 3为随租随还 4为全新品 5为分期支付**/
                        switch (platformServiceMark.getType().toString()) {
                            case "0":
                                servicemarks += ",包邮";
                                break;
                            case "1":
                                break;
                            case "2":
                                servicemarks += ",免赔";
                                break;
                            case "3":
                                servicemarks += ",随租随还";
                                break;
                            case "4":
                                servicemarks += ",全新品";
                                break;
                            case "5":
                                servicemarks += ",分期支付";
                                break;
                            default:
                                break;
                        }


                    }
                }

            }
        }
        return servicemarks;
    }

}
