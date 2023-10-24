    
package com.rent.dao.marketing.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.QueryOrderComplaintsPageReqVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsPageRespVo;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.OrderComplaintsDao;
import com.rent.mapper.marketing.OrderComplaintsMapper;
import com.rent.model.marketing.OrderComplaints;
import org.springframework.stereotype.Repository;

/**
 * OrderComplaintsDao
 *
 * @author youruo
 * @Date 2020-09-27 15:37
 */
@Repository
public class OrderComplaintsDaoImpl extends AbstractBaseDaoImpl<OrderComplaints, OrderComplaintsMapper> implements OrderComplaintsDao {


    @Override
    public Page<OrderComplaintsPageRespVo> queryOrderComplaintsPage(QueryOrderComplaintsPageReqVo request) {
        IPage<OrderComplaints> page = new Page<>(request.getPageNumber(), request.getPageSize());
        return baseMapper.queryOrderComplaintsPage(page,request);
    }
}
