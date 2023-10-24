
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
 * 店铺品牌证书表
 *
 * @author youruo
 * @Date 2020-06-17 10:38
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_brands_certificates")
public class ShopBrandsCertificates {

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
     * 所属店铺品牌id
     * 
     */
    @TableField(value="shop_brand_id")
    private Integer shopBrandId;
    /**
     * 证书图片链接
     * 
     */
    @TableField(value="src")
    private String src;
    /**
     * 类型 0为商标注册证，1为质检报告/3C认证 2为品牌授权书
     * 
     */
    @TableField(value="type")
    private Boolean type;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
