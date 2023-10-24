package com.rent.common.dto.export;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: hzsx-rent-parent
 * @description: 用户收货回执单
 * @author: yr
 * @create: 2021-07-16 11:33
 **/
@Data
@Schema(description = "用户收货回执单")
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptConfirmationReceiptDto {
    /**
     * 企业名称
     */
    private String enterpriseName;
    /**
     * 小程序名称
     */
    private String appName;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 规格颜色
     */
    private String specColor;
    /**
     *  渠道名称
     */
    private String channelName;
    /**
     * 下单人姓名
     */
    private String userName;
    /**
     * 下单人号码
     */
    private String userPhone;
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 订单总租金
     */
    private BigDecimal totalRent;
    /**
     * 订单租期
     */
    private Integer duration;
    /**
     * 物流单号
     */
    private String expressNo;

    private String payTime;

    /**
     * 商品id
     */
    private Long snapShotId;
    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品sku_id
     */
    private Long skuId;

    private String recipientPhone;

    private String totalPeriods;


    private Long parentId;


    private String channelWatermark;

    private String channelId;

}
