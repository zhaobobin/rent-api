        
package com.rent.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.product.OpeCustomProduct;
import org.apache.ibatis.annotations.Param;

/**
 * OpeCustomProductDao
 *
 * @author youruo
 * @Date 2020-06-16 10:00
 */
public interface OpeCustomProductMapper extends BaseMapper<OpeCustomProduct>{


    void  reapirSalesVolume(@Param("itemId") String itemId,@Param("salesVolume") Integer salesVolume);

}