package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.PlatformExpressDao;
import com.rent.mapper.product.PlatformExpressMapper;
import com.rent.model.product.PlatformExpress;
import org.springframework.stereotype.Repository;

/**
 * PlatformExpressDao
 *
 * @author youruo
 * @Date 2020-06-16 11:46
 */
@Repository
public class PlatformExpressDaoImpl extends AbstractBaseDaoImpl<PlatformExpress, PlatformExpressMapper>
        implements PlatformExpressDao {

    @Override
    public PlatformExpress selectExpressById(Long id) {
        return getOne(new QueryWrapper<PlatformExpress>()
                .select("id", "create_time", "update_time", "delete_time", "name", "logo", "short_name", "telephone", "`index`", "ali_code")
                .eq("id",id)
        );
    }


}
