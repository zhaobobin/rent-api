        
package com.rent.dao.product;

import com.rent.common.dto.product.ProductSnapshotsDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductSnapshots;

import java.util.List;

/**
 * ProductSnapshotsDao
 *
 * @author youruo
 * @Date 2020-06-16 15:30
 */
public interface ProductSnapshotsDao extends IBaseDao<ProductSnapshots> {


    /**
     *
     * @param id
     * @return
     */
    List<ProductSnapshotsDto> queryProductSnapshotsList(List<Long> id);



}
