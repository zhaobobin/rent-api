package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.response.FeeBillTicketPageResp;
import com.rent.common.dto.order.resquest.FeeBillTicketPageReq;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.FeeBillTicketDao;
import com.rent.mapper.order.FeeBillTicketMapper;
import com.rent.model.order.FeeBillTicket;
import org.springframework.stereotype.Repository;

/**
 * SplitBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Repository
public class FeeBillTicketDaoImpl extends AbstractBaseDaoImpl<FeeBillTicket, FeeBillTicketMapper> implements FeeBillTicketDao {


    @Override
    public Page<FeeBillTicketPageResp> pageTicket(FeeBillTicketPageReq request) {
        IPage<FeeBillTicket> page = new Page<>(request.getPageNumber(), request.getPageSize());
        return baseMapper.pageTicket(page,request);
    }
}
