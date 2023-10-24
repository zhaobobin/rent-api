package com.rent.common.dto.order.response;

import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.product.OrderProductDetailDto;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.common.dto.product.ShopProductAddReqDto;
import com.rent.common.dto.product.ShopProductSkuAllReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "确认续租订单响应参数")
public class OrderReletConfirmResponse implements Serializable {

    private static final long serialVersionUID = -177336874341187706L;

    @Schema(description = "原订单id")
    private String originalOrderId;
    @Schema(description = "是否支持买断")
    private Boolean isBuyOutSupported;
    @Schema(description = "商品信息")
    private ShopProductAddReqDto product;
    @Schema(description = "sku信息")
    private ShopProductSkuAllReqDto skuDto;
    @Schema(description = "租期开始时间")
    private String start;
    @Schema(description = "租期结束时间")
    private String end;
    @Schema(description = "租期")
    private Integer duration;
    @Schema(description = "金额信息")
    private OrderPricesDto orderPricesDto;
    @Schema(description = "用户姓名")
    private String userName;
    @Schema(description = "实名状态")
    private Boolean realNameStatus;
    @Schema(description = "订单id")
    private String orderId;
    @Schema(description = "到期买断价格")
    private BigDecimal expireBuyOutPrice;
    @Schema(description = "0元买断开始期次")
    private int freeBuyOutPeriod;
    @Schema(description = "用户已选增值服务列表")
    private List<ShopAdditionalServicesDto> additionalServicesDtoList;
    @Schema(description = "商品信息-订单商品快照")
    private OrderProductDetailDto detailDto;
}
