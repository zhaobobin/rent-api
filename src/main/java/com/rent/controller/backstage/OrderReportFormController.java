package com.rent.controller.backstage;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.OrderReportFormDto;
import com.rent.common.dto.backstage.OrderReportFormRequest;
import com.rent.common.dto.product.ShopDto;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.Product;
import com.rent.service.order.OrderReportService;
import com.rent.service.product.ProductService;
import com.rent.service.product.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author zhaowenchao
 */
@Slf4j
@RestController
@Tag(name = "运营订单模块")
@RequestMapping("/zyj-backstage-web/hzsx/ope/reportForm")
@RequiredArgsConstructor
public class OrderReportFormController {

    private final ShopService shopService;
    private final ProductService productService;
    private final OrderReportService orderReportService;

    @Operation(summary = "查询各种状态的订单数量")
    @GetMapping("/orderStatusCount")
    public CommonResponse<Map<String, Integer>> orderStatusCount(@RequestParam(value = "isMyAuditOrder", required = false, defaultValue = "false") Boolean isMyAuditOrder) {
        return CommonResponse.<Map<String, Integer>>builder().data(orderReportService.businessOrderStatistics(isMyAuditOrder)).build();
    }

    @Operation(summary = "查询订单统计")
    @PostMapping("/query")
    public CommonResponse<OrderReportFormDto> query(@RequestBody OrderReportFormRequest request) {

        if (StringUtils.isNotEmpty(request.getShopName())) {
            List<ShopDto> shopList = shopService.listAllShop(request.getShopName());
            if (CollectionUtils.isEmpty(shopList)) {
                throw new HzsxBizException("-1", "未查询到店铺信息");
            }
            if (shopList.size() > 1) {
                throw new HzsxBizException("-1", "查询到多个店铺信息");
            }
            request.setShopId(shopList.get(0).getShopId());
        }
        String productName = null;
        if (StringUtils.isNotEmpty(request.getProductId())) {
            String productId = request.getProductId();
            Product product = productService.getByProductId(productId);
            if (product == null) {
                throw new HzsxBizException("-1", "未查询到商品信息");
            }
            productName = product.getName();
            if (productName == null) {
                throw new HzsxBizException("-1", "未查询到商品信息");
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(request.getBegin());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        request.setBegin(calendar.getTime());

        calendar.setTime(request.getEnd());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        request.setEnd(calendar.getTime());

        OrderReportFormDto dto = orderReportService.orderReportForm(request);
        dto.setBegin(request.getBegin());
        dto.setEnd(request.getEnd());
        dto.setPosition(request.getPosition());
        dto.setAction(request.getAction());
        dto.setChannel(request.getChannel());
        dto.setShopName(request.getShopName());
        dto.setProductName(productName);
        return CommonResponse.<OrderReportFormDto>builder().data(dto).build();
    }

}
