
package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.order.OrderAdditionalServicesConverter;
import com.rent.common.converter.order.TransferOrderRecordConverter;
import com.rent.common.dto.backstage.TransferOrderRecordReqDto;
import com.rent.common.dto.backstage.TransferOrderRequest;
import com.rent.common.dto.order.OrderAdditionalServicesDto;
import com.rent.common.dto.order.TransferOrderRecordDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.product.TransferProductDto;
import com.rent.common.dto.product.TransferProductResponse;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.util.AsyncUtil;
import com.rent.dao.order.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.*;
import com.rent.service.order.OrderOperateCore;
import com.rent.service.order.TransferOrderRecordService;
import com.rent.service.product.ProductExtraService;
import com.rent.service.product.ShopService;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 转单记录表Service
 *
 * @author youruo
 * @Date 2021-12-22 17:55
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TransferOrderRecordServiceImpl implements TransferOrderRecordService {

    private final TransferOrderRecordDao transferOrderRecordDao;
    private final UserOrdersDao userOrdersDao;
    private final UserOrderItemsDao userOrderItemsDao;
    private final OrderAdditionalServicesDao orderAdditionalServicesDao;
    private final OrderOperateCore orderOperateCore;
    private final OrderByStagesDao orderByStagesDao;
    private final ShopService shopService;
    private final ProductExtraService productExtraService;

    @Override
    public Page<TransferOrderRecordDto> queryTransferOrderRecordPage(TransferOrderRecordReqDto request) {

        List<String> transferredShopNameShopIds = null;
        //店铺名字-被转
        String transferredShopName = request.getTransferredShopName();
        if (StringUtils.isNotEmpty(transferredShopName) && CollectionUtil.isEmpty(
                transferredShopNameShopIds = shopService.getShopIdListLikeName(transferredShopName))) {
            return new Page<>(request.getPageNumber(), request.getPageSize());
        }
        List<String> transferShopNameShopIds = null;
        //店铺名字-接手
        String transferShopName = request.getTransferShopName();
        if (StringUtils.isNotEmpty(transferShopName) && CollectionUtil.isEmpty(
                transferShopNameShopIds = shopService.getShopIdListLikeName(transferShopName))) {
            return new Page<>(request.getPageNumber(), request.getPageSize());
        }

        Page<TransferOrderRecord> page = transferOrderRecordDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<TransferOrderRecord>()
                        .eq(StringUtil.isNotEmpty(request.getOrderId()), "order_id", request.getOrderId())
                        .in(CollectionUtil.isNotEmpty(transferredShopNameShopIds), "transferred_shop_id", transferredShopNameShopIds)
                        .in(CollectionUtil.isNotEmpty(transferShopNameShopIds), "transfer_shop_id", transferShopNameShopIds)
                        .orderByDesc("id")
        );
        Set<String> shopIdSet = CollectionUtil.newHashSet();
        page.getRecords().forEach(record -> {
            shopIdSet.add(record.getTransferShopId());
            shopIdSet.add(record.getTransferredShopId());
        });
        Map<String, ShopDto> shopDtoMap = this.shopService.selectShopInfoByShopId(new ArrayList<>(shopIdSet));
        List<TransferOrderRecordDto> records = TransferOrderRecordConverter.modelList2DtoList(page.getRecords());
        for (TransferOrderRecordDto record : records) {
            record.setTransferredShopName(shopDtoMap.get(record.getTransferredShopId()).getName());
            record.setTransferShopName(shopDtoMap.get(record.getTransferShopId()).getName());
        }
        return new Page<TransferOrderRecordDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                records);
    }

    @Override
    public Boolean transferOrder(TransferOrderRequest request) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(request.getOrderId());
        if (!EnumOrderStatus.TRANSFER_SET.contains(userOrders.getStatus())) {
            throw new HzsxBizException("-1", "当前订单状态不允许流转！");
        }
        if (request.getTransferShopId().equals(userOrders.getShopId())) {
            throw new HzsxBizException("-1", "不允许自己转自己哈！");
        }
        Boolean getIsHavePay = orderByStagesDao.getIsHavePay(request.getOrderId());
        if (getIsHavePay) {
            throw new HzsxBizException("-1", "单子已经被支付过了，暂不支持转单哈！");
        }
        this.checkTransferOrder(request);
        String jedisOrderIdKey = "transferOrder:" + request.getOrderId();
        if (!RedisUtil.setNx(jedisOrderIdKey, 30)) {
            throw new HzsxBizException("-1", "订单流转正在进行中！");
        }
        String orderId = request.getOrderId();
        String transferShopId = request.getTransferShopId();
        String transferredShopId = userOrders.getShopId();
        //获取订单的商品信息并同步给被流转的商家
        UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(orderId);
        String productId = userOrderItems.getProductId();
        List<OrderAdditionalServices> additionalServices = orderAdditionalServicesDao.queryRecordByOrderId(orderId);
        List<OrderAdditionalServicesDto> orderAdditionalServicesDtos = Lists.newArrayList();
        Boolean isHasAdditonal = CollectionUtil.isNotEmpty(additionalServices);
        if (isHasAdditonal) {
            orderAdditionalServicesDtos = OrderAdditionalServicesConverter.modelList2DtoList(additionalServices);
        }
        //录入新的商品信息到数据库
        TransferProductDto transferProductDto = new TransferProductDto();
        transferProductDto.setTransferShopId(request.getTransferShopId());
        transferProductDto.setTransferredShopId(userOrders.getShopId());
        transferProductDto.setTransferredProductId(userOrderItems.getProductId());
        transferProductDto.setTransferredSkuId(userOrderItems.getSkuId());
        transferProductDto.setTransferredSnapShotId(userOrderItems.getSnapShotId());
        transferProductDto.setOrderAdditionalServicesDtos(orderAdditionalServicesDtos);
        TransferProductResponse transferProductResponse = productExtraService.saveTransferProduct(transferProductDto);

        String newestProductId = transferProductResponse.getNewestProductId();
        Long newestSkuId = transferProductResponse.getNewestSkuId();
        Long newestSnapShotId = transferProductResponse.getNewestSnapShotId();
        Map<Integer, Integer> newestShopAdditionalServicesId = transferProductResponse.getNewestShopAdditionalServicesId();
        //添加转单记录流水ct_transfer_order_record
        Date now = new Date();
        TransferOrderRecord model = new TransferOrderRecord();
        model.setOrderId(orderId);
        model.setCreateTime(now);
        model.setUpdateTime(now);
        model.setOperator(request.getOperator());
        model.setRemark(request.getRemark());
        model.setTransferredShopId(transferredShopId);
        model.setTransferredProductId(productId);
        model.setTransferredSkuId(userOrderItems.getSkuId());
        model.setTransferredSnapShotId(userOrderItems.getSnapShotId());
        model.setTransferShopId(transferShopId);
        model.setTransferProductId(newestProductId);
        model.setTransferSkuId(newestSkuId);
        model.setTransferSnapShotId(newestSnapShotId);
        transferOrderRecordDao.save(model);
        //修改主表ct_user_orders的shop_id和product_id
        UserOrders orders = new UserOrders();
        orders.setShopId(transferShopId);
        orders.setProductId(newestProductId);
        orders.setUpdateTime(now);
        userOrdersDao.update(orders, new UpdateWrapper<UserOrders>().eq("order_id", orderId));
        //修改ct_order_by_stages修改shop_id
        OrderByStages orderByStages = new OrderByStages();
        orderByStages.setShopId(transferShopId);
        orderByStagesDao.update(orderByStages, new UpdateWrapper<OrderByStages>().eq("order_id", orderId));
        //修改ct_user_order_items的字段
        UserOrderItems items = new UserOrderItems();
        items.setProductId(newestProductId);
        items.setSkuId(newestSkuId);
        items.setSnapShotId(newestSnapShotId);
        userOrderItemsDao.update(items, new UpdateWrapper<UserOrderItems>().eq("order_id", orderId));
        //ct_order_additional_services增值服务表修改shop_additional_services_id
        orderAdditionalServicesDao.batchSaveOrderAdditional(additionalServices, newestShopAdditionalServicesId, isHasAdditonal);
        //添加订单操作记录 add at 2020年10月27日13:58:41
        AsyncUtil.runAsync(
                () -> orderOperateCore.orderOperationRegister(orderId, userOrders.getStatus(),
                        userOrders.getStatus(), request.getOperatorId(), request.getOperator(), "平台转单"));
        RedisUtil.del(jedisOrderIdKey);
        return Boolean.TRUE;
    }



    private Boolean checkTransferOrder(TransferOrderRequest request) {
        TransferOrderRecord model = this.transferOrderRecordDao.getOne(new QueryWrapper<>(new TransferOrderRecord())
                .eq("order_id", request.getOrderId())
                .eq("transfer_shop_id", request.getTransferShopId())
                .orderByDesc("id").last("limit 1"));
        if (null != model) {
            throw new HzsxBizException("-1", "同一个订单号不允许重复流转到同一个商家！");
        }
        return Boolean.TRUE;
    }


}