package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * @Description: 转单实体配置
 * @Author: yr
 * @Date: 2021/12/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferOrderRequest implements Serializable {

    private static final long serialVersionUID = -223861630287131957L;

    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;

    @Schema(description = "备注")
    @NotBlank
    private String remark;

    @Schema(description = "接手商家ID")
    @NotBlank
    private String transferShopId;

    @Schema(description = "操作人")
    private String operator;

    @Schema(description = "操作人Id")
    private String operatorId;

}
