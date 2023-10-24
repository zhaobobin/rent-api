package com.rent.common.dto.backstage.request;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "分页查询某个优惠券的用户领取情况 请求参数")
public class BackstageQueryUserCouponReq extends Page implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;

    @Schema(description = "优惠券模板ID")
    @NotNull(message = "优惠券模板ID不能为空")
    private Long templateId;

    @Schema(description = "状态：UNUSED：未使用 USED：已使用")
    private String status;

    @Schema(description = "订单ID")
    private String orderId;


}
