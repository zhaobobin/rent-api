package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "订单归还提交请求类")
public class OrderGiveBackRequest implements Serializable {

    private static final long serialVersionUID = -6542428303469615354L;

    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "地址id")
    @NotNull
    private Long addressId;

    @Schema(description = "物流id")
    @NotNull
    private Long expressId;

    @Schema(description = "物流编号")
    @NotBlank
    private String expressNo;

    @Schema(description = "")
    private String giveBackUrls;
}
