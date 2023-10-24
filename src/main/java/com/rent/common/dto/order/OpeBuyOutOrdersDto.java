package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yr
 * @version 1.0
 * @date 2020/4/10
 * @desc 运营买断返回实体
 */
@Data
@Builder
@Schema(description = "运营买断列表")
@AllArgsConstructor
@NoArgsConstructor
public class OpeBuyOutOrdersDto implements Serializable {

    private static final long serialVersionUID = -8118303767457717398L;

    //    订单号
    @Schema(description = "买断订单号")
    private String buyOutOrderId;

    //    商品
    @Schema(description = "商品名称")
    private String productName;

    //    原订单号
    @Schema(description = "原订单号")
    private String orderId;

    //    买断时间
    @Schema(description = "买断时间")
    private Date createTime;

    //  用户姓名
    @Schema(description = "用户姓名")
    private String userName;

    //  下单用户
    @Schema(description = "下单用户手机号")
    private String telephone;

    //  状态
    @Schema(description = "状态")
    private String state;

    //  用户ID
    @Schema(description = "用户ID")
    private String uid;


    /**
     * 用户身份证ocr认证状态
     */
    @Schema(description = "用户身份证ocr认证状态")
    private Boolean userIdCardPhotoCertStatus;

    /**
     * 用户人脸认证状态
     */
    @Schema(description = "用户人脸认证状态")
    private Boolean userFaceCertStatus;

    /**
     * 来源渠道ID
     */
    @Schema(description = "来源渠道ID")
    private String channelId;

    /**
     * 来源渠道名字
     */
    @Schema(description = "来源渠道名字")
    private String channelName;

}
