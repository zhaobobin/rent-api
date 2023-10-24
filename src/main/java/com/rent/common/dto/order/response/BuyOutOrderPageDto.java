package com.rent.common.dto.order.response;

import com.rent.common.dto.marketing.OrderCouponDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author udo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "买断查询响应参数")
public class BuyOutOrderPageDto implements Serializable {

    private static final long serialVersionUID = -7168470208066916746L;

    /**
     * 订单号
     */
    @Schema(description = "订单编号")
    private String orderId;
    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 商品规格
     */
    @Schema(description = "商品规格")
    private String skuTitle;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String images;

    /**
     * 总租金
     */
    @Schema(description = "总租金")
    private BigDecimal totalRent;

    /**
     * 到期买断尾款
     */
    @Schema(description = "到期买断尾款")
    private BigDecimal dueBuyOutAmount;


    /**
     * 买断尾款（减去优惠券金额之前的金额）
     */
    @Schema(description = "断尾款（减去优惠券金额之前的金额）")
    private BigDecimal endFund;

    /**
     * 优惠券金额
     */
    @Schema(description = "优惠券金额")
    private BigDecimal couponAmount;

    /**
     * 优惠券编号
     */
    @Schema(description = "优惠券编号")
    private String couponCode;

    /**
     * 需要支付的金额 endFund - couponAmount;
     */
    @Schema(description = "需要支付的金额")
    private BigDecimal needPay;

    /**
     * 已付租金综合
     */
    @Schema(description = "已付租金综合")
    private BigDecimal paid;

    /**
     * 实付押金
     */
    @Schema(description = "实付押金")
    private BigDecimal deposit;

    @Schema(description = "是否可以买断 1:可以提前买断。2:支持到期买断 0:不可以买断")
    private Integer buyOutSupport;



    /**
     * 可用优惠券列表
     */
    @Schema(description = "可用优惠券列表")
    private List<OrderCouponDto> orderCouponDtos;

}
