
package com.rent.dao.product.impl;

import com.rent.common.constant.CategoryType;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.OpeCategoryDao;
import com.rent.mapper.product.OpeCategoryMapper;
import com.rent.model.product.OpeCategory;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import org.springframework.stereotype.Repository;

/**
 * OpeCategoryDao
 *
 * @author youruo
 * @Date 2020-06-15 11:07
 */
@Repository
public class OpeCategoryDaoImpl extends AbstractBaseDaoImpl<OpeCategory, OpeCategoryMapper> implements OpeCategoryDao {

    private static final String cateConfigKey = "cate:config";

    @Override
    public Integer getOneCategoryId(Integer categoryId) {
        if (null != categoryId) {
            String oneCategoryId = (String) RedisUtil.hget(cateConfigKey, categoryId.toString());
            Integer cateId = null;
            if (StringUtil.isEmpty(oneCategoryId)) {
                cateId = getFromDb(categoryId);
                if (null != cateId) {
                    RedisUtil.hset(cateConfigKey, categoryId.toString(), cateId.toString());
                }
            }else{
                cateId = Integer.valueOf(oneCategoryId);
            }
            return cateId;

        }
        return null;
    }

    private Integer getFromDb(Integer categoryId) {
        OpeCategory opeCategory = this.getById(categoryId);
        if (null != opeCategory) {
            if (CategoryType.TWO_CATEGORY.equals(opeCategory.getType())) {
                return opeCategory.getParentId();
            } else {
                OpeCategory twoOpeCategory = this.getById(opeCategory.getParentId());
                if (null != twoOpeCategory) {
                    return twoOpeCategory.getParentId();
                }
            }
        }
        return null;
    }
}
