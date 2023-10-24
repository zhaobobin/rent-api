package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 手动代扣返回
 * @Date: 2021/10/19
 */
@Data
@Schema(description = "手动发起代扣")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StageOrderWithholdResponse implements Serializable {

    private static final long serialVersionUID = 9129675550421135874L;

    @Schema(description = "是否扣款成功")
    private Boolean orderId;
    @Schema(description = "返回内容")
    private String respMsg;
}
