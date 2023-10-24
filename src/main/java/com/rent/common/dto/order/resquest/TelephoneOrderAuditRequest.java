package com.rent.common.dto.order.resquest;

import com.rent.common.enums.order.EnumOrderAuditRefuseType;
import com.rent.common.enums.order.EnumOrderAuditStatus;
import com.rent.common.enums.order.EnumOrderOperatorRole;
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
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "电审审核入参")
public class TelephoneOrderAuditRequest implements Serializable {

    private static final long serialVersionUID = 7341882444625179538L;

    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "审核结果 01:通过 02:拒绝")
    @NotNull
    private EnumOrderAuditStatus orderAuditStatus;

    @Schema(description = "拒绝类型")
    private EnumOrderAuditRefuseType refuseType;

    @Schema(description = "操作人类型",hidden = true)
    private EnumOrderOperatorRole operatorRole;

}
