package com.rent.service.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.SplitBillDetailRentDto;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.resquest.ChannelAccountPeriodReqDto;

/**
 * @author zhaowenchao
 */
public interface ChannelAccountPeriodService {


    /**
     * 查询
     * @param reqDto
     * @return
     */
    Page<ChannelAccountPeriodDto> queryChannelAccountPeriodPage(ChannelAccountPeriodReqDto reqDto);


    /**
     * 提交结算
     * @param req
     */
    void submitSettle(AccountPeriodSubmitSettleDto req);

    /**
     * 提交审核
     * @param req
     */
    void submitAudit(AccountPeriodSubmitAuditDto req);

    /**
     * 获取详细信息
     * @param id
     * @return
     */
    ChannelAccountPeriodDetailDto detail(Long id);

    /**
     * 生成渠道账期
     */
    void generateAccountPeriod();

    /**
     * 根据条件筛选 租赁订单页面
     * @param request
     * @return
     */
    Page<ChannelSplitBillRentDto> listRent(AccountPeriodItemReqDto request);

    /**
     * 分账 租金 详情页面
     * @param orderId
     * @return
     */
    SplitBillDetailRentDto rentDetail(String orderId);

    /**
     * 查看明细-常规订单
     * @param request
     * @return
     */
    Page<ChannelSplitBillRentDto> rent(AccountPeriodItemReqDto request);
}
