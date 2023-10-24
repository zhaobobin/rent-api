package com.rent.common.dto.export;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: hzsx-rent-parent
 * @description: 预审单
 * @author: yr
 * @create: 2021-07-16 11:33
 **/
@Data
@Schema(description = "预审单")
public class PrequalificationSheetDto {
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 下单人姓名
     */
    private String userName;
    /**
     * 下单人手机号码
     */
    private String userPhone;
    /**
     * 年龄
     */
    private String age;
    /**
     * 在途订单数
     */
    private Integer orderNumbers;
    /**
     * 人脸认证
     */
    private String userFaceCertStatus;
    /**
     * 所在位置
     */
    private String orderLocationAddress;
    /**
     * 收件人姓名
     */
    private String recipientName;
    /**
     * 收件人手机号码
     */
    private String recipientPhone;
    /**
     * 收件地址
     */
    private String recipientAddress;
    /**
     * 收件地址-街道
     */
    private String recipientStreet;
    /**
     * 备注信息
     */
    private String userRemark;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 规格颜色
     */
    private String specColor;
    /**
     * 销售价
     */
    private BigDecimal salePrice;
    /**
     * 总租金
     */
    private BigDecimal totalRent;
    /**
     * 月租金
     */
    private BigDecimal monthlyPay;
    /**
     * 买断价-展示到期买断价：销售价-已付租金
     */
    private String dueBuyOutAmount;
    /**
     * 审核记录
     */
    private List<AuditLogDto> audits;
    /**
     * 平台佣金
     */
    private String toOpeAmount;
    /**
     * 平台其中租金佣金
     */
    private String toOpeRentCommission;
    /**
     * 平台其中买断佣金
     */
    private String toOpeBuyOutCommission;
    /**
     * 代运营佣金
     */
    private BigDecimal toAgentOpeAmount;
    /**
     * 代运营其中租金佣金
     */
    private BigDecimal toAgentOpeRentCommission;
    /**
     * 代运营其中买断佣金
     */
    private String toAgentOpeBuyOutCommission;

    private String shopName;

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

    /**
     * 租期时长
     */
    private Integer rentDuration;

    private String shopId;

    private BigDecimal scale;

    private BigDecimal buyOutScale;


    private String channelWatermark;

    private String channelId;
}
