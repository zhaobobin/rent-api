package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "新增优惠券时-指定商品-商品筛选-响应结果")
public class AbleProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品id")
    private String productId;

    @Schema(description = "商品名称")
    private String productName;
}
