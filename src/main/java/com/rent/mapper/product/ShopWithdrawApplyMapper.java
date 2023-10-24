        
package com.rent.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.request.WithdrawApplyPageReq;
import com.rent.common.dto.product.resp.WithdrawApplyPageResp;
import com.rent.model.product.ShopWithdrawApply;


/**
 * @author zhaowenchao
 */
public interface ShopWithdrawApplyMapper extends BaseMapper<ShopWithdrawApply>{

    /**
     * 商家提现审批 分页列表页面
     * @param page
     * @param request
     * @return
     */
    Page<WithdrawApplyPageResp> withDrawApplyPage(IPage<ShopWithdrawApply> page, WithdrawApplyPageReq request);
}