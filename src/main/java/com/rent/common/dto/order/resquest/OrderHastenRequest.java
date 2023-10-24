package com.rent.common.dto.order.resquest;

import com.rent.common.enums.order.EnumOrderResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author udo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "催收记录请求类")
public class OrderHastenRequest implements Serializable {

    private static final long serialVersionUID = 6994032781858615239L;

    @NotBlank(message = "订单编号不能为空")
    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "结果")
    private EnumOrderResult result;

    @NotBlank(message = "小记不能为空")
    @Schema(description = "小记")
    private String notes;

    // @NotBlank(message = "操作人不能为空")
    @Schema(description = "操作人")
    private String userName;

}
