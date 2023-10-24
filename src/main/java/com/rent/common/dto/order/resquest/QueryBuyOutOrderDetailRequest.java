package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "买断订单详情查询条件")
public class QueryBuyOutOrderDetailRequest implements Serializable {

    private static final long serialVersionUID = 8627422352713856241L;

    @Schema(description = "原订单编号")
    private String orderId;

    @Schema(description = "买断订单编号")
    private String buyOutOrderId;

}
