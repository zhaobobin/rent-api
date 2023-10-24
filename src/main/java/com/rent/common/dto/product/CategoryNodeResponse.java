package com.rent.common.dto.product;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "商品类目返回实体")
public class CategoryNodeResponse implements Serializable {

    private static final long serialVersionUID = 4367092259188628563L;

    /**
     * 一级分类主键
     */
    @Schema(description = "一级分类主键")
    Long value;

    /**
     * 父类渠道id
     */
    @Schema(description = "父类渠道id")
    Long parentId;

    @Schema(description = "标签名称")
    String label;

    /**
     * 二级分类节点
     */
    @Schema(description = "二级分类节点")
    List<CategoryNodeResponse> children = new ArrayList<>();

}
