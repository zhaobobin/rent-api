
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 店铺品牌表
 *
 * @author youruo
 * @Date 2020-06-17 10:37
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_brands")
public class ShopBrands {

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
     * 所属平台品牌的id
     * 所属平台品牌的id, 新品牌无此值
     */
    @TableField(value="brand_id")
    private Integer brandId;
    /**
     * 品牌主营类目
     * 品牌主营类目, 为店铺主营类目中的id
     */
    @TableField(value="main_category_id")
    private Integer mainCategoryId;
    /**
     * 品牌授权类型id，根据不同的店铺类型，区分不同的授权类型
     * 
     */
    @TableField(value="brand_auth_type")
    private Integer brandAuthType;
    /**
     * 0正在审核 1审核不通过 2审核通过
     * 
     */
    @TableField(value="status")
    private Boolean status;
    /**
     * 所属店铺id
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 提交资料的店铺管理人员id
     * 
     */
    @TableField(value="shop_admin_id")
    private String shopAdminId;
    /**
     * 店铺类型id
     * 
     */
    @TableField(value="shop_type_id")
    private Integer shopTypeId;
    /**
     * 是否属于已注册品牌
     * 
     */
    @TableField(value="is_r")
    private Boolean isR;
    /**
     * 是否属于正在注册品牌
     * 
     */
    @TableField(value="is_tm")
    private Boolean isTm;
    /**
     * 品牌名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 品牌描述
     * 
     */
    @TableField(value="description")
    private String description;
    /**
     * 品牌正方形Logo
     * 
     */
    @TableField(value="square_logo")
    private String squareLogo;
    /**
     * 品牌长方形Logo
     * 
     */
    @TableField(value="rectangle_logo")
    private String rectangleLogo;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
