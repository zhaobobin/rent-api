package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2021-6-16 上午 11:07:35
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户订单押金周期扣款响应参数")
public class UserOrderPaySignDto implements Serializable {

    private static final long serialVersionUID = -9086745698817828762L;

    @Schema(description = "是否展示支付租金icon")
    private Boolean showDepositIcon;

    @Schema(description = "是否展示签约周期扣款icon")
    private Boolean showCyclePayIcon;

    @Schema(description = "是否展示绑定银行卡icon")
    private Boolean showBankCardIcon;


}
