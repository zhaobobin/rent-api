
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 前台类目商品表
 *
 * @author youruo
 * @Date 2020-06-15 10:28
 */
@Data
@Accessors(chain = true)
@TableName("ct_ope_category_product")
public class OpeCategoryProduct {

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
    @TableField(value = "delete_time", updateStrategy = FieldStrategy.IGNORED)
    private Date deleteTime;
    /**
     * 分类Id
     * 
     */
    @TableField(value="operate_category_id")
    private Integer operateCategoryId;
    /**
     * 父类id
     * 
     */
    @TableField(value="parent_category_id")
    private Integer parentCategoryId;
    /**
     * ItemId
     * 
     */
    @TableField(value="item_id")
    private String itemId;
    /**
     * Image
     * 
     */
    @TableField(value="image")
    private String image;
    /**
     * 价格优惠
     * 
     */
    @TableField(value="price")
    private BigDecimal price;
    /**
     * 价格原价（划横线）
     * 
     */
    @TableField(value="sale")
    private BigDecimal sale;
    /**
     * 新旧程度
     * 
     */
    @TableField(value="old_new_degree")
    private String oldNewDegree;
    /**
     * 服务标签
     * 
     */
    @TableField(value="service_marks")
    private String serviceMarks;
    /**
     * 商品名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 店铺id
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 起租天数
     * 
     */
    @TableField(value="min_rent")
    private Integer minRent;
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
     * 0失效  1有效
     * 
     */
    @TableField(value="status")
    private Integer status;

    /**
     * 物流服务方式
     *
     */
    @TableField(value="freight_type")
    private String freightType;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
