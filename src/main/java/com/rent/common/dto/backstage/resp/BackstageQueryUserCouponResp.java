package com.rent.common.dto.backstage.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "分页查询某个优惠券的用户领取情况 响应")
public class BackstageQueryUserCouponResp implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "领取用户的手机号码")
    private String phone;

    @Schema(description = "用户领取时间")
    private Date receiveTime;

    @Schema(description = "领取方式 REQUEST：用户主动领取 ASSIGN：系统派发")
    private String receiveType;

    @Schema(description = "订单ID")
    private String orderId;

    @Schema(description = "用户使用时间")
    private Date useTime;

    @Schema(description = "状态：UNUSED：未使用 USED：已使用")
    private String status;
}
