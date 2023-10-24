package com.rent.dao.order;

import com.rent.common.dto.backstage.ExportBuyOutOrderReq;
import com.rent.common.dto.export.BuyOutOrderExportDto;
import com.rent.common.enums.order.EnumOrderBuyOutStatus;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.UserOrderBuyOut;

import java.util.Date;
import java.util.List;

/**
 * UserOrderBuyOutDao
 *
 * @author xiaoyao
 * @Date 2020-06-23 14:50
 */
public interface UserOrderBuyOutDao extends IBaseDao<UserOrderBuyOut> {

    /**
     * 根据订单id查询一条记录
     *
     * @param orderId 订单id
     * @return 买断详情
     */
    UserOrderBuyOut selectOneByOrderId(String orderId);

    /**
     * 根据订单id及状态查询一条记录
     *
     * @param orderId 订单id
     * @return 买断详情
     */
    UserOrderBuyOut selectOneByOrderIdAndStatus(String orderId, EnumOrderBuyOutStatus status);

    /**
     * 根据订单id查询一条记录
     *
     * @param buyOutOrderId 买断订单id
     * @return 买断详情
     */
    UserOrderBuyOut selectOneByBuyOutOrderId(String buyOutOrderId);

    /**
     * 查询待分账买断订单
     * @return
     */
    List<UserOrderBuyOut> queryUnSplitList();

    /**
     * 更新分账时间
     * @param id
     * @return
     */
    Boolean updateStateSplitBill(Long id);

    /**
     * 获取买断订单订单号
     * @param begin
     * @param end
     * @param status
     * @return
     */
    List<String> getOrderIdList(Date begin, Date end, String status);

    /**
     * 租赁订单导出-商家和运营公用一个接口
     * @param req
     * @return
     */
    List<BuyOutOrderExportDto> getBuyOutOrder(ExportBuyOutOrderReq req);
}
