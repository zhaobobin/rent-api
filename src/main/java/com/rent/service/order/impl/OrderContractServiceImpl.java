package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.rent.common.converter.order.OrderByStagesConverter;
import com.rent.common.dto.components.dto.OrderContractDto;
import com.rent.common.dto.components.response.ContractResultResponse;
import com.rent.common.dto.order.OrderAddressDto;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.dto.order.OrderDetailResponse;
import com.rent.common.dto.product.ShopEnterpriseInfosDto;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.util.OSSFileUtils;
import com.rent.config.outside.OutsideConfig;
import com.rent.config.outside.PlatformChannelDto;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.OrderContractDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.OrderContract;
import com.rent.service.components.ContractSignService;
import com.rent.service.order.FeeBillService;
import com.rent.service.order.OrderContractService;
import com.rent.service.order.UserOrdersQueryService;
import com.rent.service.product.PlatformChannelService;
import com.rent.service.product.ShopEnterpriseInfosService;
import com.rent.service.user.DistrictService;
import com.rent.util.DateUtil;
import com.rent.util.LoginUserUtil;
import com.rent.util.PDFGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-13 下午 8:14:47
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderContractServiceImpl implements OrderContractService {

    private static final String savePath = OutsideConfig.TEMP_FILE_DIR + File.separator;

    private final OrderContractDao orderContractDao;
    private final UserOrdersQueryService userOrdersQueryService;
    private final OrderByStagesDao orderByStagesDao;
    private final PlatformChannelService platformChannelService;
    private final ShopEnterpriseInfosService shopEnterpriseInfosService;
    private final DistrictService districtService;
    private final ContractSignService contractSignService;
    private final FeeBillService feeBillService;
    private final OSSFileUtils ossFileUtils;

    private OrderContractDto getOrderContractDtoInfo(String orderId, OrderDetailResponse response) {
        if (response == null) {
            response = userOrdersQueryService.selectUserOrderDetail(orderId);
        }
        ShopEnterpriseInfosDto enterpriseInfosDto = shopEnterpriseInfosService.queryShopEnterpriseInfosDetailByshopId(response.getShopDto().getShopId());

        OrderContractDto orderContractDto = new OrderContractDto();
        orderContractDto.setOrderId(orderId);
        orderContractDto.setShopLegalName(enterpriseInfosDto.getRealname());
        orderContractDto.setShopLegalIdNo(enterpriseInfosDto.getIdentity());
        orderContractDto.setShopLegalPhone(enterpriseInfosDto.getContactTelephone());
        orderContractDto.setBusinessLicenseNo(enterpriseInfosDto.getBusinessLicenseNo());
        orderContractDto.setEnterpriseName(enterpriseInfosDto.getName());
        orderContractDto.setShopName(enterpriseInfosDto.getName());
        orderContractDto.setShopAddress(response.getShopDto().getShopAddress());
        if (response.getUserCertification() != null) {
            orderContractDto.setUserName(response.getUserOrdersDto().getUserName());
            orderContractDto.setIdNo(response.getUserCertification().getIdCard());
            orderContractDto.setTelephone(response.getUserCertification().getTelephone());
        } else {
            orderContractDto.setUserName("-");
            orderContractDto.setIdNo("-");
            orderContractDto.setTelephone("-");
        }
        orderContractDto.setProductName(response.getOrderProductDetailDto().getProductName());
        //账单信息
        List<OrderByStages> orderByStages = orderByStagesDao.queryOrderByOrderId(orderId);

        orderContractDto.setOrderByStagesDtoList(OrderByStagesConverter.modelList2DtoList(orderByStages));
        //平台来源渠道
        PlatformChannelDto platformChannelDto = platformChannelService.getPlatFormChannel(response.getUserOrdersDto().getChannelId());
        if (platformChannelDto != null) {
            orderContractDto.setPlatformName(platformChannelDto.getEnterpriseName());
            orderContractDto.setPlatformAddress(platformChannelDto.getEnterpriseAddress());
            orderContractDto.setPlatform(platformChannelDto.getPlatformName());
            orderContractDto.setCompany(platformChannelDto.getEnterpriseName());
            orderContractDto.setPlatformLicenseNo(platformChannelDto.getEnterpriseLicenseNo());
            orderContractDto.setPlatformLegalName(platformChannelDto.getEnterpriseLegalName());
            orderContractDto.setPlatformLegalIdNo(platformChannelDto.getEnterpriseLegalIdNo());
            orderContractDto.setPlatformLegalPhone(platformChannelDto.getEnterpriseLegalPhone());
            orderContractDto.setPlatformContactTelephone(platformChannelDto.getEnterpriseLegalPhone());
            orderContractDto.setSignAddress(platformChannelDto.getContractSignCity());
        } else {
            throw new IllegalStateException("Unexpected value: " + response.getUserOrdersDto().getChannelId());
        }

        orderContractDto.setOrderId(response.getUserOrdersDto().getOrderId());
        OrderAddressDto orderAddressDto = response.getOrderAddressDto();
        if (null != orderAddressDto) {
            Map<String, String> distinctNameMap = districtService.getDistinctName(Arrays.asList(
                    orderAddressDto.getProvince().toString(),
                    orderAddressDto.getCity().toString(),
                    orderAddressDto.getArea().toString()));
            orderContractDto.setReciveCity(distinctNameMap.get(orderAddressDto.getProvince().toString())
                    + distinctNameMap.get(orderAddressDto.getCity().toString()));
            orderContractDto.setReciveAddress(distinctNameMap.get(orderAddressDto.getProvince().toString())
                    + distinctNameMap.get(orderAddressDto.getCity().toString())
                    + distinctNameMap.get(orderAddressDto.getArea().toString())
                    + response.getOrderAddressDto().getStreet());
            orderContractDto.setReciveName(orderAddressDto.getRealname());
        }
        orderContractDto.setSkuTitle(response.getOrderProductDetailDto().getSkuTitle());
        orderContractDto.setTotalPeriod(response.getOrderByStagesDtoList().get(0).getTotalPeriods().toString());
        orderContractDto.setTotalRent(response.getUserOrderCashesDto().getTotalRent().toPlainString());
        if (CollectionUtil.isNotEmpty(response.getOrderByStagesDtoList())) {
            orderContractDto.setOriginalRent(response.getOrderByStagesDtoList()
                    .size() == 1 ? response.getOrderByStagesDtoList()
                    .get(0)
                    .getCurrentPeriodsRent()
                    .toPlainString() : response.getOrderByStagesDtoList()
                    .get(1)
                    .getCurrentPeriodsRent()
                    .toPlainString());
        }
        if (null != response.getOrderProductDetailDto().getSalePrice()) {
            orderContractDto.setSalePrice(response.getOrderProductDetailDto().getSalePrice().toPlainString());
        }

        if (response.getOrderProductDetailDto().getBuyOutSupport().intValue() != 0) {
            BigDecimal buyOutPrice = response.getOrderProductDetailDto().getSalePrice()
                    .subtract(response.getUserOrderCashesDto().getTotalRent());
            buyOutPrice = buyOutPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : buyOutPrice;
            orderContractDto.setBuyOutPrice(buyOutPrice.toPlainString());
        } else {
            orderContractDto.setBuyOutPrice("-");
        }
        orderContractDto.setCreateTime(DateUtil.date2String(response.getUserOrdersDto().getCreateTime(), DateUtil.DATE_FORMAT_3));
        return orderContractDto;
    }

    @Override
    public OrderContractDto getOrderContractInfo(String orderId) {
        return getOrderContractDtoInfo(orderId, null);
    }

    @Override
    public String signOrderContract(String orderId) {
        feeBillService.contractBilling(orderId);
        OrderContract orderContract = orderContractDao.getByOrderId(orderId);
        if (orderContract == null) {
            orderContract = new OrderContract();
        }
        if (StringUtils.isNotEmpty(orderContract.getSignedPdf())) {
            return orderContract.getSignedPdf();
        }
        orderContract.setBackstageUserId(LoginUserUtil.getLoginUser().getId());

        OrderDetailResponse response = userOrdersQueryService.selectUserOrderDetail(orderId);
        if (response.getUserOrdersDto().getStatus() != EnumOrderStatus.PENDING_DEAL) {
            throw new HzsxBizException("-1", "只有待发货订单才能签署协议!");
        }
        OrderContractDto orderContractDto = getOrderContractDtoInfo(orderId, response);
        orderContract.setShopId(response.getShopDto().getShopId());
        orderContract.setOrderId(orderId);
        String localFile = generateOrderContractPdfLocalFile(orderContractDto);
        ContractResultResponse contractResultResponse = contractSignService.signOrderContract(new File(localFile), orderContractDto);
        String fileUrl = contractResultResponse.getFileUrl();
        orderContract.setSignedPdf(fileUrl);
        orderContract.setContractCode(contractResultResponse.getContractCode());
        orderContract.setSignerCode(contractResultResponse.getSignerCodes());
        orderContractDao.saveOrUpdateContract(orderContract);
        return orderContract.getSignedPdf();
    }

    @Override
    public String generateOrderContractUnsignedFile(String orderId) {
        OrderContractDto orderContractDto = getOrderContractInfo(orderId);
        String orderContractPdfPath = generateOrderContractPdfLocalFile(orderContractDto);
        String orderContractUrl = ossFileUtils.uploadFile("unSignContract", orderContractPdfPath, orderId + ".pdf");
        OrderContract orderContract = orderContractDao.getByOrderId(orderId);
        if (orderContract == null) {
            orderContract = new OrderContract();
        }
        orderContract.setOrderId(orderId);
        orderContract.setOriginPdf(orderContractUrl);
        orderContractDao.saveOrUpdateContract(orderContract);
        return orderContract.getSignedPdf();
    }

    /**
     * 生成订单合同文件保存在本地
     *
     * @param orderContractDto
     * @return 合同文件本地地址
     */
    private String generateOrderContractPdfLocalFile(OrderContractDto orderContractDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("shopName", orderContractDto.getShopName());
        map.put("shopAddress", orderContractDto.getShopAddress());

        map.put("userName", orderContractDto.getUserName());
        map.put("idNo", orderContractDto.getIdNo());
        map.put("telephone", orderContractDto.getTelephone());
        map.put("productName", orderContractDto.getProductName());

        map.put("platformName", orderContractDto.getPlatformName());
        map.put("platformAddress", orderContractDto.getPlatformAddress());
        map.put("platform", orderContractDto.getPlatform());
        map.put("company", orderContractDto.getCompany());

        map.put("orderId", orderContractDto.getOrderId());
        map.put("receiveCity", orderContractDto.getReciveCity());
        map.put("receiveAddress", orderContractDto.getReciveAddress());
        map.put("reciveName", orderContractDto.getReciveName());
        map.put("skuTitle", orderContractDto.getSkuTitle());
        map.put("totalPeriod", orderContractDto.getTotalPeriod());
        map.put("totalRent", orderContractDto.getTotalRent());
        map.put("salePrice", orderContractDto.getSalePrice());
        map.put("buyOutPrice", orderContractDto.getBuyOutPrice());
        map.put("createTime", orderContractDto.getCreateTime());
        map.put("platformContactTelephone", orderContractDto.getPlatformContactTelephone());
        List<OrderByStagesDto> orderByStagesDtoList = orderContractDto.getOrderByStagesDtoList();
        List<String> stageList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orderByStagesDtoList)) {
            orderByStagesDtoList.forEach(item -> {
                stageList.add("第" + item.getCurrentPeriods() + "期租金：" + item.getCurrentPeriodsRent());
            });
        } else {
            stageList.add("每期租金：" + orderContractDto.getOriginalRent());
        }
        map.put("stageList", stageList);
        try {
            return PDFGenerator.generate(savePath, orderContractDto.getOrderId() + ".pdf", "orderContract", map, orderContractDto.getOrderId());
        } catch (Exception e) {
            log.error("生成合同pdf失败", e);
            throw new HzsxBizException("-1", "生成合同pdf失败！请联系技术人员处理！");
        }
    }
}
