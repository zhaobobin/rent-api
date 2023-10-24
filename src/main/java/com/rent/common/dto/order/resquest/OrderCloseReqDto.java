package com.rent.common.dto.order.resquest;

import com.rent.common.enums.order.EnumOrderCloseType;
import com.rent.common.enums.order.EnumOrderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-17 13:40
 * @since 1.0
 */
@Schema(description = "用户关单请求类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCloseReqDto implements Serializable {

    private static final long serialVersionUID = 2952473290617061842L;

    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;
    @Schema(description = "订单类型")
    private EnumOrderType orderType;
    @Schema(description = "01:未支付用户主动申请,02:支付失败,03:超时支付,04:已支付用户主动申请,05:风控拒绝,06:商家关闭订单,07:商家风控关闭订单,08:商家超时发货")
    private EnumOrderCloseType closeType;
    @Schema(description = "取消原因")
    // @NotBlank
    private String cancelReason;


}
