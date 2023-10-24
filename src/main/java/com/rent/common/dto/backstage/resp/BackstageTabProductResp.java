package com.rent.common.dto.backstage.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "运营后台查看tab下商品响应结果")
public class BackstageTabProductResp {

    private static final long serialVersionUID = 2346915145375375346L;

    @Schema(description = "tabProduct id值")
    private Long id;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "排序值")
    private Integer indexSort;

    @Schema(description = "商品编号")
    private String itemId;

    @Schema(description = "店铺名称")
    private String shopName;
}
