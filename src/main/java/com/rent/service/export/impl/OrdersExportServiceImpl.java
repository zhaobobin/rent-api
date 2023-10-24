package com.rent.service.export.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.CategoryType;
import com.rent.common.dto.backstage.ExportRentOrderReq;
import com.rent.common.dto.export.OrderExportDto;
import com.rent.common.dto.order.ChannelOrdersExportDto;
import com.rent.common.dto.order.resquest.ChannelUserOrdersReqDto;
import com.rent.common.enums.export.ExportFileName;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.util.AmountUtil;
import com.rent.config.annotation.ExportFile;
import com.rent.dao.order.ChannelUserOrdersDao;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.OrderHastenDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.dao.product.OpeCategoryDao;
import com.rent.dao.product.OpeCategoryProductDao;
import com.rent.dao.product.ProductDao;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.OrderHasten;
import com.rent.model.product.OpeCategory;
import com.rent.model.product.OpeCategoryProduct;
import com.rent.model.product.Product;
import com.rent.service.export.OrdersExportService;
import com.rent.service.product.PlatformChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhaowenchao
 */
@Service
@Slf4j
public class OrdersExportServiceImpl implements OrdersExportService {

    private ProductDao productDao;
    private UserOrdersDao userOrdersDao;
    private OrderByStagesDao orderByStagesDao;
    private OrderHastenDao orderHastenDao;
    private PlatformChannelService platformChannelService;
    private OpeCategoryProductDao opeCategoryProductDao;
    private OpeCategoryDao opeCategoryDao;
    private ChannelUserOrdersDao channelUserOrdersDao;

    public OrdersExportServiceImpl(ProductDao productDao, UserOrdersDao userOrdersDao, OrderByStagesDao orderByStagesDao, OrderHastenDao orderHastenDao, PlatformChannelService platformChannelService, OpeCategoryProductDao opeCategoryProductDao, OpeCategoryDao opeCategoryDao, ChannelUserOrdersDao channelUserOrdersDao) {
        this.productDao = productDao;
        this.userOrdersDao = userOrdersDao;
        this.orderByStagesDao = orderByStagesDao;
        this.orderHastenDao = orderHastenDao;
        this.platformChannelService = platformChannelService;
        this.opeCategoryProductDao = opeCategoryProductDao;
        this.opeCategoryDao = opeCategoryDao;
        this.channelUserOrdersDao = channelUserOrdersDao;
    }


    @Override
    @ExportFile(fileName=ExportFileName.RENT_ORDER_LIST,exportDtoClazz=OrderExportDto.class)
    public List<OrderExportDto> rentOrder(ExportRentOrderReq request) {
        List<OrderExportDto> list = userOrdersDao.getRentOrderExport(request);
        if(CollectionUtil.isEmpty(list)){
            return list;
        }
        return packOrderExportInfo(list);
    }

    @Override
    @ExportFile(fileName=ExportFileName.OVER_DUE_ORDER,exportDtoClazz=OrderExportDto.class)
    public List<OrderExportDto> overdueOrder(ExportRentOrderReq request) {
        List<OrderExportDto> list = userOrdersDao.getOverdueOrderExport(request);
        return packOrderExportInfo(list);
    }

    @Override
    @ExportFile(fileName= ExportFileName.NOT_GIVE_BACK,exportDtoClazz=OrderExportDto.class)
    public List<OrderExportDto> notGiveBack(ExportRentOrderReq request) {
        List<OrderExportDto> list = userOrdersDao.getNotGiveBackOrderExport(request);
        return packOrderExportInfo(list);
    }

    @Override
    @ExportFile(fileName=ExportFileName.CHANNEL_RENT_ORDERS,exportDtoClazz=ChannelOrdersExportDto.class)
    public List<ChannelOrdersExportDto> channelRentOrder(ChannelUserOrdersReqDto request) {
        List<ChannelOrdersExportDto> list = channelUserOrdersDao.channelRentOrder(request);
        List<String> orderIdList = list.stream().map(ChannelOrdersExportDto::getOrderId).collect(Collectors.toList());

        //获取订单分期账单信息
        List<OrderByStages> orderStages = orderByStagesDao.list(new QueryWrapper<OrderByStages>().in("order_id",orderIdList));
        Map<String,List<OrderByStages>> orderStagesMap = orderStages.stream().collect(Collectors.groupingBy(OrderByStages::getOrderId));
        for (ChannelOrdersExportDto channelOrdersExportDto : list) {
            String orderId = channelOrdersExportDto.getOrderId();
            List<OrderByStages> stages = orderStagesMap.get(orderId);

            BigDecimal payedRent = BigDecimal.ZERO;
            int payedPeriods = 0;
            if (CollectionUtil.isNotEmpty(stages)) {
                for (OrderByStages stage : stages) {
                    if (EnumOrderByStagesStatus.OVERDUE_PAYED.getCode().equals(stage.getStatus())
                            || EnumOrderByStagesStatus.PAYED.getCode().equals(stage.getStatus())) {
                        payedRent = AmountUtil.safeAdd(payedRent, stage.getCurrentPeriodsRent());
                        payedPeriods++;
                    }
                }
            }
            channelOrdersExportDto.setCurrentPeriods(payedPeriods);
            channelOrdersExportDto.setPayRent(payedRent);
            channelOrdersExportDto.setSettleAmount(payedRent.multiply(channelOrdersExportDto.getScale()));
            channelOrdersExportDto.setStatus(EnumOrderStatus.find(channelOrdersExportDto.getStatus()).getDescription());
        }
        return list;
    }


    /**
     * 封装OrderExportDto
     * @param list
     * @return
     */
    private List<OrderExportDto> packOrderExportInfo(List<OrderExportDto> list){
        if(CollectionUtil.isEmpty(list)){
            return list;
        }
        List<String> orderIdList = list.stream().map(OrderExportDto::getOrderId).collect(Collectors.toList());
        List<String> productIdList = list.stream().map(OrderExportDto::getProductId).collect(Collectors.toList());
        //获取商品ID和商品名称对应信息

        List<Product> productList = productDao.getProductNames(productIdList);
        Map<String,String> productNameMap = productList.stream().collect(Collectors.toMap(Product::getProductId,Product::getName,(k1 , k2)-> k2 ));
        //获取订单分期账单信息

        List<OrderByStages> orderStages = orderByStagesDao.list(new QueryWrapper<OrderByStages>().select("order_id,total_periods,total_rent,current_periods,status,current_periods_rent").in("order_id",orderIdList));
        Map<String,List<OrderByStages>> orderStagesMap = orderStages.stream().collect(Collectors.groupingBy(OrderByStages::getOrderId));
        //获取订单催收信息
        Map<String,List<OrderHasten>> orderHastensMap = orderHastenDao.queryListByOrderIds(orderIdList);
        //获取订单商品与商品分类映射关系

        List<OpeCategoryProduct> opeCategoryProductList = opeCategoryProductDao.list(new QueryWrapper<OpeCategoryProduct>().select("operate_category_id,item_id").in("item_id",productIdList));
        Map<String,Integer> productCategoryIdMap = opeCategoryProductList.stream().collect(Collectors
                .toMap(OpeCategoryProduct::getItemId,OpeCategoryProduct::getOperateCategoryId,(k1 , k2)-> k2 ));

        //获取所有的分类信息
        List<OpeCategory> categoryList = opeCategoryDao.list(new QueryWrapper<OpeCategory>().select("id,name,parent_id,type"));
        Map<Integer,OpeCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(OpeCategory::getId,Function.identity()));

        for (OrderExportDto orderExportDto : list) {
            orderExportDto.setChannelName(platformChannelService.getPlatFormChannel(orderExportDto.getChannelName()).getChannelName());
            String orderId = orderExportDto.getOrderId();
            String productId = orderExportDto.getProductId();
            List<OrderByStages> stages = orderStagesMap.get(orderId);

            orderExportDto.setProductName(productNameMap.get(productId));

            BigDecimal payedRent = BigDecimal.ZERO;
            BigDecimal overAmount = BigDecimal.ZERO;
            int overPaid = 0;
            int payedPeriods = 0;
            if (CollectionUtil.isNotEmpty(stages)) {
                for (OrderByStages stage : stages) {
                    orderExportDto.setTotalPeriods(stage.getTotalPeriods());
                    orderExportDto.setTotalRent(stage.getTotalRent());
                    if (EnumOrderByStagesStatus.OVERDUE_PAYED.getCode().equals(stage.getStatus())
                            || EnumOrderByStagesStatus.PAYED.getCode().equals(stage.getStatus())) {
                        payedRent = AmountUtil.safeAdd(payedRent, stage.getCurrentPeriodsRent());
                        payedPeriods++;
                    }
                    if (EnumOrderByStagesStatus.OVERDUE_NOT_PAY.getCode().equals(stage.getStatus())){
                        overAmount = AmountUtil.safeAdd(overAmount, stage.getCurrentPeriodsRent());
                        overPaid++;
                    }
                }
            }
            orderExportDto.setCurrentPeriods(payedPeriods);
            orderExportDto.setPayedRent(payedRent);
            orderExportDto.setOverPaid(overPaid);
            orderExportDto.setOverAmount(overAmount);

            List<OrderHasten> hastens = orderHastensMap.get(orderId);
            if (CollectionUtil.isNotEmpty(hastens)) {
                StringBuilder opeSb = new StringBuilder();
                StringBuilder businessSb = new StringBuilder();
                int opeIndex = 0;
                int businessIndex = 0;
                for (OrderHasten hasten : hastens){
                    if(hasten.getSource().equals(EnumOrderRemarkSource.OPE)){
                        opeIndex++;
                        opeSb = opeSb.append(opeIndex).append(":");
                        opeSb = opeSb.append(hasten.getUserName()).append(":");
                        opeSb = opeSb.append(hasten.getNotes());
                    }else{
                        businessIndex++;
                        businessSb = businessSb.append(businessIndex).append(":");
                        businessSb = businessSb.append(hasten.getUserName()).append(":");
                        businessSb = businessSb.append(hasten.getNotes());
                    }
                }
                orderExportDto.setOpeHasten(opeSb.toString());
                orderExportDto.setBusinessHasten(businessSb.toString());
            }

            Integer categoryId = productCategoryIdMap.get(productId);
            if(categoryId!=null){
                while (0L!=categoryId){
                    OpeCategory category = categoryMap.get(categoryId);
                    switch (category.getType()) {
                        case CategoryType.THREE_CATEGORY:
                            orderExportDto.setThirdCategory(category.getName());
                            break;
                        case CategoryType.TWO_CATEGORY:
                            orderExportDto.setSecondCategory(category.getName());
                            break;
                        case CategoryType.FIRST_CATEGORY:
                            orderExportDto.setFirstCategory(category.getName());
                            break;
                    }
                    categoryId = category.getParentId();
                }
            }
        }
        return list;
    }
}
