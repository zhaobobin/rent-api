package com.rent.common.dto.backstage;

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
 * @date 2020-8-4 上午 11:15:41
 * @since 1.0
 */
@Data
@Builder
@Schema(description = "后台查询订单详情")
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserOrderDetailRequest implements Serializable {

    private static final long serialVersionUID = 4331398225483269235L;

    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;
}
