package com.rent.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.OrderRemarkDto;
import com.rent.common.dto.order.resquest.OrderRemarkReqDto;
import com.rent.common.enums.order.EnumOrderRemarkSource;

/**
 * 订单备注Service
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
public interface OrderRemarkService {

    /**
     * 新增订单备注
     *
     * @param request 条件
     * @return boolean
     */
    Long addOrderRemark(OrderRemarkDto request);

    /**
     * <p>
     * 根据条件列表
     * </p>
     *
     * @param request 实体对象
     * @return OrderRemark
     */
    Page<OrderRemarkDto> queryOrderRemarkPage(OrderRemarkReqDto request);

    /**
     * 增加订单备注
     * @param orderId 订单编号
     * @param remark 备注
     * @param userName 操作人
     * @param source 来源
     */
    void orderRemark(String orderId, String remark, String userName, EnumOrderRemarkSource source);
}