package com.rent.service.export.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdcardUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.rent.common.constant.ShopSplitRuleConstant;
import com.rent.common.dto.export.AuditLogDto;
import com.rent.common.dto.export.PrequalificationSheetDto;
import com.rent.common.dto.export.ReceiptConfirmationReceiptDto;
import com.rent.common.dto.product.*;
import com.rent.common.enums.export.EnumOrderScale;
import com.rent.common.enums.export.ExportFileName;
import com.rent.common.enums.export.TypeFileName;
import com.rent.common.enums.export.WordExportMethods;
import com.rent.config.annotation.ExportWordFile;
import com.rent.config.outside.OutsideConfig;
import com.rent.config.outside.PlatformChannelDto;
import com.rent.dao.order.*;
import com.rent.dao.product.ProductSnapshotsDao;
import com.rent.dao.product.ShopDao;
import com.rent.dao.user.UserCertificationDao;
import com.rent.model.order.*;
import com.rent.model.product.ProductSnapshots;
import com.rent.model.product.Shop;
import com.rent.model.user.UserCertification;
import com.rent.service.export.FormWordService;
import com.rent.service.product.PlatformChannelService;
import com.rent.service.product.SplitBillConfigService;
import com.rent.service.user.DistrictService;
import com.rent.util.AppParamUtil;
import com.rent.util.DateUtil;
import com.rent.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2021-07-16 17:09
 **/
@Service
@Slf4j
public class FormWordServiceImpl implements FormWordService {

    private OrderByStagesDao orderByStagesDao;
    private ProductSnapshotsDao productSnapshotsDao;
    private OrderAuditRecordDao orderAuditRecordDao;
    private SplitBillConfigService splitBillConfigService;
    private UserOrdersDao userOrdersDao;
    private UserCertificationDao userCertificationDao;
    private OrderAddressDao orderAddressDao;
    private DistrictService districtService;
    private OrderLocationAddressDao orderLocationAddressDao;
    private UserOrderItemsDao userOrderItemsDao;
    private UserOrderCashesDao userOrderCashesDao;
    private ShopDao shopDao;
    private PlatformChannelService platformChannelService;

    public FormWordServiceImpl(OrderByStagesDao orderByStagesDao, ProductSnapshotsDao productSnapshotsDao, OrderAuditRecordDao orderAuditRecordDao, SplitBillConfigService splitBillConfigService, UserOrdersDao userOrdersDao, UserCertificationDao userCertificationDao, OrderAddressDao orderAddressDao, DistrictService districtService, OrderLocationAddressDao orderLocationAddressDao, UserOrderItemsDao userOrderItemsDao, UserOrderCashesDao userOrderCashesDao, ShopDao shopDao, PlatformChannelService platformChannelService) {
        this.orderByStagesDao = orderByStagesDao;
        this.productSnapshotsDao = productSnapshotsDao;
        this.orderAuditRecordDao = orderAuditRecordDao;
        this.splitBillConfigService = splitBillConfigService;
        this.userOrdersDao = userOrdersDao;
        this.userCertificationDao = userCertificationDao;
        this.orderAddressDao = orderAddressDao;
        this.districtService = districtService;
        this.orderLocationAddressDao = orderLocationAddressDao;
        this.userOrderItemsDao = userOrderItemsDao;
        this.userOrderCashesDao = userOrderCashesDao;
        this.shopDao = shopDao;
        this.platformChannelService = platformChannelService;
    }


    @ExportWordFile(fileName = ExportFileName.PREQUALIFICATION_SHEET, exportDtoClazz = PrequalificationSheetDto.class, fileType = TypeFileName.WORD, method = WordExportMethods.PREQUALIFICATION_SHEET)
    @Override
    public PrequalificationSheetDto prequalificationSheet(String orderId) {
        PrequalificationSheetDto dto = new PrequalificationSheetDto();
        if (StringUtil.isNotEmpty(orderId)) {
            BigDecimal totolRent = BigDecimal.ZERO;
            BigDecimal buyOutRent = BigDecimal.ZERO;
            BigDecimal toAgentOpeBuyOutCommission = BigDecimal.ZERO;
            EnumOrderScale enumOrderScale = EnumOrderScale.NORMAL;

            UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
            dto.setOrderId(userOrders.getOrderId());
            dto.setUserName(userOrders.getUserName());
            dto.setShopId(userOrders.getShopId());

            UserCertification userCertification = userCertificationDao.getByUid(userOrders.getUid());
            dto.setAge(IdcardUtil.getAgeByIdCard(userCertification.getIdCard())+"");
            dto.setUserPhone(userCertification.getTelephone());
            dto.setOrderNumbers(userOrdersDao.countUserRentingOrder(userOrders.getUid()));
            if("03".equals(userOrders.getFaceAuthStatus())){
                dto.setUserFaceCertStatus("已认证");
            }else {
                dto.setUserFaceCertStatus("未认证");
            }
            OrderLocationAddress orderLocationAddress = orderLocationAddressDao.getByOrderId(orderId);
            if(orderLocationAddress!=null){
                dto.setOrderLocationAddress(orderLocationAddress.getProvince()+orderLocationAddress.getCity()+orderLocationAddress.getDistrict()+orderLocationAddress.getStreetNumber());
            }
            OrderAddress orderAddress = orderAddressDao.queryByOrderId(orderId);
            String provinceStr = districtService.getNameByDistrictId(orderAddress.getProvince().toString());
            String cityStr = districtService.getNameByDistrictId(orderAddress.getCity().toString());
            String areaStr = "";
            if(orderAddress.getArea()!=null){
                areaStr = districtService.getNameByDistrictId(orderAddress.getCity().toString());
            }
            dto.setRecipientName(orderAddress.getRealname());
            dto.setRecipientPhone(orderAddress.getTelephone());
            dto.setRecipientAddress(provinceStr+cityStr+areaStr);
            dto.setRecipientStreet(orderAddress.getStreet());
            dto.setUserRemark(userOrders.getRemark());
            dto.setProductId(userOrders.getProductId());
            UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(orderId);
            dto.setSkuId(userOrderItems.getSkuId());
            dto.setSnapShotId(userOrderItems.getSnapShotId());
            dto.setRentDuration(userOrders.getRentDuration());
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
            dto.setTotalRent(userOrderCashes.getTotalRent());
            dto.setChannelId(userOrders.getChannelId());


            if (null != dto) {
                if (StringUtil.isNotEmpty(dto.getRecipientStreet()) && StringUtil.isNotEmpty(dto.getRecipientAddress())) {
                    dto.setRecipientAddress(dto.getRecipientAddress() + "," + dto.getRecipientStreet());
                }
                dto.setAge(StringUtil.isNotEmpty(dto.getAge()) ? dto.getAge() : "--");
                //分期账单
                List<OrderByStages> stages = orderByStagesDao.queryAllOrderByOrderId(orderId);
                if (stages.size() > 1) {
                    dto.setMonthlyPay(stages.get(1).getCurrentPeriodsRent());
                } else {
                    dto.setMonthlyPay(stages.get(0).getCurrentPeriodsRent());
                }
                //上面的求和不能过滤bigDecimal对象为null的情况，可能会报空指针，这种情况，我们可以用filter方法过滤，或者重写求和方法
                BigDecimal totolRentBuy = stages.stream().map(OrderByStages::getCurrentPeriodsRent).reduce(BigDecimal.ZERO, BigDecimal::add);
                //获取商品信息
                OrderProductDetailDto orderProductDetailDto = this.getOrderProductDetail(dto.getSnapShotId(), dto.getSkuId(), dto.getRentDuration());
                if (null != orderProductDetailDto) {
                    BigDecimal salePrice = orderProductDetailDto.getSalePrice();
                    totolRent = dto.getTotalRent();
                    dto.setSpecColor(orderProductDetailDto.getSkuTitle());
                    dto.setProductName(orderProductDetailDto.getProductName());
                    dto.setSalePrice(salePrice);
                    if (orderProductDetailDto.getBuyOutSupport() > 0) {
                        BigDecimal dueBuyOutAmount = salePrice.subtract(totolRentBuy).setScale(2, BigDecimal.ROUND_HALF_UP);
                        buyOutRent = dueBuyOutAmount.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : dueBuyOutAmount;
                        dto.setDueBuyOutAmount(String.valueOf(buyOutRent));
                        toAgentOpeBuyOutCommission = buyOutRent.multiply(new BigDecimal("0.12")).setScale(2, BigDecimal.ROUND_HALF_UP);
                        dto.setToAgentOpeBuyOutCommission(String.valueOf(toAgentOpeBuyOutCommission));
                    } else {
                        enumOrderScale = EnumOrderScale.NO_SUPPERT_BUY_OUT;
                    }
                }
                //审核信息
                OrderAuditRecord orderAuditRecord = orderAuditRecordDao.getOrderAuditRecordByOrderId(orderId);
                List<AuditLogDto> auditLogDtoList = new ArrayList<>();
                AuditLogDto auditLogDto = new AuditLogDto();
                auditLogDto.setAuditContent(orderAuditRecord.getRemark());
                auditLogDto.setAuditTime(DateUtil.date2String(orderAuditRecord.getUpdateTime()));
                auditLogDto.setAuditOpetor(orderAuditRecord.getApproveUserName());
                auditLogDtoList.add(auditLogDto);
                dto.setAudits(auditLogDtoList);

                //代运营佣金
                BigDecimal toAgentOpeRentCommission = totolRent.multiply(new BigDecimal("0.12")).setScale(2, BigDecimal.ROUND_HALF_UP);
                dto.setToAgentOpeRentCommission(toAgentOpeRentCommission);
                dto.setToAgentOpeAmount(toAgentOpeRentCommission.add(toAgentOpeBuyOutCommission).setScale(2, BigDecimal.ROUND_HALF_UP));
                //平台佣金
                SpiltBillRuleConfigDto rentConfig = splitBillConfigService.getUseAbleConfigByType(dto.getShopId(),new Date(),ShopSplitRuleConstant.TYPE_RENT, AppParamUtil.getChannelId());
                if(rentConfig!=null){
                    dto.setScale(rentConfig.getScale());
                    BigDecimal toOpeAmount = totolRent.multiply(BigDecimal.ONE.subtract(dto.getScale())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    dto.setToOpeRentCommission(String.valueOf(toOpeAmount));
                    dto.setToOpeAmount(String.valueOf(toOpeAmount));
                }else {
                    enumOrderScale = EnumOrderScale.SHOP_SPLIT_ERROR;
                    dto.setToOpeRentCommission(EnumOrderScale.SHOP_SPLIT_ERROR.getDescription());
                    dto.setToOpeAmount(EnumOrderScale.SHOP_SPLIT_ERROR.getDescription());
                }
                SpiltBillRuleConfigDto buyoutConfig = splitBillConfigService.getUseAbleConfigByType(dto.getShopId(),new Date(),ShopSplitRuleConstant.TYPE_BUY_OUT, AppParamUtil.getChannelId());
                if(buyoutConfig!=null){
                    dto.setBuyOutScale(buyoutConfig.getScale());
                }else {
                    enumOrderScale = EnumOrderScale.SHOP_SPLIT_ERROR;
                    dto.setToOpeBuyOutCommission(EnumOrderScale.SHOP_SPLIT_ERROR.getDescription());
                }
                switch (enumOrderScale) {
                    case NORMAL:
                        BigDecimal toOpeBuyOutCommission = buyOutRent.multiply(BigDecimal.ONE.subtract(dto.getBuyOutScale())).setScale(2, BigDecimal.ROUND_HALF_UP);
                        dto.setToOpeBuyOutCommission(String.valueOf(toOpeBuyOutCommission));
                        dto.setToOpeAmount(String.valueOf(new BigDecimal(dto.getToOpeAmount()).add(toOpeBuyOutCommission).setScale(2, BigDecimal.ROUND_HALF_UP)));
                        break;
                    case NO_SUPPERT_BUY_OUT:
                        dto.setToOpeBuyOutCommission(EnumOrderScale.NO_SUPPERT_BUY_OUT.getDescription());
                        dto.setDueBuyOutAmount(EnumOrderScale.NO_SUPPERT_BUY_OUT.getDescription());
                        dto.setToOpeBuyOutCommission(EnumOrderScale.NO_SUPPERT_BUY_OUT.getDescription());
                        dto.setToAgentOpeBuyOutCommission(EnumOrderScale.NO_SUPPERT_BUY_OUT.getDescription());
                        break;
                }
            }
        }
        return dto;
    }

    @ExportWordFile(fileName = ExportFileName.RECEIPT_CONFIRMATION_RECEIPT, exportDtoClazz = ReceiptConfirmationReceiptDto.class, fileType = TypeFileName.WORD, method = WordExportMethods.RECEIPT_CONFIRMATION_RECEIPT)
    @Override
    public ReceiptConfirmationReceiptDto receiptConfirmationReceipt(String orderId) {
        ReceiptConfirmationReceiptDto dto = new ReceiptConfirmationReceiptDto();
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        dto.setOrderId(userOrders.getOrderId());
        Shop shop = shopDao.getByShopId(userOrders.getShopId());
        dto.setShopName(shop.getName());
        dto.setChannelId(userOrders.getChannelId());
        PlatformChannelDto platformChannelDto = platformChannelService.getPlatFormChannel(userOrders.getChannelId());
        dto.setEnterpriseName(platformChannelDto.getEnterpriseName());
        dto.setAppName("支付宝");
        dto.setChannelName(OutsideConfig.APP_NAME);
        dto.setUserName(userOrders.getUserName());
        UserCertification userCertification = userCertificationDao.getByUid(userOrders.getUid());
        dto.setUserPhone(userCertification.getTelephone());
        dto.setDuration(userOrders.getRentDuration());
        dto.setExpressNo(userOrders.getExpressNo());
        dto.setProductId(userOrders.getProductId());
        UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(orderId);
        dto.setSkuId(userOrderItems.getSkuId());
        dto.setSnapShotId(userOrderItems.getSnapShotId());

        OrderAddress orderAddress = orderAddressDao.queryByOrderId(orderId);
        dto.setRecipientPhone(orderAddress.getTelephone());

        OrderByStages orderByStages = orderByStagesDao.getEarliestPeriod(orderId);
        dto.setTotalPeriods(orderByStages.getTotalPeriods().toString());
        dto.setTotalRent(orderByStages.getTotalRent());
        //获取商品信息
        OrderProductDetailDto orderProductDetailDto = this.getOrderProductDetail(dto.getSnapShotId(), dto.getSkuId(), dto.getDuration());
        if (null != orderProductDetailDto) {
            BigDecimal salePrice = orderProductDetailDto.getSalePrice();
            dto.setSpecColor(orderProductDetailDto.getSkuTitle());
            dto.setProductName(orderProductDetailDto.getProductName());
        }
        return dto;
    }

    public OrderProductDetailDto getOrderProductDetail(Long id, Long skuId, Integer rentDuration) {
        if (null == id) {
            return null;
        }
        OrderProductDetailDto orderProductDetailDto = new OrderProductDetailDto();
        //获取商品信息
        ProductSnapshots productSnapshots = productSnapshotsDao.getById(id);
        if (null != productSnapshots && StringUtil.isNotEmpty((String) productSnapshots.getData())) {
            ShopProductSnapResponse productSnapshot = JSONObject.parseObject((String) productSnapshots.getData(),ShopProductSnapResponse.class);
            ProductDto productDto = productSnapshot.getProduct();
            if (productDto != null) {
                orderProductDetailDto.setId(productDto.getId());
                orderProductDetailDto.setProductName(productDto.getName());
                orderProductDetailDto.setProductId(productDto.getProductId());
                orderProductDetailDto.setShopId(productDto.getShopId());
                //快照sku
                List<ShopProductSnapSkusResponse> shopProductSnapSkuses = productSnapshot.getShopProductSnapSkus();
                if (CollectionUtil.isNotEmpty(shopProductSnapSkuses)) {
                    for (ShopProductSnapSkusResponse shopProductSnapSkus : shopProductSnapSkuses) {
                        ProductSkusDto productSkusDto = shopProductSnapSkus.getProductSkus();
                        if (skuId.intValue() == productSkusDto.getId()) {
                            //获取销售价 有问题不同租期的销售价不同这里取第一个不正确 增加租期判断
                            if (CollectionUtil.isNotEmpty(shopProductSnapSkus.getCyclePrices())) {
                                shopProductSnapSkus.getCyclePrices()
                                        .stream()
                                        .filter(a -> skuId
                                                .equals(a.getSkuId()) && a.getDays().equals(rentDuration))
                                        .findFirst()
                                        .ifPresent(productCyclePricesDto -> orderProductDetailDto.setSalePrice(
                                                productCyclePricesDto.getSalePrice()));
                                //老系统中支持自定义租期，有些订单租期在系统中找不到，买断时候会有问题
                                //修复方法是判断用户的订单租期是否在快照中可以找到，找到取找到的；
                                //找不到的话，取订单租期最接近的下一个快照租期时长的销售价
                                //若订单租期是最大，在快照租期中找不到，取快照租期租期最大的销售价
                                if (null == orderProductDetailDto.getSalePrice()) {
                                    List<ProductCyclePricesDto> cyclePrices = shopProductSnapSkus.getCyclePrices()
                                            .stream()
                                            .filter(a -> skuId.equals(a.getSkuId())
                                            )
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(cyclePrices)) {
                                        ProductCyclePricesDto maxCyclePricesDto = cyclePrices.stream().max(Comparator.comparing(ProductCyclePricesDto::getDays)).get();
                                        //订单租期大于快照租期，取快照租期最大的销售价;否则取最接近订单租期的下一个快照租期的销售价
                                        Boolean isMoreMax = rentDuration.compareTo(maxCyclePricesDto.getDays()) == 1;
                                        if (isMoreMax) {
                                            orderProductDetailDto.setSalePrice(maxCyclePricesDto.getSalePrice());
                                        } else {
                                            cyclePrices.stream()
                                                    .filter(a -> rentDuration.compareTo(a.getDays()) == -1)
                                                    .findFirst()
                                                    .ifPresent(productCyclePricesDto -> orderProductDetailDto.setSalePrice(
                                                            productCyclePricesDto.getSalePrice()));
                                        }
                                    }
                                }
                            }
                            orderProductDetailDto.setCyclePrices(shopProductSnapSkus.getCyclePrices());
                            orderProductDetailDto.setMarketPrice(shopProductSnapSkus.getProductSkus()
                                    .getMarketPrice());
                            //买断功能上线之前不存在买断字段，默认设置为不支持买断
                            orderProductDetailDto.setBuyOutSupport(null == productSkusDto.getBuyOutSupport() ? 0 : productSkusDto.getBuyOutSupport());
                            List<ProductSkuValuesDto> skuValues = shopProductSnapSkus.getSkuValues();
                            if (CollectionUtil.isNotEmpty(skuValues)) {
                                String name = "";
                                for (ProductSkuValuesDto productSkuValues : skuValues) {
                                    Long specValueId = productSkuValues.getSpecValueId();
                                    if (specValueId != null) {
                                        List<ShopProductSnapSpecResponse> shopProductSnapSpecs
                                                = productSnapshot.getSnapSpecs();
                                        for (ShopProductSnapSpecResponse shopProductSnapSpec : shopProductSnapSpecs) {
                                            if (shopProductSnapSpec != null) {
                                                List<ShopProductSnapSpecResponse.ShopProductSnapSpecValue>
                                                        shopProductSnapSpecValues
                                                        = shopProductSnapSpec.getSpecValues();
                                                if (CollectionUtil.isNotEmpty(shopProductSnapSpecValues)) {
                                                    for (ShopProductSnapSpecResponse.ShopProductSnapSpecValue specValue : shopProductSnapSpecValues) {
                                                        if (specValue != null) {
                                                            ProductSpecValueDto productSpecValue
                                                                    = specValue.getProductSpecValue();
                                                            if (productSpecValue != null &&
                                                                    productSpecValue.getId()
                                                                            .intValue() == specValueId) {
                                                                String specName = productSpecValue.getName();
                                                                name += "/" + specName;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                try {
                                    orderProductDetailDto.setSkuTitle(
                                            name.length() > 0 ? name.substring(1) : "");
                                } catch (Exception e) {
                                    log.error(skuValues + "1069  ***********  ", e);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        return orderProductDetailDto;
    }
}
