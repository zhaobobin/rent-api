package com.rent.dao.product.impl;

import com.rent.common.dto.product.ProductSpecValueDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductSpecValueDao;
import com.rent.mapper.product.ProductSpecValueMapper;
import com.rent.model.product.ProductSpecValue;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductSpecValueDao
 *
 * @author youruo
 * @Date 2020-06-16 15:33
 */
@Repository
public class ProductSpecValueDaoImpl extends AbstractBaseDaoImpl<ProductSpecValue, ProductSpecValueMapper>
    implements ProductSpecValueDao {

    @Override
    public List<ProductSpecValueDto> selectBySpecId(Integer specId) {
        return this.baseMapper.selectBySpecId(specId);
    }

    @Override
    public List<ProductSpecValueDto> selectBySpecIds(List<Integer> specIds) {
        if (CollectionUtils.isEmpty(specIds)) {
            return new ArrayList<>();
        }
        return this.baseMapper.selectBySpecIds(specIds);
    }
}
