    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.ShopFundFlowReqDto;
import com.rent.common.enums.product.EnumShopFundStatus;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ShopFundFlowDao;
import com.rent.mapper.product.ShopFundFlowMapper;
import com.rent.model.product.ShopFundFlow;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author zhaowenchao
 */
@Repository
public class ShopFundFlowDaoImpl extends AbstractBaseDaoImpl<ShopFundFlow, ShopFundFlowMapper> implements ShopFundFlowDao {


    @Override
    public ShopFundFlow getLastSuccessFlow(String shopId) {
        return getOne(new QueryWrapper<ShopFundFlow>()
                .eq("shop_id",shopId)
                .eq("status", EnumShopFundStatus.SUCCESS)
                .orderByDesc("update_time")
                .last("limit 1"));
    }

    @Override
    public ShopFundFlow getSuccessByIdAndShopId(Long id, String shopId) {
        ShopFundFlow shopFundFlow = getById(id);
        if(shopFundFlow==null){
            return null;
        }
        if(!shopFundFlow.getShopId().equals(shopId) && !EnumBackstageUserPlatform.OPE.getCode().equals(shopId)){
            return null;
        }
        if(!shopFundFlow.getStatus().equals(EnumShopFundStatus.SUCCESS)){
            return null;
        }
        return shopFundFlow;
    }

    @Override
    public ShopFundFlow getByFlowNo(String flowNo) {
        return getOne(new QueryWrapper<ShopFundFlow>().eq("flow_no",flowNo));
    }

    @Override
    public Page<ShopFundFlow> pageShopFundFlow(ShopFundFlowReqDto request) {
        Page<ShopFundFlow> page = page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<ShopFundFlow>()
                        .eq("status", EnumShopFundStatus.SUCCESS)
                        .eq("shop_id", request.getShopId())
                        .eq(request.getOperate()!=null, "operate", request.getOperate())
                        .orderByDesc("update_time")
        );
        return page;
    }

    @Override
    public List<ShopFundFlow> listChannelFundFlow(String uid) {
        return list(new QueryWrapper<ShopFundFlow>()
                .eq("shop_id",uid)
                .eq("status",EnumShopFundStatus.SUCCESS)
                .orderByDesc("update_time"));
    }
}
