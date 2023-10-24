
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
 * 自定义tab产品挂载表
 *
 * @author youruo
 * @Date 2020-06-16 10:00
 */
@Data
@Accessors(chain = true)
@TableName("ct_tab_product")
public class TabProduct {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 删除时间
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 跟新时间
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * 序列
     * 
     */
    @TableField(value="index_sort")
    private Integer indexSort;
    /**
     * 产品名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 父tab的id
     * 
     */
    @TableField(value="tab_id")
    private Long tabId;
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
    private String price;

    /**
     * 新旧程度
     * 
     */
    @TableField(value="old_new_degree")
    private String oldNewDegree;
    /**
     * 产品唯一id
     * 
     */
    @TableField(value="item_id")
    private String itemId;
    /**
     * LinkUrl
     * 
     */
    @TableField(value="link_url")
    private String linkUrl;
    /**
     * 店铺名称 
     * 
     */
    @TableField(value="shop_name")
    private String shopName;

     /**
     * 销售量
     *
     */
    @TableField(value="sales_volume")
    private Integer salesVolume;

    /**
     * 0失效  1有效
     *
     */
    @TableField(value="status")
    private Integer status;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
