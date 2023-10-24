
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
 * 商家详情轮播配置
 *
 * @author youruo
 * @Date 2020-07-23 17:37
 */
@Data
@Accessors(chain = true)
@TableName("ct_ope_index_shop_banner")
public class OpeIndexShopBanner {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
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
     * OnlineTime
     * 
     */
    @TableField(value="online_time")
    private Date onlineTime;
    /**
     * ShopId
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * Name
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * ImgSrc
     * 
     */
    @TableField(value="img_src")
    private String imgSrc;
    /**
     * JumpUrl
     * 
     */
    @TableField(value="jump_url")
    private String jumpUrl;
    /**
     * 0 失效 1 有效
     * 
     */
    @TableField(value="status")
    private Integer status;
    /**
     * IndexSort
     * 
     */
    @TableField(value="index_sort")
    private Integer indexSort;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
