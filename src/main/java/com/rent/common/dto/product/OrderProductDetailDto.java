package com.rent.common.dto.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
public class OrderProductDetailDto {

    /** 平台服务表 */
    private List<PlatformServiceMarksDto> productServiceMarksList;

    /** 市场价 */
    private BigDecimal marketPrice;

    /** 销售价 */
    private BigDecimal salePrice;

    /** skuId */
    private Long skuId;

    /** 是否支持买断 */
    private Integer buyOutSupport;
    /** 归还规则**/
    private Integer returnRule;

    /** 商品名称 */
    private String productName;

    /** 商品名称 */
    private String productId;

    /** 商品主键 */
    private Integer id;

    /** 商品主图片 */
    private String mainImageUrl;

    private String skuTitle;
    /** 运费方式**/
    private String freightStr;

    private String freightType;
    private String returnfreightType;

    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 商品周期价格表
     */
    private List<ProductCyclePricesDto> cyclePrices;

    private Long parentId;

    private Integer isOnLine;


}
