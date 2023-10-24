package com.rent.common.dto.product;


import com.rent.common.enums.product.EnumProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ShopProductAddReqDto {
    @Schema(description = "分类id")
    private Integer categoryId;
    @Schema(description = "一级分类id")
    private Integer oneCategoryId;
    @Schema(description = "产品name")
    private String name;
    @Schema(description = "新旧程度")
    private Integer oldNewDegree;
    @Schema(description = "租赁标签")
    private List<String> labels;
    @Schema(description = "增值服务")
    private List<ProductAdditionalServicesDto> additionals;
    @Schema(description = "增值服务ids")
    private List<Integer> shopAdditionals;
    @Schema(description = "是否支持买断")
    private Integer buyOutSupport;
    @Schema(description = "归还规则")
    private Integer returnRule;
    @Schema(description = "商品sku规格")
    private List<ShopProductSkuAllReqDto> productSkuses;
    @Schema(description = "最小租赁天数")
    private Integer minRentCycle;
    @Schema(description = "最大租赁天数")
    private Integer maxRentCycle;
    @Schema(description = "店铺id")
    private String shopId;
    @Schema(description = "类型")
    private EnumProductType type;
    @Schema(description = "商品详情")
    private String detail;
    @Schema(description = "商品发货地所在省--区域编码")
    private String province;
    @Schema(description = "商品发货地所在市--区域编码")
    private String city;
    @Schema(description = "快递方式")
    private String freightType;
    @Schema(description = "归还快递方式")
    private String returnfreightType;
    @Schema(description = "归还地址id")
    private List<Integer> addIds;


    @Schema(description = "图片地址")
    @Size(max = 5,message = "商品图片不能超过5张")
    @Size(min = 1,message = "至少上传一张商品图片")
    @Valid
    private List<ProductImageAddReqDto> images;
    @Schema(description = "主图")
    private String src;
    @Schema(description = "产品id")
    private Integer id;
    @Schema(description = "商品id")
    private String productId;
    @Schema(description = "历史销量")
    private Integer salesVolume;
    @Schema(description = "最低售价-天")
    private BigDecimal lowestSalePrice;
    @Schema(description = "商品参数")
    private List<ProductParameterDto> parameters;

    @Schema(description = "商品可用优惠券列表")
    private List<ProductCouponDto> productCouponList;

    @Schema(description = "是否已经收藏")
    private Boolean collected ;

    @Schema(description = "商品详情库存规格")
    private List<SpecsDto> specs;


    @Schema(description = "商家客服电话")
    private String serviceTel;



}
