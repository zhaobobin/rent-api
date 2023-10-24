package com.rent.common.dto.components.request;

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
 * @date 2020-7-2 下午 3:19:25
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "物流信息查询请求类")
public class ExpressQueryRequest implements Serializable {

    private static final long serialVersionUID = 7611332519260088214L;

    @Schema(description = "物流公司")
    @NotBlank
    private String com;

    @Schema(description = "快递单号")
    @NotBlank
    private String no;

    @Schema(description = "收货人手机号后四位")
    // @NotBlank
    private String receiverPhone;


}
