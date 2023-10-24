
package com.rent.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.common.enums.user.EnumBackstageUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;


/**
 * 审核用户表
 */
@Data
@Accessors(chain = true)
@TableName("ct_audit_user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuditUser {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="update_time")
    private Date updateTime;

    @TableField(value="backstage_user_id")
    private Long backstageUserId;

    @TableField(value="mobile")
    private String mobile;

    @TableField(value="name")
    private String name;

    @TableField(value="status")
    private EnumBackstageUserStatus status;

    @TableField(value="type")
    private EnumBackstageUserPlatform type;

    @TableField(value="shop_id")
    private String shopId;

    @TableField(value="qrcode_url")
    private String qrcodeUrl;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
