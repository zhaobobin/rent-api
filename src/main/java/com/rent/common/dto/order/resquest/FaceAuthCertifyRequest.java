package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-25 下午 4:06:19
 * @since 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "人脸初始化请求类")
public class FaceAuthCertifyRequest implements Serializable {

    private static final long serialVersionUID = 213611043387860315L;

    @Schema(description = "订单编号")
    @NotNull
    private String orderId;
}
