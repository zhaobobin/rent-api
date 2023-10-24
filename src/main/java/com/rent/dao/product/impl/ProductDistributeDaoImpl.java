package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductDistributeDao;
import com.rent.mapper.product.ProductDistributeMapper;
import com.rent.model.product.ProductDistribute;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaowenchao
 */
@Repository
public class ProductDistributeDaoImpl extends AbstractBaseDaoImpl<ProductDistribute, ProductDistributeMapper>
    implements ProductDistributeDao {

    @Override
    public List<ProductDistribute> getByVirtualProductId(String virtualProductId) {
        return list(new QueryWrapper<ProductDistribute>().eq("virtual_product_id", virtualProductId)
            .isNull("delete_time"));
    }

    @Override
    public void updateRemark(String virtualProductId, String remark) {
        ProductDistribute productDistribute = new ProductDistribute();
        productDistribute.setRemark(remark);
        update(productDistribute, new QueryWrapper<ProductDistribute>().eq("virtual_product_id", virtualProductId)
            .isNull("delete_time"));
    }

    @Override
    public void delProductDistribute(String virtualProductId) {
        remove(new QueryWrapper<ProductDistribute>().eq("virtual_product_id", virtualProductId));
    }
}
