package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-17 下午 2:02:03
 * @since 1.0
 */
@Data
@Schema(description = "用户订单列表响应参数")
public class UserOrderListDto implements Serializable {

    private static final long serialVersionUID = -2307808057227743528L;

    @Schema(description = "订单id")
    private String orderId;

    @Schema(description = "订单创建时间")
    private Date createTime;

    @Schema(description = "skuId")
    private String skuId;

    @Schema(description = "商品id")
    private String productId;

    @Schema(description = "刷脸认证状态 01:未认证 02:认证中 03:已认证")
    private String faceAuthStatus;

    @Schema(description = "总租金")
    private BigDecimal totalRent;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "商家服务电话")
    private String serviceTel;

    @Schema(description = "商品图片列表")
    private String mainImageUrl;

    @Schema(description = "商品标题")
    private String skuTitle;

    @Schema(description = "用户手机号码")
    private String telephone;

    @Schema(description = "商铺名字")
    private String shopName;

    @Schema(description = "用户身份证ocr认证状态")
    private Integer userIdCardPhotoCertStatus;

    @Schema(description = "用户人脸认证状态")
    private Integer userFaceCertStatus;

    @Schema(description = "订单状态")
    private String status;

    @Schema(description = "订单类型 01:为常规订单 02:为拼团订单 03:续租订单  04:买断订单")
    private String orderType;

    @Schema(description = "取消原因")
    private String cancelReason;

    @Schema(description = "是否支持买断 1:支持 2:不支持")
    private Integer buyOutSupport;


    @Schema(description = "是否显示归还按钮 0:不显示 1:显示")
    private Integer isShowReturnButton;

    @Schema(description = "是否显示买断按钮 0:不显示 1:显示")
    private Integer isShowBuyOutButton;

    @Schema(description = "是否显示续租按钮 0:不显示 1:显示")
    private Boolean showReletButton;

    @Schema(description = "退租物流单号")
    private String unrentExpressNo;

    @Schema(description = "退租物流id")
    private Long unrentExpressId;

    @Schema(description = "结算待支付金额")
    private BigDecimal settlementRent;

    @Schema(description = "关单类型")
    private String closeType;

    @Schema(description = "逾期状态 是否违约 01正常 02结算单逾期 03提前归还 04账单逾期 05:逾期未归还")
    private String violationStatus;
}
