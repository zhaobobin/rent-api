package com.rent.common.dto.components.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author udo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "提交订单页协议")
public class UserOrderAgreementRequest implements Serializable {

    private static final long serialVersionUID = -7754982900771474655L;


    @Schema(description = "订单ID")
    private String orderId;

    @Schema(description = "原订单号")
    private String tempOrderId;

    @Schema(description = "店铺ID")
    private String shopId;

    @Schema(description = "用户ID")
    private String uid;

    @Schema(description = "产品id")
    private String productId;


}
