        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductDistribute;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface ProductDistributeDao extends IBaseDao<ProductDistribute> {

    /**
     * 根据虚拟ID获取配置
     * @param virtualProductId
     * @return
     */
    List<ProductDistribute> getByVirtualProductId(String virtualProductId);


    /**
     * 新增|修改 虚拟商品备注
     * @param virtualProductId
     * @param remark
     */
    void updateRemark(String virtualProductId, String remark);

    /**
     * 删除商品分发配置
     * @param virtualProductId
     */
    void delProductDistribute(String virtualProductId);
}
