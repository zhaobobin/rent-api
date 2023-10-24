package com.rent.common.dto.components.request;

import com.rent.common.enums.components.EnumTradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "预授权解冻请求类")
public class AlipayUnfreezeRequest implements Serializable {

    private static final long serialVersionUID = 5994597332849534618L;
    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;
    @Schema(description = "资金授权号")
    @NotBlank
    private String authNo;
    @Schema(description = "请求流水号")
    @NotBlank
    private String outRequestNo;
    @Schema(description = "金额")
    private BigDecimal amount;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "回调地址")
    private String notify_url;
    @Schema(description = "交易类型")
    @NotNull
    private EnumTradeType tradeType;
    @Schema(description = "uid")
    private String uid;
    @Schema(description = "channelId")
    private String channelId;
}
