package com.rent.service.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.SplitBilRentlDto;
import com.rent.common.dto.backstage.SplitBillDetailBuyOutDto;
import com.rent.common.dto.backstage.SplitBillDetailRentDto;
import com.rent.common.dto.order.AccountPeriodItemReqDto;
import com.rent.common.dto.order.response.SplitBillBuyOutDto;

/**
 * @author zhaowenchao
 */
public interface AccountPeriodItemService {

    /**
     * 查询账期内租赁订单详细
     * @param request
     * @return
     */
    Page<SplitBilRentlDto> rent(AccountPeriodItemReqDto request);

    /**
     * 查询账期内买断订单详细
     * @param request
     * @return
     */
    Page<SplitBillBuyOutDto> buyOut(AccountPeriodItemReqDto request);


    /**
     * 分账 租金 详情页面
     * @param id
     * @param shopId
     * @return
     */
    SplitBillDetailRentDto rentDetail(Long id, String shopId);


    /**
     * 分账 买断 详情页面
     * @param id
     * @param shopId
     * @return
     */
    SplitBillDetailBuyOutDto buyOutDetail(Long id, String shopId);

    /**
     * 根据条件筛选 买断订单详细
     * @param request
     * @return
     */
    Page<SplitBilRentlDto> listRent(AccountPeriodItemReqDto request);

    /**
     * 根据条件筛选 买断订单详细
     * @param request
     * @return
     */
    Page<SplitBillBuyOutDto> listBuyOut(AccountPeriodItemReqDto request);
}
