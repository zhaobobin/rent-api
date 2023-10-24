package com.rent.service.components.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMerchantOrderSyncRequest;
import com.alipay.api.response.AlipayMerchantOrderSyncResponse;
import com.rent.common.enums.components.OrderCenterStatus;
import com.rent.common.util.AliPayClientFactory;
import com.rent.config.outside.OutsideConfig;
import com.rent.dao.components.OrderCenterSyncLogDao;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.UserOrderBuyOutDao;
import com.rent.dao.order.UserOrderCashesDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.dao.product.ProductDao;
import com.rent.model.components.OrderCenterProduct;
import com.rent.model.components.OrderCenterSyncLog;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrderBuyOut;
import com.rent.model.order.UserOrderCashes;
import com.rent.model.order.UserOrders;
import com.rent.service.components.OrderCenterProductService;
import com.rent.service.components.OrderCenterService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author udo
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCenterServiceImpl implements OrderCenterService {

    private final OrderCenterProductService orderCenterProductService;
    private final OrderCenterSyncLogDao orderCenterSyncLogDao;
    private final UserOrdersDao userOrdersDao;
    private final OrderByStagesDao orderByStagesDao;
    private final UserOrderCashesDao userOrderCashesDao;
    private final ProductDao productDao;
    private final UserOrderBuyOutDao userOrderBuyOutDao;

    private JSONObject getExtJson(String key, Object value) {
        JSONObject obj = new JSONObject();
        obj.put("ext_key", key);
        obj.put("ext_value", value);
        return obj;
    }

    @Override
    public void merchantOrderSync(String orderId, OrderCenterStatus status) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        UserOrderCashes cashes = userOrderCashesDao.selectOneByOrderId(orderId);
        OrderCenterProduct orderCenterProduct = orderCenterProductService.getByProductId(userOrders.getProductId());
        if (orderCenterProduct == null) {
            log.info("【订单中心-订单同步-未查询到商品信息】-orderId={}", orderId);
            return;
        }
        JSONObject reqParam = new JSONObject();
        reqParam.put("out_biz_no", orderId);
        reqParam.put("buyer_id", userOrders.getPayerUserId());
//        reqParam.put("buyer_open_id", userOrders.getPayerUserId());
        reqParam.put("buyer_open_id", null);
        reqParam.put("order_type", "SERVICE_ORDER");
        reqParam.put("order_create_time", DateUtil.date2String(userOrders.getCreateTime(), DateUtil.DATETIME_FORMAT_1));
        reqParam.put("order_modified_time", DateUtil.date2String(new Date(), DateUtil.DATETIME_FORMAT_1));

        JSONArray extInfo = new JSONArray();
        extInfo.add(getExtJson("merchant_order_status", status.getCode()));
        extInfo.add(getExtJson("merchant_biz_type", "3C_RENT"));
        extInfo.add(getExtJson("merchant_order_link_page", "/pages/orderList/orderList"));
        extInfo.add(getExtJson("tiny_app_id", OutsideConfig.APPID));
        extInfo.add(getExtJson("business_info", getBusinessInfo(orderId, status, userOrders)));
        reqParam.put("ext_info", extInfo);

        JSONArray itemOrderList = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("quantity", 1);
        item.put("unit_price", cashes.getTotalRent());
        item.put("item_name", productDao.getProductName(userOrders.getProductId()));
        JSONArray itemExtInfo = new JSONArray();
        itemExtInfo.add(getExtJson("ext_key", orderCenterProduct.getMaterialId()));
        item.put("ext_info", itemExtInfo);
        itemOrderList.add(item);
        reqParam.put("item_order_list", itemOrderList);

        OrderCenterSyncLog orderCenterSyncLog = new OrderCenterSyncLog()
                .setOrderId(orderId)
                .setType(status.getCode())
                .setProductId(userOrders.getProductId())
                .setReqParams(reqParam.toJSONString())
                .setState("FAIL")
                .setCreateTime(new Date());
        AlipayClient alipayClient = AliPayClientFactory.getAlipayClientByType();
        AlipayMerchantOrderSyncRequest request = new AlipayMerchantOrderSyncRequest();
        request.setBizContent(reqParam.toJSONString());
        try {
            AlipayMerchantOrderSyncResponse response = alipayClient.certificateExecute(request);
            orderCenterSyncLog.setResp(response.getBody());
            if (!response.isSuccess()) {
                log.info("【订单中心-订单同步-同步失败】-responseBody={}", new Object[]{response.getBody()});
            } else {
                orderCenterSyncLog.setState("SUCCESS");
            }
        } catch (Exception e) {
            log.info("【订单中心-订单同步-同步异常】{}", e);
        } finally {
            orderCenterSyncLogDao.save(orderCenterSyncLog);
        }
    }

    private JSONObject getBusinessInfo(String orderId, OrderCenterStatus orderCenterStatus, UserOrders userOrders) {
        JSONObject businessInfo = new JSONObject();
        if (OrderCenterStatus.APPROVAL == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
        } else if (OrderCenterStatus.REJECT == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
            businessInfo.put("cash_pledge", userOrderCashes.getDeposit().toString());
            return businessInfo;
        } else if (OrderCenterStatus.TO_SEND_GOODS == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
            businessInfo.put("thaw_deposit", userOrderCashes.getDeposit().toString());
            return businessInfo;
        } else if (OrderCenterStatus.IN_DELIVERY == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
            businessInfo.put("thaw_deposit", userOrderCashes.getDeposit().toString());
            businessInfo.put("delivery_time", DateUtil.date2String(userOrders.getDeliveryTime(), DateUtil.DATETIME_FORMAT_1));
            businessInfo.put("courier_number", userOrders.getExpressNo());
            businessInfo.put("lease_period", "2021.09.01--2022.09.01");
            return businessInfo;
        } else if (OrderCenterStatus.IN_THE_LEASE == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("receiving_time", DateUtil.date2String(userOrders.getRentStart(), DateUtil.DATETIME_FORMAT_1));
            businessInfo.put("thaw_deposit", userOrderCashes.getDeposit().toString());
            businessInfo.put("delivery_time", DateUtil.date2String(userOrders.getDeliveryTime(), DateUtil.DATETIME_FORMAT_1));
            businessInfo.put("rent_due_date", DateUtil.date2String(userOrders.getUnrentTime(), DateUtil.DATE_FORMAT_1));
            businessInfo.put("courier_number", userOrders.getExpressNo());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease_period", getLeasePeriod(userOrders));
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
            businessInfo.put("give_back_date", DateUtil.date2String(userOrders.getUnrentTime(), DateUtil.DATE_FORMAT_1));
            return businessInfo;
        } else if (OrderCenterStatus.REJECT == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
            return businessInfo;
        } else if (OrderCenterStatus.CLOSED == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
        } else if (OrderCenterStatus.IN_THE_BACK == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("receiving_time", DateUtil.date2String(userOrders.getRentStart(), DateUtil.DATETIME_FORMAT_1));
            businessInfo.put("thaw_deposit", userOrderCashes.getDeposit().toString());
            businessInfo.put("delivery_time", DateUtil.date2String(userOrders.getDeliveryTime(), DateUtil.DATETIME_FORMAT_1));
            businessInfo.put("rent_due_date", DateUtil.date2String(userOrders.getUnrentTime(), DateUtil.DATE_FORMAT_1));
            businessInfo.put("courier_number", userOrders.getExpressNo());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease_period", getLeasePeriod(userOrders));
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
            businessInfo.put("give_back_date", DateUtil.date2String(userOrders.getUnrentTime(), DateUtil.DATE_FORMAT_1));
        } else if (OrderCenterStatus.BUYOUT == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            UserOrderBuyOut userOrderBuyOut = userOrderBuyOutDao.selectOneByOrderId(orderId);
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("receiving_time", DateUtil.date2String(userOrders.getRentStart(), DateUtil.DATETIME_FORMAT_1));
            businessInfo.put("buy_amount", userOrderBuyOut.getEndFund());
            businessInfo.put("thaw_deposit", userOrderCashes.getDeposit().toString());
            businessInfo.put("delivery_time", DateUtil.date2String(userOrders.getDeliveryTime(), DateUtil.DATETIME_FORMAT_1));
            businessInfo.put("rent_due_date", DateUtil.date2String(userOrders.getUnrentTime(), DateUtil.DATE_FORMAT_1));
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
            businessInfo.put("courier_number", userOrders.getExpressNo());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease_period", getLeasePeriod(userOrders));
            businessInfo.put("real_date", DateUtil.date2String(userOrders.getUnrentTime(), DateUtil.DATE_FORMAT_1));
            return businessInfo;
        } else if (OrderCenterStatus.FINISHED == orderCenterStatus) {
            OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
            businessInfo.put("first_rent", earliestPeriod.getCurrentPeriodsRent().toPlainString());
            businessInfo.put("receiving_time", DateUtil.date2String(userOrders.getRentStart(), DateUtil.DATETIME_FORMAT_1));
            businessInfo.put("thaw_deposit", userOrderCashes.getDeposit().toString());
            businessInfo.put("delivery_time", DateUtil.date2String(userOrders.getDeliveryTime(), DateUtil.DATETIME_FORMAT_1));
            businessInfo.put("rent_due_date", DateUtil.date2String(userOrders.getUnrentTime(), DateUtil.DATE_FORMAT_1));
            businessInfo.put("courier_number", userOrders.getExpressNo());
            businessInfo.put("total_rent", earliestPeriod.getTotalRent().toPlainString());
            businessInfo.put("lease_period", getLeasePeriod(userOrders));
            businessInfo.put("real_date", DateUtil.date2String(new Date(), DateUtil.DATE_FORMAT_1));
            businessInfo.put("lease", earliestPeriod.getTotalPeriods().toString());
            businessInfo.put("give_back_date", DateUtil.date2String(userOrders.getUnrentTime(), DateUtil.DATE_FORMAT_1));
        }
        return businessInfo;
    }

    private String getLeasePeriod(UserOrders userOrders) {
        Date start = userOrders.getDeliveryTime();
        Date end = DateUtil.getEndTimeOfDay(DateUtil.addDate(userOrders.getDeliveryTime(), userOrders.getRentDuration() - 1));
        return DateUtil.date2String(start, DateUtil.DATE_FORMAT_DOT) + "--" + DateUtil.date2String(end, DateUtil.DATE_FORMAT_DOT);
    }


}
