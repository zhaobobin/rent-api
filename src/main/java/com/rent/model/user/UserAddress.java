
package com.rent.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;


/**
 * 用户地址表
 *
 * @author zhao
 * @Date 2020-06-18 13:54
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_address")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserAddress {


    /**
     * Id
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
     * 更新时间
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * 删除时间
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 省
     * 
     */
    @TableField(value="province")
    private Integer province;
    /**
     * 市
     * 
     */
    @TableField(value="city")
    private Integer city;
    /**
     * 所属用户id
     * 
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 区
     * 
     */
    @TableField(value="area")
    private Integer area;
    /**
     * 详细地址
     * 
     */
    @TableField(value="street")
    private String street;
    /**
     * 邮编
     * 
     */
    @TableField(value="zcode")
    private String zcode;
    /**
     * 手机号码
     * 
     */
    @TableField(value="telephone")
    private String telephone;
    /**
     * 收货人姓名
     * 
     */
    @TableField(value="realname")
    private String realname;
    /**
     * 是否为默认收货地址  0否 1是 
     * 
     */
    @TableField(value="is_default")
    private Boolean isDefault;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
