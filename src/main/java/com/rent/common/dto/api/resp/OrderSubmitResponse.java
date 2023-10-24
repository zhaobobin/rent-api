package com.rent.common.dto.api.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "提交订单响应参数")
public class OrderSubmitResponse implements Serializable {

    private static final long serialVersionUID = -8083146531906804445L;

    @Schema(description = "冻结预授权url")
    private String freezeUrl;

    @Schema(description = "支付流水号")
    private String serialNo;


}
