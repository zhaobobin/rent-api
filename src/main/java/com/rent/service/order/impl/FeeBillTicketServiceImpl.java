        
package com.rent.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.response.EnableApplyFeeBillTicketResp;
import com.rent.common.dto.order.response.FeeBillTicketPageResp;
import com.rent.common.dto.order.resquest.FeeBillTicketPageReq;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import com.rent.dao.order.FeeBillDao;
import com.rent.dao.order.FeeBillTicketDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.FeeBill;
import com.rent.model.order.FeeBillTicket;
import com.rent.service.order.FeeBillTicketService;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FeeBillTicketServiceImpl implements FeeBillTicketService {

    private final FeeBillDao feeBillDao;
    private final FeeBillTicketDao feeBillTicketDao;

    @Override
    public EnableApplyFeeBillTicketResp getEnableApplyFeeBill(String shopId) {
        List<FeeBill> feeBillList = feeBillDao.list(new QueryWrapper<FeeBill>()
                .eq("shop_id",shopId)
                .eq("ticket_status", EnumOderFeeBillInvoiceStatus.UN_TICKET));

        BigDecimal amount = new BigDecimal(0);
        List<Long> feeBillIdList = new ArrayList<>(feeBillList.size());
        for (FeeBill feeBill : feeBillList) {
            amount = amount.add(feeBill.getAmount());
            feeBillIdList.add(feeBill.getId());
        }

        EnableApplyFeeBillTicketResp resp = new EnableApplyFeeBillTicketResp();
        resp.setFeeBillIdList(feeBillIdList);
        resp.setAmount(amount);
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void apply(List<Long> idList,BigDecimal amount,String shopId) {
        FeeBillTicket feeBillTicket = new FeeBillTicket();
        feeBillTicket.setShopId(shopId);
        feeBillTicket.setAmount(amount);
        feeBillTicket.setTicketStatus(EnumOderFeeBillInvoiceStatus.APPLYING);
        feeBillTicket.setApplyUid(LoginUserUtil.getLoginUser().getId());
        feeBillTicket.setCreateTime(new Date());
        feeBillTicketDao.save(feeBillTicket);

        List<FeeBill> feeBillList = feeBillDao.listByIds(idList);
        BigDecimal checkAmount = new BigDecimal(0);
        for (FeeBill feeBill : feeBillList) {
            if(!feeBill.getShopId().equals(shopId)){
                throw new HzsxBizException("-1","数据错误，请重试");
            }
            if(!feeBill.getTicketStatus().equals(EnumOderFeeBillInvoiceStatus.UN_TICKET)){
                throw new HzsxBizException("-1","数据错误，请重试");
            }
            checkAmount = checkAmount.add(feeBill.getAmount());
            feeBill.setTicketId(feeBillTicket.getId());
            feeBill.setTicketStatus(EnumOderFeeBillInvoiceStatus.APPLYING);
        }
        if(checkAmount.compareTo(amount)!=0){
            throw new HzsxBizException("-1","数据错误，请重试");
        }
        feeBillDao.updateBatchById(feeBillList);
    }

    @Override
    public Page<FeeBillTicketPageResp> page(FeeBillTicketPageReq req) {
        return feeBillTicketDao.pageTicket(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirm(Long id) {
        FeeBillTicket ticket = feeBillTicketDao.getById(id);
        ticket.setTicketStatus(EnumOderFeeBillInvoiceStatus.TICKETED);
        ticket.setAuditUid(LoginUserUtil.getLoginUser().getId());
        ticket.setUpdateTime(new Date());
        feeBillTicketDao.updateById(ticket);
        feeBillDao.updateTicketStatus(id,EnumOderFeeBillInvoiceStatus.TICKETED);
    }
}