package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 *
 * @return
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "取消App支付入参")
public class CancelAliPayTradeAppPayRequest implements Serializable {

    private static final long serialVersionUID = 2121119792046980706L;

    @Schema(description = "流水号")
    @NotBlank
    private String serialNo;

}
