package com.rent.controller.api;


import cn.hutool.core.collection.CollectionUtil;
import com.rent.common.dto.components.dto.OrderContractDto;
import com.rent.common.dto.components.dto.ShopContractDto;
import com.rent.common.dto.components.dto.UserOrderAgreementRequest;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.config.outside.OutsideConfig;
import com.rent.model.user.UserCertification;
import com.rent.service.order.OrderContractService;
import com.rent.service.order.UserOrdersQueryService;
import com.rent.service.product.ShopContractService;
import com.rent.service.user.UserCertificationService;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 签署合同之前预览合同
 *
 * @author zhaowenchao
 */
@Controller
@Tag(name = "预览合同")
@RequiredArgsConstructor
@RequestMapping("/zyj-components-center")
public class ContractController {

    private final OrderContractService orderContractService;
    private final UserOrdersQueryService userOrdersQueryService;
    private final ShopContractService shopContractService;
    private final UserCertificationService userCertificationService;

    @Operation(summary = "预览订单个人信息授权")
    @GetMapping("/contract/pac")
    public String pac(@RequestParam(value = "uid", required = true) String uid,
                      ModelMap map) {
        UserCertificationDto userCertificationDto = userCertificationService.getByUid(uid);
        map.addAttribute("platform", OutsideConfig.COMPANY);
        map.addAttribute("userName", userCertificationDto != null ? userCertificationDto.getUserName() : "");
        map.addAttribute("createTime", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
        return "authorizationAgreement";
    }

    @Operation(summary = "预览订单合同")
    @GetMapping("/contract/order")
    public String preview(@RequestParam(value = "orderId", required = false) String orderId,
                          @RequestParam(value = "uid", required = false) String uid,
                          @RequestParam(value = "productId", required = false) String productId,
                          @RequestParam(value = "tempOrderId", required = false) String tempOrderId,
                          ModelMap map) {
        OrderContractDto dto = getContractDto(orderId, uid, tempOrderId, productId);
        map.addAttribute("shopName", dto.getShopName());
        map.addAttribute("shopAddress", dto.getShopAddress());
        map.addAttribute("userName", dto.getUserName());
        map.addAttribute("idNo", dto.getIdNo());
        map.addAttribute("telephone", dto.getTelephone());
        map.addAttribute("productName", dto.getProductName());
        map.addAttribute("platformName", dto.getPlatformName());
        map.addAttribute("platformAddress", dto.getPlatformAddress());
        map.addAttribute("platform", dto.getPlatform());
        map.addAttribute("company", dto.getCompany());
        map.addAttribute("orderId", dto.getOrderId());
        map.addAttribute("receiveCity", dto.getReciveCity());
        map.addAttribute("receiveAddress", dto.getReciveAddress());
        map.addAttribute("skuTitle", dto.getSkuTitle());
        map.addAttribute("totalPeriod", dto.getTotalPeriod());
        map.addAttribute("totalRent", dto.getTotalRent());
        map.addAttribute("salePrice", dto.getSalePrice());
        map.addAttribute("buyOutPrice", dto.getBuyOutPrice());
        map.addAttribute("reciveName", dto.getReciveName());
        map.addAttribute("platformContactTelephone", dto.getPlatformContactTelephone());

        List<String> stageList = new ArrayList<>();
        List<OrderByStagesDto> orderByStagesDtoList = dto.getOrderByStagesDtoList();
        if (CollectionUtil.isNotEmpty(orderByStagesDtoList)) {
            for (OrderByStagesDto orderByStagesDto : orderByStagesDtoList) {
                stageList.add("第 " + orderByStagesDto.getCurrentPeriods() + " 期租金：" + orderByStagesDto.getCurrentPeriodsRent());
            }
        }
        map.addAttribute("createTime", dto.getCreateTime());
        map.addAttribute("stageList", stageList);
        return "orderContract";
    }

    @Operation(summary = "预览店铺合同")
    @GetMapping("/contract/shop")
    public String generatePdf(@RequestParam(value = "shopId") String shopId, ModelMap map) {
        ShopContractDto dto = shopContractService.getShopContractData(shopId);
        map.addAttribute("userName", dto.getUserName());
        map.addAttribute("name", dto.getName());
        map.addAttribute("businessLicenseNo", dto.getBusinessLicenseNo());
        map.addAttribute("contactName", dto.getContactName());
        map.addAttribute("contactTelephone", dto.getContactTelephone());
        map.addAttribute("start", dto.getStart());
        map.addAttribute("end", dto.getEnd());
        map.addAttribute("zfbName", dto.getZfbName());
        map.addAttribute("zfbCode", dto.getZfbCode());
        map.addAttribute("createTime", dto.getCreateTime());
        map.addAttribute("shopRate", dto.getShopRate());
        map.addAttribute("platformRate", dto.getPlatformRate());
        map.addAttribute("platformEnterpriseName", dto.getPlatformEnterpriseName());
        map.addAttribute("platformLicenseNo", dto.getPlatformLicenseNo());
        map.addAttribute("platformContactName", dto.getPlatformContactName());
        map.addAttribute("platformContactTelephone", dto.getPlatformContactTelephone());
        map.addAttribute("platformAddress", dto.getPlatformAddress());
        map.addAttribute("platformBank", dto.getPlatformBank());
        map.addAttribute("platformBankCardNo", dto.getPlatformBankCardNo());
        map.addAttribute("platformAlipay", dto.getPlatformAlipay());
        map.addAttribute("signAddress", dto.getSignAddress());
        map.addAttribute("platformName", dto.getPlatformName());
        return "shopContract";
    }

    private OrderContractDto getContractDto(String orderId, String uid, String tempOrderId, String productId) {
        OrderContractDto dto = null;
        if (StringUtil.isNotEmpty(orderId)) {
            dto = orderContractService.getOrderContractInfo(orderId);
        } else {
            UserOrderAgreementRequest request = new UserOrderAgreementRequest();
            request.setOrderId(orderId);
            request.setUid(uid);
            request.setTempOrderId(tempOrderId);
            request.setProductId(productId);
            dto = userOrdersQueryService.agreementV2(request);
        }
        return dto;
    }
}
