package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "取消预授权冻结请求参数")
public class CancelAliPayFreezeRequest implements Serializable {

    private static final long serialVersionUID = -4217917839749978546L;

    @NotBlank(message = "uid不能为空")
    @Schema(description = "请求流水号", required = true)
    private String serialNo;


}
