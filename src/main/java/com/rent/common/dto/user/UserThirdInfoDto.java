package com.rent.common.dto.user;

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
 * 用户第三方信息表
 *
 * @author zhao
 * @Date 2020-06-28 14:24
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户第三方信息表")
public class UserThirdInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * CreateTime
     * 
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     * 
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * 用户头像
     * 
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 用户昵称
     * 
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 省
     * 
     */
    @Schema(description = "省")
    private String province;

    /**
     * 市
     * 
     */
    @Schema(description = "市")
    private String city;

    /**
     * 性别
     * 
     */
    @Schema(description = "性别")
    private String gender;

    /**
     * 用户类型值 1代表公司账户2代表个人账户 
     * 
     */
    @Schema(description = "用户类型值 1代表公司账户2代表个人账户 ")
    private String userType;

    /**
     * 是否实名认证
     * 
     */
    @Schema(description = "是否实名认证")
    private String isCertified;

    /**
     * 是否学生
     * 
     */
    @Schema(description = "是否学生")
    private String isStudentCetified;

    /**
     * 第三方用户唯一标识
     * 
     */
    @Schema(description = "第三方用户唯一标识")
    private String thirdId;

    /**
     * 用户状态 Q代表快速注册用户  T代表已认证用户   B代表被冻结账户  W代表已注册，未激活的账户
     * 
     */
    @Schema(description = "用户状态 Q代表快速注册用户  T代表已认证用户   B代表被冻结账户  W代表已注册，未激活的账户")
    private String userStatus;

    /**
     * 用户id
     * 
     */
    @Schema(description = "用户id")
    private String uid;

    /**
     * 用户姓名
     * 
     */
    @Schema(description = "用户姓名")
    private String userName;

    /**
     * 手机号
     * 
     */
    @Schema(description = "手机号")
    private String telephone;

    /**
     * ALIPAY:支付宝  TOUTIAO：头条
     * 
     */
    @Schema(description = "ALIPAY:支付宝  TOUTIAO：头条")
    private String channel;

    @Schema(description = "app名称")
    private String appName;

    private String token;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
