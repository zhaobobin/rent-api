
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.OpeCustomProductDao;
import com.rent.mapper.product.OpeCustomProductMapper;
import com.rent.model.product.OpeCustomProduct;
import org.springframework.stereotype.Repository;

/**
 * OpeCustomProductDao
 *
 * @author youruo
 * @Date 2020-06-16 10:00
 */
@Repository
public class OpeCustomProductDaoImpl extends AbstractBaseDaoImpl<OpeCustomProduct, OpeCustomProductMapper> implements OpeCustomProductDao {

    @Override
    public void cleanSortScore() {
        OpeCustomProduct model = new OpeCustomProduct();
        model.setSortScore(0);
        update(model,new UpdateWrapper<>());
    }

    @Override
    public void setProductSortScore(String productId, Integer score) {
        OpeCustomProduct model = new OpeCustomProduct();
        model.setSortScore(score);
        update(model,new UpdateWrapper<OpeCustomProduct>().eq("item_id",productId));
    }

}
