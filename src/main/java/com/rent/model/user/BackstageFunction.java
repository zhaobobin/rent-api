
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
 * 后台功能点
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Accessors(chain = true)
@TableName("ct_backstage_function")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BackstageFunction {
    /**
     * Id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 父级功能ID
     */
    @TableField(value="parent_id")
    private Long parentId;
    /**
     * 功能名称
     */
    @TableField(value="name")
    private String name;
    /**
     * 功能编码
     */
    @TableField(value="code")
    private String code;
    /**
     * 是否是菜单栏
     */
    @TableField(value="type")
    private String type;

    /**
     * 功能所属平台 OPE: 运营平台 BUSINESS：商家平台
     */
    @TableField(value="platform")
    private EnumBackstageUserPlatform platform;
    /**
     * 创建时间
     */
    @TableField(value="create_time")
    private Date createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
