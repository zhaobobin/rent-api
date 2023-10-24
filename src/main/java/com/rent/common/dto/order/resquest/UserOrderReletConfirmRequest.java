package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-10 下午 1:43:55
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户续租请求类")
public class UserOrderReletConfirmRequest implements Serializable {

    private static final long serialVersionUID = 4156669964724285880L;

    @Schema(description = "原订单Id")
    @NotBlank
    private String originalOrderId;

    @Schema(description = "租期")
    @NotNull
    private Integer duration;

    @Schema(description = "skuId")
    @NotNull
    private Long skuId;

    @Schema(description = "uid")
    @NotBlank(message = "uid不能为空")
    private String uid;

    @Schema(description = "单价")
    @NotNull
    private BigDecimal price;

    @Schema(description = "增值服务id列表")
    private List<String> additionalServicesIds;

}
