
package com.rent.service.marketing.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.rent.common.converter.marketing.OrderComplaintsConverter;
import com.rent.common.dto.api.request.SubmitOrderComplaintReqVo;
import com.rent.common.dto.api.resp.OrderComplaintsResponse;
import com.rent.common.dto.backstage.request.ModifyOrderComplaintsReqVo;
import com.rent.common.dto.backstage.request.QueryOrderComplaintsPageReqVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsDetailRespVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsPageRespVo;
import com.rent.common.dto.common.resp.GetOrderComplaintTypeRespVo;
import com.rent.common.enums.marketing.OrderComplaintStatusEnum;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.dao.marketing.OrderComplaintsDao;
import com.rent.dao.marketing.OrderComplaintsImageDao;
import com.rent.dao.marketing.OrderComplaintsTypeDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.dao.product.ShopDao;
import com.rent.model.marketing.OrderComplaints;
import com.rent.model.marketing.OrderComplaintsType;
import com.rent.model.order.UserOrders;
import com.rent.model.product.Shop;
import com.rent.service.marketing.OrderComplaintsService;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderComplaintsServiceImpl implements OrderComplaintsService {

    private final OrderComplaintsDao orderComplaintsDao;
    private final OrderComplaintsImageDao orderComplaintsImageDao;
    private final OrderComplaintsTypeDao orderComplaintsTypeDao;
    private final ShopDao shopDao;
    private final UserOrdersDao userOrdersDao;

    @Override
    public Boolean addOrderComplaints(SubmitOrderComplaintReqVo request) {
        OrderComplaints model = OrderComplaintsConverter.submitVo2Model(request);
        if (orderComplaintsDao.save(model)) {
            if (CollectionUtils.isNotEmpty(request.getImages())) {
                orderComplaintsImageDao.batchSaveOrderComplaintsImage(request.getImages(), model.getId());
            }
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean modifyOrderComplaints(ModifyOrderComplaintsReqVo request) {
        OrderComplaints orderComplaints = new OrderComplaints();
        orderComplaints.setId(request.getId());
        orderComplaints.setOperator(LoginUserUtil.getLoginUser().getName());
        orderComplaints.setStatus(OrderComplaintStatusEnum.FINISH);
        orderComplaints.setUpdateTime(new Date());
        orderComplaints.setResult(request.getResult());
        return orderComplaintsDao.updateById(orderComplaints);
    }

    @Override
    public OrderComplaintsDetailRespVo queryOrderComplaintsDetail(Long id) {
        OrderComplaints orderComplaints = orderComplaintsDao.getById(id);
        OrderComplaintsDetailRespVo resp = OrderComplaintsConverter.model2DetailRespVo(orderComplaints);
        if (null != resp) {
            resp.setImages(orderComplaintsImageDao.getOrdOrderImages(orderComplaints.getId()));
            Shop shop = shopDao.getByShopId(orderComplaints.getShopId());
            resp.setShopName(shop.getName());
            OrderComplaintsType model = orderComplaintsTypeDao.getById(orderComplaints.getTypeId());
            if (null != model) {
                resp.setTypeName(model.getName());
            }
        }
        return resp;
    }

    @Override
    public Page<OrderComplaintsPageRespVo> queryOrderComplaintsPage(QueryOrderComplaintsPageReqVo request) {
        return orderComplaintsDao.queryOrderComplaintsPage(request);
    }

    @Override
    public List<GetOrderComplaintTypeRespVo> getOrderComplaintsTypes() {
        List<OrderComplaintsType> list = orderComplaintsTypeDao.list(new QueryWrapper<>());
        if (CollectionUtil.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        List<GetOrderComplaintTypeRespVo> voList = new ArrayList<>(list.size());
        for (OrderComplaintsType orderComplaintsType : list) {
            GetOrderComplaintTypeRespVo vo = new GetOrderComplaintTypeRespVo();
            vo.setId(orderComplaintsType.getId());
            vo.setName(orderComplaintsType.getName());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<OrderComplaintsResponse> getOrderAndShopName(String uid, String channelId) {
        List<OrderComplaintsResponse> result = new ArrayList<>();
        List<String> statusList = Lists.newArrayList(EnumOrderStatus.WAITING_PAYMENT.getCode(),EnumOrderStatus.CLOSED.getCode());
        List<UserOrders> userOrders = this.userOrdersDao.list(new QueryWrapper<UserOrders>()
                .select("order_id,shop_id","create_time")
                .eq("uid", uid)
                .eq("channel_id", channelId)
                .notIn("status", statusList)
                .isNull("delete_time")
                .orderByDesc("id")
        );
        if (CollectionUtils.isNotEmpty(userOrders)) {
            List<String> shopIdList = userOrders.stream().map(UserOrders::getShopId).collect(Collectors.toList());
            List<Shop> shops = shopDao.list(new QueryWrapper<Shop>().select("shop_id,name").in("shop_id", shopIdList));
            Map<String, String> shopIdNameMap = shops.stream().collect(Collectors.toMap(Shop::getShopId, Shop::getName));
            userOrders.forEach(item -> {
                String shopName = shopIdNameMap.containsKey(item.getShopId()) ? shopIdNameMap.get(item.getShopId()): "店铺";
                String orderId = item.getOrderId();
                String newstring = orderId.substring(orderId.length() - 4);
                String contactString = shopName.concat("-").concat(newstring);
                result.add(OrderComplaintsResponse.builder()
                        .orderId(item.getOrderId())
                        .shopId(item.getShopId())
                        .shopName(shopName)
                        .contactString(contactString)
                        .build());
            });
        }
        return result;
    }
}