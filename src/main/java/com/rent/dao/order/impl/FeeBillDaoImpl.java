package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.export.FeeBillDto;
import com.rent.common.dto.order.FeeBillDetailReqDto;
import com.rent.common.dto.order.response.FeeBillPageResp;
import com.rent.common.dto.order.resquest.FeeBillReqDto;
import com.rent.common.enums.order.EnumFeeBillType;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.FeeBillDao;
import com.rent.mapper.order.FeeBillMapper;
import com.rent.model.order.FeeBill;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SplitBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Repository
public class FeeBillDaoImpl extends AbstractBaseDaoImpl<FeeBill, FeeBillMapper> implements FeeBillDao {

    @Override
    public List<FeeBill> getAssessmentFeeByOrderId(List<String> orderIdList) {
        List<FeeBill> feeBills = list(new QueryWrapper<FeeBill>()
                .in("order_id",orderIdList)
                .eq("type", EnumFeeBillType.ASSESSMENT)
        );
        return feeBills;
    }

    @Override
    public FeeBill getByOrderIdAndType(String orderId,EnumFeeBillType type) {
        return getOne(new QueryWrapper<FeeBill>().eq("order_id",orderId).eq("type",type));
    }

    @Override
    public void updateTicketStatus(Long ticketId, EnumOderFeeBillInvoiceStatus ticketStatus) {
        baseMapper.updateTicketStatus(ticketId,ticketStatus);
    }

    @Override
    public Page<FeeBillPageResp> pageByCondition(FeeBillReqDto request) {
        IPage<FeeBill> page = new Page<>(request.getPageNumber(), request.getPageSize());
        return baseMapper.pageByCondition(page,request);
    }

    @Override
    public List<FeeBillDto> feeBillDetail(FeeBillDetailReqDto request) {
        return baseMapper.feeBillDetail(request);
    }

}
