package com.rent.dao.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.rent.common.dto.backstage.ExportRentOrderReq;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.export.OrderExportDto;
import com.rent.common.dto.order.BackstageUserOrderDto;
import com.rent.common.dto.order.OrderByConditionRequest;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.EnumSerialModalName;
import com.rent.common.util.StringUtil;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderAuditUserDao;
import com.rent.dao.order.UserOrderCashesDao;
import com.rent.dao.order.UserOrderItemsDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.mapper.order.UserOrdersMapper;
import com.rent.model.order.OrderAuditUser;
import com.rent.model.order.UserOrderItems;
import com.rent.model.order.UserOrders;
import com.rent.util.AppParamUtil;
import com.rent.util.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserOrdersDao
 *
 * @author xiaoyao
 * @Date 2020-06-03 16:49
 */
@Repository
public class UserOrdersDaoImpl extends AbstractBaseDaoImpl<UserOrders, UserOrdersMapper> implements UserOrdersDao {

    @Autowired
    private UserOrderCashesDao userOrderCashesDao;
    @Autowired
    private UserOrderItemsDao userOrderItemsDao;
    @Autowired
    private OrderAuditUserDao orderAuditUserDao;

    @Override
    public boolean existsWithOrderId(String orderId) {
        UserOrders example = new UserOrders();
        example.setOrderId(orderId);
        return this.count(new QueryWrapper<>(example)) > 0;
    }

    @Override
    public boolean existsWithUid(String uid) {
        UserOrders example = new UserOrders();
        example.setUid(uid);
        return this.count(new QueryWrapper<>(example)) > 0;
    }

    @Override
    public UserOrders selectOneByOrderId(String orderId) {
        UserOrders example = new UserOrders();
        example.setOrderId(orderId);
        return this.getOne(new QueryWrapper<>(example));
    }

    @Override
    public boolean updateByOrderId(UserOrders userOrders) {
        if (null == userOrders || StringUtil.isEmpty(userOrders.getOrderId())) {
            throw new HzsxBizException("998333", "更新失败，记录不存在",
                    this.getClass());
        }
        return this.update(userOrders, new QueryWrapper<>(UserOrders.builder()
                .orderId(userOrders.getOrderId())
                .build()));
    }

    @Override
    public Page<UserOrders> pageUserOrdersLite(Page page, String uid, List<String> statusList,
                                               List<String> violationStatusList) {

        return page(page, new QueryWrapper<UserOrders>().select("create_time", "order_id", "status", "shop_id", "type",
                        "examine_status", "unrent_time", "rent_start", "unrent_express_no", "unrent_express_id", "face_auth_status",
                        "product_id", "is_violation")
                .eq("uid", uid)
                .eq("channel_id", AppParamUtil.getChannelId())
                .in(CollectionUtil.isNotEmpty(statusList), "status", statusList)
                .in(CollectionUtil.isNotEmpty(violationStatusList), "is_violation", violationStatusList)
                .orderByDesc("create_time"));
    }

    @Override
    public List<UserOrders> queryUserOrdersByUid(String uid, String channelId) {
        UserOrders example = UserOrders.builder()
                .uid(uid)
                .build();
        if (StringUtil.isNotEmpty(channelId)) {
            example.setChannelId(channelId);
        }
        return this.list(
                new QueryWrapper<>(example).select("id,order_id", "uid", "status", "channel_id", "type", "payer_user_id",
                        "product_id"));
    }

    @Override
    public List<String> queryUidByUserName(String userName) {
        List<UserOrders> userOrdersList = this.list(new QueryWrapper<>(UserOrders.builder()
                .userName(userName)
                .build()).select("uid"));
        if (CollectionUtil.isEmpty(userOrdersList)) {
            return Collections.emptyList();
        }
        return userOrdersList.stream()
                .map(UserOrders::getUid)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOrders> selectCountByDate(Date dayBegin, Date dayEnd, List<EnumOrderStatus> statusList,
                                              String shopId) {
        return this.selectCountByDate(dayBegin, dayEnd, statusList, shopId, false);
    }

    @Override
    public List<UserOrders> selectCountByDate(Date dayBegin, Date dayEnd, List<EnumOrderStatus> statusList,
                                              String shopId, Boolean isMyAuditOrder) {
        List<UserOrders> list;
        if (isMyAuditOrder) {
            LoginUserBo loginUserBo = LoginUserUtil.getLoginUser();
            List<OrderAuditUser> orderAuditUsers = orderAuditUserDao.getOrderByBackstageUserId(loginUserBo.getId());
            List<String> orderIds = orderAuditUsers.stream().map(OrderAuditUser::getOrderId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(orderIds)) {
                orderIds.add("");
            }
            list = this.list(new QueryWrapper<>(UserOrders.builder()
                    .build()).between(null != dayBegin, "create_time", dayBegin, dayEnd)
                    .in(CollectionUtil.isNotEmpty(statusList), "`status`", statusList)
                    .in("`order_id`", orderIds)
                    .eq(StringUtil.isNotEmpty(shopId), "shop_id", shopId)
                    .select("order_id", "`status`", "is_violation", "examine_status", "type"));
        } else {
            list = this.list(new QueryWrapper<>(UserOrders.builder()
                    .build()).between(null != dayBegin, "create_time", dayBegin, dayEnd)
                    .in(CollectionUtil.isNotEmpty(statusList), "`status`", statusList)
                    .eq(StringUtil.isNotEmpty(shopId), "shop_id", shopId)
                    .select("order_id", "`status`", "is_violation", "examine_status", "type"));
        }


        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list;
    }

    @Override
    public int countByDate(Date dayBegin, Date dayEnd, List<EnumOrderStatus> statusList) {
        return this.count(new QueryWrapper<>(UserOrders.builder()
                .build()).between("create_time", dayBegin, dayEnd)
                .in(CollectionUtil.isNotEmpty(statusList), "`status`", statusList));
    }

    @Override
    public Boolean checkOrderStatus(String orderId) {
        List<UserOrders> userOrdersList = this.list(new QueryWrapper<UserOrders>().select("uid")
                .eq("order_id", orderId)
                .in("status", EnumOrderStatus.RENTING.getCode()));
        if (CollectionUtils.isEmpty(userOrdersList)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


    @Override
    public Integer getProductSales(String productId) {
        return count(new QueryWrapper<UserOrders>().eq("product_id", productId));
    }


    //目前先查询31期以内的分期订单
    @Override
    public List<String> selectAllOrderId(String orderId) {
        //初始化第一层
        List<String> orderIds = Lists.newArrayList(orderId);
        if (orderId.contains(EnumSerialModalName.RELET_ORDER_ID.getCode())) {
            //获取第一层
            Boolean isRelet = Boolean.FALSE;
            String orderIdDo = orderId;
            int index = 0;
            do {
                UserOrders originalOrder = this.getFinishOrder(orderIdDo);
                isRelet = null != originalOrder && StringUtil.isNotEmpty(originalOrder.getOriginalOrderId());
                if (isRelet) {
                    orderIdDo = originalOrder.getOriginalOrderId();
                    orderIds.add(orderIdDo);
                }
                if (index > 30) {
                    isRelet = Boolean.FALSE;
                }
                index++;
            } while (isRelet);
        }
        return orderIds;
    }

    @Override
    public UserOrders getFinishOrder(String orderId) {
        return this.getOne(new QueryWrapper<>(new UserOrders()).select("order_id,original_order_id,uid")
                .eq("order_id", orderId)
                .notIn("status", EnumOrderStatus.PAYED_USER_APPLY_CLOSE, EnumOrderStatus.CLOSED)
                .last("limit 1")
                .orderByDesc("id"));
    }

    @Override
    public BigDecimal getOrderTotolRent(String orderId) {
        List<String> orderIds = this.selectAllOrderId(orderId);
        return userOrderCashesDao.getAllOrdersTotolRent(orderIds);
    }

    @Override
    public Integer getSnapShotIdOrder(String orderId) {
        UserOrderItems item = userOrderItemsDao.selectOneByOrderId(orderId);
        return null != item ? item.getSnapShotId()
                .intValue() : null;
    }

    @Override
    public List<OrderExportDto> getRentOrderExport(ExportRentOrderReq request) {
        return baseMapper.getRentOrderExport(request);
    }

    @Override
    public List<OrderExportDto> getOverdueOrderExport(ExportRentOrderReq request) {
        return baseMapper.getOverdueOrderExport(request);
    }

    @Override
    public List<OrderExportDto> getNotGiveBackOrderExport(ExportRentOrderReq request) {
        return baseMapper.getNotGiveBackOrderExport(request);
    }

    @Override
    public Integer countUserRentingOrder(String uid) {
        return count(new QueryWrapper<UserOrders>().eq("uid", uid).in("status", EnumOrderStatus.RENTING_SET));
    }

    @Override
    public Page<BackstageUserOrderDto> queryOpeOrderByCondition(OrderByConditionRequest request) {
        IPage<UserOrders> page = new Page<>(request.getPageNumber(), request.getPageSize());
        return baseMapper.queryOpeOrderByCondition(page, request);
    }


}
