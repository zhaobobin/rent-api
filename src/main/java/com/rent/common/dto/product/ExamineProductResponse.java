package com.rent.common.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rent.common.enums.product.EnumProductAuditState;
import com.rent.common.enums.product.EnumProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: hzsx-rent-parent
 * @description: 商品审核详情返回实体
 * @author: yr
 * @create: 2020-08-04 15:13
 **/
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "商品审核返回实体")
public class ExamineProductResponse implements Serializable {
    private static final long serialVersionUID = 4191915000995212741L;
    @Schema(description = "商品图片")
    private List<ProductImageDto> images;
    @Schema(description = "商家信息")
    private ShopDto shop;
    @Schema(description = "规格信息")
    private List<SpecsResponse> specs;
    @Schema(description = "商品库存信息")
    private List<ProductSkusDto> skus;
    @Schema(description = "店铺增值服务表")
    private List<ProductAdditionalServicesDto> additionalServices;
    @Schema(description = "商品标签")
    private List<String> labels;
    @Schema(description = "商品参数")
    private List<ProductParameterDto> parameters;
    @Schema(description = "商品类目")
    private OpeCategoryNameAndIdDto categoryStr;
    @Schema(description = "商品归还地址")
    private List<ShopGiveBackAddressesDto> shopGiveBackAddressesList;
    @Schema(description = "id")
    private Integer id;
    private BigDecimal sales;
    @Schema(description = "商品详细信息")
    private String detail;
    @Schema(description = "商品名称")
    private String name;
    @Schema(description = "最小租赁周期")
    private  Integer minRentCycle;
    @Schema(description = "最大租赁周期")
    private  Integer maxRentCycle;
    @Schema(description = "商品itemId")
    private String itemId;
    @Schema(description = "商品销量")
    private Integer salesVolume;
    @Schema(description = "商品月销量")
    private Integer monthlySalesVolume;
    @Schema(description = "新旧程度")
    private Integer oldNewDegree;
    @Schema(description = "上下架")
    private EnumProductType type;
    @Schema(description = "创建时间")
    private Date createTime;
    @Schema(description = "商品审核状态 0为正在审核中 1为审核不通过 2为审核通过")
    private EnumProductAuditState auditState;
    @Schema(description = "商品审核理由")
    private String auditRefuseReason;
    @Schema(description = "是否可以买断 1:可以提前买断。2:支持到期买断 0:不可以买断")
    private Integer buyOutSupport;
    @Schema(description = "归还规则 1:可以提前归还 2:支持到期归还")
    private Integer returnRule;
    @Schema(description = "快递方式")
    private String freightType;
    @Schema(description = "归还快递方式")
    private String returnfreightType;
    @Schema(description = "发货地")
    private String city;
    @Schema(description = "是否线上商品 1:线上商品。2:线下商品")
    private Integer isOnLine;

    @Schema(description = "商品定价信息")
    private List<PricingInformationsDto> pricingInformations;
}
