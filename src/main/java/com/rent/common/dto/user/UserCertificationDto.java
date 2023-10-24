package com.rent.common.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 用户认证表
 *
 * @author zhao
 * @Date 2020-06-18 15:06
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户认证表")
public class UserCertificationDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     *
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 用户平台唯一标识
     * 
     */
    @Schema(description = "用户平台唯一标识")
    private String uid;

    /**
     * 用户姓名
     * 
     */
    @Schema(description = "用户姓名")
    private String userName;

    /**
     * 用户身份号码
     * 
     */
    @Schema(description = "用户身份号码")
    private String idCard;

    /**
     * 手机号
     * 
     */
    @Schema(description = "手机号")
    private String telephone;

    /**
     * 身份证照片 前面
     * 
     */
    @Schema(description = "身份证照片 前面")
    private String idCardFrontUrl;

    /**
     * 身份证照片背面
     * 
     */
    @Schema(description = "身份证照片背面")
    private String idCardBackUrl;

    /**
     * 身份证到期时间
     *
     */
    @Schema(description = "身份证到期时间")
    private String limitDate;
    @Schema(description ="有效期开始时间")
    private String startDate;
    @Schema(description ="地址")
    private String address;
    @Schema(description ="性别")
    private String sex;
    @Schema(description ="民族")
    private String nation;
    @Schema(description ="发证机关")
    private String issueOrg;

    /**
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     * 
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "获取验证码返回的codeKey")
    private String codeKey;

    @Schema(description = "用户提交的短信验证码")
    private String smsCode;

    @Schema(description = "获取验证码返回的codeTime")
    private Long codeTime;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
