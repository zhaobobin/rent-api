package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author udo
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "现金转账类")
public class AliPayTransferRespModel implements Serializable {

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String outBizNo;

    /**
     * 订单号
     */
    @Schema(description = "支付宝流水号")
    private String payFundOrderId;

    /**
     * 响应结果
     */
    @Schema(description = "响应结果")
    private String status;


}
