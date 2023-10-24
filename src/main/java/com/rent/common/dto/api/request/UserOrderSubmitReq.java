package com.rent.common.dto.api.request;

import com.rent.common.dto.order.OrderLocationAddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "订单提交请求类")
public class UserOrderSubmitReq implements Serializable {
    private static final long serialVersionUID = -1723645187448674442L;

    @Schema(description = "订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderId;

    @Schema(description = "用户ID")
    @NotBlank(message = "用户ID不能为空")
    private String uid;

    @Schema(description = "skuId")
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @Schema(description = "收货地址ID")
    @NotNull(message = "收货地址不能为空")
    private Long addressId;

    @Schema(description = "租期")
    @NotNull(message = "租期不能为空")
    private Integer duration;

    @Schema(description = "增值服务ID列表")
    private List<String> additionalServicesIds;

    @Schema(description = "用户优惠券ID")
    private String couponId;

    @Schema(description = "买家留言")
    private String remark;

    @Schema(description = "反欺诈级别，支付宝获取")
    private String nsfLevel;

    @Schema(description = "反欺诈级别，支付宝获取")
    private String antiCheatingLevel;

    @Schema(description = "下单时用户位置，支付宝获取")
    private OrderLocationAddressDto orderLocationAddress;

}


