
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
 * 平台物流表
 *
 * @author youruo
 * @Date 2020-06-16 11:46
 */
@Data
@Accessors(chain = true)
@TableName("ct_platform_express")
public class PlatformExpress {

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
     * 物流公司名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 物流公司logo
     * 
     */
    @TableField(value="logo")
    private String logo;
    /**
     * 物流公司简称
     * 
     */
    @TableField(value="short_name")
    private String shortName;
    /**
     * 物流公司官方电话
     * 
     */
    @TableField(value="telephone")
    private String telephone;
    /**
     * 排位，越大越靠前
     * 
     */
    @TableField(value="`index`")
    private Integer index;
    /**
     * 支付宝对应的的code
     * 
     */
    @TableField(value="ali_code")
    private String aliCode;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
