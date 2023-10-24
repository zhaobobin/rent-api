
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
 * 店铺归还地址表
 *
 * @author youruo
 * @Date 2020-06-17 10:47
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_give_back_addresses")
public class ShopGiveBackAddresses {

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
     * 店铺id
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 省id
     * 
     */
    @TableField(value="province_id")
    private Integer provinceId;
    /**
     * 市id
     * 
     */
    @TableField(value="city_id")
    private Integer cityId;
    /**
     * 区id
     * 
     */
    @TableField(value="area_id")
    private Integer areaId;
    /**
     * 街道
     * 
     */
    @TableField(value="street")
    private String street;
    /**
     * 收件人手机号码
     * 
     */
    @TableField(value="telephone")
    private String telephone;
    /**
     * 邮编
     * 
     */
    @TableField(value="zcode")
    private String zcode;
    /**
     * 收件人姓名
     * 
     */
    @TableField(value="name")
    private String name;

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
