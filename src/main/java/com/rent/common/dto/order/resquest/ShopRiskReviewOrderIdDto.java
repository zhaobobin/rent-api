package com.rent.common.dto.order.resquest;

import com.rent.common.enums.order.EnumOrderCloseType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:boannpx@163.com">Boan</a>
 * @version 1.0
 * @date 2020/1/15
 * @desc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "商家风控关单请求类")
public class ShopRiskReviewOrderIdDto implements Serializable {

    private static final long serialVersionUID = 7613077499905020497L;

    @Schema(description = "店铺id")
    private String shopId;

    @Schema(description = "操作人id")
    private String shopOperatorId;

    @NotBlank
    @Schema(description = "订单id")
    private String orderId;

    @NotNull
    @Schema(description = "关单类型 可用值 06:客户要求关单 07:商家风控关单")
    private EnumOrderCloseType closeType;

    @NotBlank
    @Schema(description = "取消原因")
    private String closeReason;

    @Size(max = 9)
    @Schema(description = "证件照片")
    private List<String> certificateImages;

}
