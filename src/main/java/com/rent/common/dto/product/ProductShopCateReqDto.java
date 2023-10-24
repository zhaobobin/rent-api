package com.rent.common.dto.product;

import com.rent.common.enums.product.AntChainProductClassEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProductShopCateReqDto {
    /**一级分类id", value = "一级分类id")*/
    private Integer categoryId;
    //@Schema(description = "一级分类名称", value = "一级分类名称")
    private String categoryName;
    // @Schema(description = "支付宝类目code", value = "支付宝类目code")
    private String zfbCode;
    //@Schema(description = "二级分类id", value = "二级分类id")
    private Integer twoCategoryId;
    //@Schema(description = "二级分类名称", value = "二级分类名称")
    private String twoCategoryName;
    //@Schema(description = "三级分类id", value = "二级分类id")
    private Integer threeCategoryId;
    //@Schema(description = "三级分类名称", value = "二级分类名称")
    private String threeCategoryName;
    //@Schema(description = "产品name", value = "产品name")
    private String name;
    //@Schema(description = "店铺id", value = "店铺id")
    private String shopId;
    //@Schema(description = "商品id", value = "商品id")
    private String productId;
    //@Schema(description = "图片地址", value = "图片地址")
    private List<ProductImageAddReqDto> images;

    private AntChainProductClassEnum antChainProductClassEnum;

}
