package com.rent.common.dto.user;


import com.rent.common.dto.Page;
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
 * 用户主体表
 *
 * @author zhao
 * @Date 2020-06-28 14:20
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户主体表")
public class UserReqDto extends Page implements Serializable {

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
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 用户手机号
     * 
     */
    @Schema(description = "用户手机号")
    private String telephone;

    /**
     * 用户id 自动生成
     * 
     */
    @Schema(description = "用户id 自动生成")
    private String uid;

    /**
     * 0 未认证 1已经认证
     * 
     */
    @Schema(description = "0 未认证 1已经认证")
    private Boolean isAuth;

    /**
     * 帐号被封 0正常 1已经被封
     * 
     */
    @Schema(description = "帐号被封 0正常 1已经被封")
    private Boolean isDisabled;

    /**
     * 身份证照片上传状态
     * 
     */
    @Schema(description = "身份证照片上传状态")
    private Boolean idCardPhotoStatus;

    /**
     * 第一次生成的用户的渠道
     * 
     */
    @Schema(description = "第一次生成的用户的渠道")
    private String channel;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
