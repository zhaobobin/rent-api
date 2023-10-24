
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.product.EnumProductAuditState;
import com.rent.common.enums.product.EnumProductStatus;
import com.rent.common.enums.product.EnumProductType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 *
 * @author youruo
 * @Date 2020-06-16 15:06
 */
@Data
@Accessors(chain = true)
@TableName("ct_product")
public class Product {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 商品名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 商品id
     * 
     */
    @TableField(value="product_id")
    private String productId;
    /**
     * 平台分类id，为最后一级分类id
     * 
     */
    @TableField(value="category_id")
    private Integer categoryId;
    /**
     * 所属店铺id
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 商品详情
     * 
     */
    @TableField(value="detail")
    private String detail;
    /**
     * 0回收站中的商品 1已上架售卖的商品 2放在仓库的商品
     * 
     */
    @TableField(value="type")
    private EnumProductType type;
    /**
     * 商品状态 0为失效 1为有效
     * 
     */
    @TableField(value="status")
    private EnumProductStatus status;
    /**
     * 0失效 1有效
     * 
     */
    @TableField(value="shop_status")
    private Integer shopStatus;
    /**
     * 商品发货地所在省
     * 
     */
    @TableField(value="province")
    private String province;
    /**
     * 商品发货地所在市
     * 
     */
    @TableField(value="city")
    private String city;

    /**
     * 起租周期
     * 
     */
    @TableField(value="min_rent_cycle")
    private Integer minRentCycle;
    /**
     * 最大租用周期
     * 
     */
    @TableField(value="max_rent_cycle")
    private Integer maxRentCycle;
    /**
     * 最少提前多少天下单
     * 
     */
    @TableField(value="min_advanced_days")
    private Integer minAdvancedDays;
    /**
     * 最多提前多少天下单
     * 
     */
    @TableField(value="max_advanced_days")
    private Integer maxAdvancedDays;

    @TableField(value="audit_state")
    private EnumProductAuditState auditState;
    /**
     * 审核不通过原因
     * 
     */
    @TableField(value="audit_refuse_reason")
    private String auditRefuseReason;
    /**
     * 历史销量
     * 
     */
    @TableField(value="sales_volume")
    private Integer salesVolume;

    /**
     * 月销量
     * 
     */
    @TableField(value="monthly_sales_volume")
    private Integer monthlySalesVolume;
    /**
     * 1为全新 2为99新 3为95新 4为9成新 5为8成新 6为7成新
     * 
     */
    @TableField(value="old_new_degree")
    private Integer oldNewDegree;

    /**
     * 商品最低价
     * 
     */
    @TableField(value="sale")
    private BigDecimal sale;


    /**
     * 是否可以买断 1:可以买断。0:不可以买断
     *
     */
    @TableField(value="buy_out_support")
    private Integer buyOutSupport;

    /**
     * 归还规则 1:支持提前归还  2:支持到期归还
     *
     */
    @TableField(value="return_rule")
    private Integer returnRule;


    /**
     * 发货物流服务方式-快递方式-商家承担:FREE-用户支付:PAY-自提:SELF
     *
     */
    @TableField(value="freight_type")
    private String freightType;

    @TableField(value="sort_score")
    private Integer sortScore;

    @TableField(value="hidden")
    private Boolean hidden;

    /**
     * 归还物流服务方式-快递方式-FREE-用户支付:PAY-商家承担
     *
     */
    @TableField(value="return_freight_type")
    private String returnFreightType;

    /**
     * 是否线上商品 1：需实名商品 2:只填写实名商品 3:无需实名商品
     */
    @TableField(value="is_on_line")
    private Integer isOnLine;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
