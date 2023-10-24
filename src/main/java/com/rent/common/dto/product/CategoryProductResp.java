package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author youruo
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "小程序查看类目下商品信息")
public class CategoryProductResp implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "所属类目ID")
    private Integer categoryId;

    @Schema(description = "商品主图")
    private String image;

    @Schema(description = "展示价格")
    private BigDecimal price;

    @Schema(description = "新旧程度")
    private String oldNewDegree;

    @Schema(description = "商品ID")
    private String itemId;

    @Schema(description = "类目ID")
    private Integer salesVolume;

    @Schema(description = "商品状态")
    private Integer status;

    @Schema(description = "商品标签")
    private List<String> labels;
}
