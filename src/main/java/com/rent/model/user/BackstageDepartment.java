
package com.rent.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;


/**
 * 后台部门
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Accessors(chain = true)
@TableName("ct_backstage_department")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BackstageDepartment {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 部门名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 只能描述
     * 
     */
    @TableField(value="description")
    private String description;

    /**
     * 部门所属平台 OPE：运营平台 BUSINESS:商家平台
     */
    @TableField(value="platform")
    private EnumBackstageUserPlatform platform;

    /**
     * 所属店铺ID ，运营平台固定为OPE
     *
     */
    @TableField(value="shop_id")
    private String shopId;


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


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
