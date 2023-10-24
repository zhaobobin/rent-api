package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 商品主图表
 *
 * @author youruo
 * @Date 2020-06-16 15:17
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageAddReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "图片地址")
    @NotBlank(message = "商品图片地址不能为空")
    private String src;

    private Integer isMain;

}
