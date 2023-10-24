package com.rent.common.dto.order.response;

import com.rent.common.dto.order.UserOrdersDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "提交订单响应参数")
public class OrderReletSubmitResponse implements Serializable {

    private static final long serialVersionUID = -5361737934392404564L;
    @Schema(description = "订单信息")
	private UserOrdersDto userOrdersDto;
    @Schema(description = "冻结预授权url")
    private String freezeUrl;
    @Schema(description = "支付流水号")
    private String serialNo;

 }
