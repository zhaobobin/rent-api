package com.rent.common.dto.order.resquest;

import com.rent.common.enums.order.EnumOrderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author udo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单备注请求类")
public class OrderRemarkRequest implements Serializable {

    private static final long serialVersionUID = 6994032781858615239L;

    @NotBlank(message = "订单编号不能为空")
    @Schema(description = "订单编号")
    private String orderId;

    @NotNull(message = "订单类型")
    @Schema(description = "订单类型 01为常规订单 02为拼团订单 03 续租订单 04买断订单")
    private EnumOrderType orderType;

    @NotBlank(message = "备注不能为空")
    @Schema(description = "备注")
    private String remark;

    // @NotBlank(message = "操作人不能为空")
    @Schema(description = "操作人")
    private String userName;

}
