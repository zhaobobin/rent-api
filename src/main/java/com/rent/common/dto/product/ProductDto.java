package com.rent.common.dto.product;

import com.rent.common.enums.product.EnumProductAuditState;
import com.rent.common.enums.product.EnumProductStatus;
import com.rent.common.enums.product.EnumProductType;
import com.rent.model.product.Shop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 商品表
 *
 * @author youruo
 * @Date 2020-06-16 15:06
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品表")
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Integer id;

    /**
     * CreateTime
     * 
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     * 
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 商品名称
     * 
     */
    @Schema(description = "商品名称")
    private String name;

    /**
     * 商品id
     * 
     */
    @Schema(description = "商品id")
    private String productId;

    /**
     * 平台分类id，为最后一级分类id
     * 
     */
    @Schema(description = "平台分类id，为最后一级分类id")
    private Integer categoryId;

    /**
     * 所属店铺id
     * 
     */
    @Schema(description = "所属店铺id")
    private String shopId;

    /**
     * 商品详情
     * 
     */
    @Schema(description = "商品详情")
    private String detail;

    /**
     * 0回收站中的商品 1已上架售卖的商品 2放在仓库的商品
     * 
     */
    @Schema(description = "商家上下架状态 0回收站中的商品 1已上架售卖的商品 2放在仓库的商品")
    private EnumProductType type;

    /**
     * 商品状态 0为失效 1为有效 
     * 
     */
    @Schema(description = "商品状态 0为失效 1为有效 ")
    private EnumProductStatus status;

    /**
     * 0失效 1有效
     * 
     */
    @Schema(description = "0失效 1有效")
    private Integer shopStatus;

    /**
     * 商品发货地所在省
     * 
     */
    @Schema(description = "商品发货地所在省")
    private String province;

    /**
     * 商品发货地所在市
     * 
     */
    @Schema(description = "商品发货地所在市")
    private String city;

    /**
     * 租用规则id
     * 
     */
    @Schema(description = "租用规则id")
    private Integer rentRuleId;

    /**
     * 赔偿规则id
     * 
     */
    @Schema(description = "赔偿规则id")
    private Integer compensateRuleId;

    /**
     * 起租周期
     * 
     */
    @Schema(description = "起租周期")
    private Integer minRentCycle;

    /**
     * 最大租用周期
     * 
     */
    @Schema(description = "最大租用周期")
    private Integer maxRentCycle;

    /**
     * 最少提前多少天下单
     * 
     */
    @Schema(description = "最少提前多少天下单")
    private Integer minAdvancedDays;

    /**
     * 最多提前多少天下单
     * 
     */
    @Schema(description = "最多提前多少天下单")
    private Integer maxAdvancedDays;

    /**
     * 商品审核状态 0为正在审核中 1为审核不通过 2为审核通过
     * 
     */
    @Schema(description = "商品审核状态 0为正在审核中 1为审核不通过 2为审核通过")
    private EnumProductAuditState auditState;

    /**
     * 审核不通过原因
     * 
     */
    @Schema(description = "审核不通过原因")
    private String auditRefuseReason;

    /**
     * 历史销量
     * 
     */
    @Schema(description = "历史销量")
    private Integer salesVolume;

    /**
     * 月销量
     * 
     */
    @Schema(description = "月销量")
    private Integer monthlySalesVolume;

    /**
     * 1为全新 2为99新 3为95新 4为9成新 5为8成新 6为7成新
     * 
     */
    @Schema(description = "1为全新 2为99新 3为95新 4为9成新 5为8成新 6为7成新")
    private Integer oldNewDegree;

    /**
     * 商品最低价
     * 
     */
    @Schema(description = "商品最低价")
    private BigDecimal sale;


    @Schema(description = "商店")
    private Shop shop;
    @Schema(description = "商店名称")
    private String shopName;
    @Schema(description = "商品图片")
    private String image;
    @Schema(description = "商品价格")
    private Map<String, String> OldNewDegreeAndPrice;
    @Schema(description = "服务商标")
    private String serviceMark;
    @Schema(description = "商品标签")
    private String ProductMark;
    @Schema(description = "类目名称链接")
    private String categoryNameLink;
    @Schema(description = "类目商品字符串")
    private String  opeCategoryStr;
    /**
     * 是否可以买断 1:可以买断。0:不可以买断
     */
    @Schema(description = "是否可以买断 1:可以提前买断。2:支持到期买断 0:不可以买断")
    private Integer buyOutSupport;
    @Schema(description = "归还规则 1:可以提前归还 2:支持到期归还")
    private Integer returnRule;

    @Schema(description = "快递方式-包邮:FREE-到付:PAY-自提:SELF")
    private String freightType;
    @Schema(description = "归还快递方式")
    private String returnfreightType;
    @Schema(description = "主图")
    private String src;

    @Schema(description = "是否线上商品 1:线上商品。2:线下商品")
    private Integer isOnLine;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
