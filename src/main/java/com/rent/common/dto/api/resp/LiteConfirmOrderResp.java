package com.rent.common.dto.api.resp;

import com.rent.common.dto.marketing.OrderCouponDto;
import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.product.ProductAdditionalServicesDto;
import com.rent.common.dto.user.UserAddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "确认订单响应结果")
public class LiteConfirmOrderResp implements Serializable {

    private static final long serialVersionUID = -7168470208066916746L;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "地址信息")
    private UserAddressDto address;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "商品主图")
    private String productImage;

    @Schema(description = "规格颜色")
    private List<String> specName;

    @Schema(description = "可用优惠券列表")
    private List<OrderCouponDto> orderCouponList;

    @Schema(description = "可选增值服务列表")
    private List<ProductAdditionalServicesDto> additionalServices;

    @Schema(description = "订单价格信息")
    private OrderPricesDto orderPrices;

    @Schema(description = "发货物流服务方式-快递方式-商家承担:FREE-用户支付:PAY-自提:SELF")
    private String productFreightType;

    @Schema(description = "是否实名认证 UN_CERT:未认证 UN_UPLOAD:填写身份证号码未上传照片 FINISH:已认证")
    private String hasCertification;

    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "是否支持买断 true可以买断 false不能")
    private Boolean isBuyOutSupported;

    @Schema(description = "销售价")
    private BigDecimal salePrice;

    @Schema(description = "到期买断价")
    private BigDecimal expireBuyOutPrice;

    @Schema(description = "是否支持云信 true 支持")
    private Boolean yx;

    @Schema(description = "是否是刷单商品 1：需实名商品 2:只填写实名商品 3:无需实名商品")
    private Integer isOnLine;

}
