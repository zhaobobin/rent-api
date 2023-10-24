package com.rent.common.dto.backstage;

import com.rent.common.enums.order.DeductionMethodType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-20 下午 2:33:51
 * @since 1.0
 */
@Data
@Schema(description = "手动发起代扣")
public class StageOrderWithholdRequest implements Serializable {

    private static final long serialVersionUID = -223861630287131957L;

    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;

    /**
     * 当前期数
     */
    @Schema(description = "扣款期数", required = true)
    private Integer currentPeriods;


    //扣款方式
    @Schema(description = "扣款方式")
    @NotNull
    private DeductionMethodType deductionMethodType;


}
