
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
 * 后台用户
 *
 * @author zhao
 * @Date 2020-06-24 11:46
 */
@Data
@Accessors(chain = true)
@TableName("ct_backstage_user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BackstageUser {


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
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 电子邮件
     * 
     */
    @TableField(value="email")
    private String email;
    /**
     * 密码
     * 
     */
    @TableField(value="password")
    private String password;
    /**
     * 类型：运营=OPE，商家=BUSINESS
     * 
     */
    @TableField(value="type")
    private EnumBackstageUserPlatform type;
    /**
     * 状态值 有效=VALID 冻结=FROZEN
     * 
     */
    @TableField(value="status")
    private EnumBackstageUserStatus status;
    /**
     * 手机号码
     * 
     */
    @TableField(value="mobile")
    private String mobile;
    /**
     * 店铺ID，运营人员为固定OPE
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 盐值
     * 
     */
    @TableField(value="salt")
    private String salt;

    /**
     * 盐值
     *
     */
    @TableField(value="remark")
    private String remark;
    /**
     * 添加该用户的用户名
     * 
     */
    @TableField(value="create_user_name")
    private String createUserName;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
