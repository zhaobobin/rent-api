package com.rent.common.dto.order.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-2 上午 9:27:54
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "买断支付响应类")
public class BuyOutOrderPayResponse implements Serializable {

    private static final long serialVersionUID = 2590868141626945055L;

    @Schema(description = "支付地址")
    private String payUrl;

    @Schema(description = "流水号")
    private String serialNo;

}
