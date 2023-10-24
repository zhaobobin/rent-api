        
package com.rent.dao.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.request.WithdrawApplyPageReq;
import com.rent.common.dto.product.resp.WithdrawApplyPageResp;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ShopWithdrawApply;


/**
 * @author zhaowenchao
 */
public interface ShopWithdrawApplyDao extends IBaseDao<ShopWithdrawApply> {

    /**
     * 商家提现审批 分页列表页面
     * @param request
     * @return
     */
    Page<WithdrawApplyPageResp> withDrawApplyPage(WithdrawApplyPageReq request);
}
