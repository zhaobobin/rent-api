package com.rent.common.dto.order.resquest;

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
 * @date 2020-8-10 上午 11:54:30
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "续租订单展示页请求类")
public class UserOrderReletPageRequest implements Serializable {

    private static final long serialVersionUID = 6901591563649925242L;

    @NotBlank
    @Schema(description = "订单编号")
    private String orderId;
}
