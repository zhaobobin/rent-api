package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.request.WithdrawApplyPageReq;
import com.rent.common.dto.product.resp.WithdrawApplyPageResp;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ShopWithdrawApplyDao;
import com.rent.mapper.product.ShopWithdrawApplyMapper;
import com.rent.model.product.ShopWithdrawApply;
import org.springframework.stereotype.Repository;

@Repository
public class ShopWithdrawApplyDaoImpl extends AbstractBaseDaoImpl<ShopWithdrawApply, ShopWithdrawApplyMapper> implements ShopWithdrawApplyDao {

    @Override
    public Page<WithdrawApplyPageResp> withDrawApplyPage(WithdrawApplyPageReq request) {
        IPage<ShopWithdrawApply> page = new Page<>(request.getPageNumber(), request.getPageSize());
        return baseMapper.withDrawApplyPage(page,request);
    }
}
