        
package com.rent.dao.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.ShopFundFlowReqDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ShopFundFlow;

import java.util.List;


/**
 * @author zhaowenchao
 */
public interface ShopFundFlowDao extends IBaseDao<ShopFundFlow> {

    /**
     * 根据店铺ID查看最近的一笔流水
     * @param shopId
     * @return
     */
    ShopFundFlow getLastSuccessFlow(String shopId);

    /**
     * 根据店铺ID和id查询成功的记录
     * @param id
     * @param shopId
     * @return
     */
    ShopFundFlow getSuccessByIdAndShopId(Long id,String shopId);

    /**
     * 根据流水号查询
     * @param flowNo
     */
    ShopFundFlow getByFlowNo(String flowNo);

    /**
     * 分页查询成功的记录
     * @param request
     * @return
     */
    Page<ShopFundFlow> pageShopFundFlow(ShopFundFlowReqDto request);


    /**
     * 查询渠道提现的记录
     * @param uid
     * @return
     */
    List<ShopFundFlow> listChannelFundFlow(String uid);
}
