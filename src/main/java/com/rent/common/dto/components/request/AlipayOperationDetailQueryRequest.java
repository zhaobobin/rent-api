package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "资金授权操作查询")
public class AlipayOperationDetailQueryRequest implements Serializable {
    private static final long serialVersionUID = -3060436619710273184L;

    @Schema(description = "订单编号")
    private String orderId;
    @Schema(description = "授权编号")
    private String authNo;
    @Schema(description = "商户订单号")
    private String outOrderNo;
    @Schema(description = "请求交易流水")
    private String outRequestNo;
    @Schema(description = "channelId")
    private String channelId;
}
