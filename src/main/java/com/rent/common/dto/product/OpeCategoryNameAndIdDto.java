package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpeCategoryNameAndIdDto implements Serializable {

    @Schema(description = "商品类目名字")
    private List<String> name;
    @Schema(description = "商品类目id")
    private List<Integer> categoryId;


}
