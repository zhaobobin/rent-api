package com.rent.dao.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.ExportRentOrderReq;
import com.rent.common.dto.export.OrderExportDto;
import com.rent.common.dto.order.BackstageUserOrderDto;
import com.rent.common.dto.order.OrderByConditionRequest;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.UserOrders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * UserOrdersDao
 *
 * @author xiaoyao
 * @Date 2020-06-10 17:02
 */
public interface UserOrdersDao extends IBaseDao<UserOrders> {
    /**
     * 根据订单id查询是否存在记录
     *
     * @param orderId 订单id
     * @return 是否存在记录
     */
    boolean existsWithOrderId(String orderId);

    /**
     * 根据uid查询是否存在记录
     *
     * @param uid uid
     * @return 是否存在记录
     */
    boolean existsWithUid(String uid);

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderId 订单编号
     * @return 订单信息
     */
    UserOrders selectOneByOrderId(String orderId);

    /**
     * 根据orderId更新订单信息
     *
     * @param userOrders 订单信息对象
     * @return 结果
     */
    boolean updateByOrderId(UserOrders userOrders);

    /**
     * 分页查询订单信息
     *
     * @param page
     * @param uid
     * @param statusList
     * @param violationStatusList
     * @return
     */
    Page<UserOrders> pageUserOrdersLite(Page page, String uid, List<String> statusList, List<String> violationStatusList);

    /**
     * 根据uid查询订单列表
     *
     * @param uid
     * @return
     */
    List<UserOrders> queryUserOrdersByUid(String uid, String channelId);

    /**
     * 根据用户姓名查询uid列表
     *
     * @param userName 用户姓名
     * @return
     */
    List<String> queryUidByUserName(String userName);

    /**
     * 根据条件查询订单数量
     *
     * @param dayBegin   开始时间
     * @param dayEnd     结束时间
     * @param statusList 状态列表
     * @param shopId
     * @return 数量
     */
    List<UserOrders> selectCountByDate(Date dayBegin, Date dayEnd, List<EnumOrderStatus> statusList, String shopId, Boolean isMyAuditOrder);

    List<UserOrders> selectCountByDate(Date dayBegin, Date dayEnd, List<EnumOrderStatus> statusList, String shopId);


    /**
     * 根据条件查询订单数量
     *
     * @param dayBegin   开始时间
     * @param dayEnd     结束时间
     * @param statusList 状态列表
     * @return 数量
     */
    int countByDate(Date dayBegin, Date dayEnd, List<EnumOrderStatus> statusList);

    /**
     * 检验订单状态
     *
     * @param orderId
     * @return
     */
    Boolean checkOrderStatus(String orderId);

    /**
     * 获取销量
     *
     * @param productId
     * @return
     */
    Integer getProductSales(String productId);

    /**
     * 获取当前订单Id和以往续租订单Id
     *
     * @param orderId
     * @return
     */
    List<String> selectAllOrderId(String orderId);


    UserOrders getFinishOrder(String orderId);


    BigDecimal getOrderTotolRent(String orderId);


    Integer getSnapShotIdOrder(String orderId);

    /**
     * @param request
     * @return
     */
    List<OrderExportDto> getRentOrderExport(ExportRentOrderReq request);

    /**
     * @param request
     * @return
     */
    List<OrderExportDto> getOverdueOrderExport(ExportRentOrderReq request);

    /**
     * @param request
     * @return
     */
    List<OrderExportDto> getNotGiveBackOrderExport(ExportRentOrderReq request);

    /**
     * 查询用户在租订单数量
     *
     * @param uid
     * @return
     */
    Integer countUserRentingOrder(String uid);

    /**
     * @param request
     * @return
     */
    Page<BackstageUserOrderDto> queryOpeOrderByCondition(OrderByConditionRequest request);

}
