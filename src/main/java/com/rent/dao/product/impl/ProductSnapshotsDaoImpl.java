    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.product.ProductSnapshotsConverter;
import com.rent.common.dto.product.ProductSnapshotsDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductSnapshotsDao;
import com.rent.mapper.product.ProductSnapshotsMapper;
import com.rent.model.product.ProductSnapshots;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductSnapshotsDao
 *
 * @author youruo
 * @Date 2020-06-16 15:30
 */
@Repository
public class ProductSnapshotsDaoImpl extends AbstractBaseDaoImpl<ProductSnapshots, ProductSnapshotsMapper> implements
    ProductSnapshotsDao {


    @Override
    public List<ProductSnapshotsDto> queryProductSnapshotsList(List<Long> ids) {
        List<ProductSnapshots> snaps = list(new QueryWrapper<ProductSnapshots>().in("id", ids).orderByDesc("version"));
        return ProductSnapshotsConverter.modelList2DtoList(snaps);
    }
}
