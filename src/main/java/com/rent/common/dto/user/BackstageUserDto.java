package com.rent.common.dto.user;


import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.common.enums.user.EnumBackstageUserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户
 *
 * @author zhao
 * @Date 2020-06-24 11:46
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "后台用户信息")
public class BackstageUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "删除时间")
    private Date deleteTime;

    @Schema(description = "成员姓名")
    private String name;

    /**
     * 电子邮件
     * 
     */
    @Schema(description = "成员姓名")
    private String email;

    /**
     * 密码
     *
     */
    private String password;

    /**
     * 类型：运营=OPE，商家=BUSINESS
     * 
     */
    private EnumBackstageUserPlatform type;

    /**
     * 状态值 有效=VALID 冻结=FROZEN
     * 
     */
    private EnumBackstageUserStatus status;

    /**
     * 手机号码
     * 
     */
    private String mobile;



    /**
     * 店铺ID，运营人员为固定OPE
     * 
     */
    private String shopId;

    /**
     * 盐值
     *
     */
    private String salt;

    /**
     * 添加该用户的用户名
     * 
     */
    private String createUserName;

    /**
     * 添加该用户的用户名
     */
    private String remark;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
