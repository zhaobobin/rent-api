package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "商品审核修改参数")
public class UpdateExamineProductDto implements Serializable {

    private static final long serialVersionUID = -7033763585239002673L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "商品名称")
    private String name;
    @Schema(description = "productId")
    private String productId;

    @Schema(description = "商品类目Id")
    private Integer categoryId;

    @Schema(description = "上下架状态 1已上架 2放在仓库的商品 ")
    private Integer type;

    @Schema(description = "图片链接地址")
    private List<String> images;

    @Schema(description = "是否活动商品：1普通商品 2活动商品")
    private Integer isEventGoods;
}
