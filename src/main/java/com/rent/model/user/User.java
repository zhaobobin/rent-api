
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
 * 用户主体表
 *
 * @author zhao
 * @Date 2020-06-28 14:20
 */
@Data
@Accessors(chain = true)
@TableName("ct_user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {


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
     * 用户手机号
     * 
     */
    @TableField(value="telephone")
    private String telephone;
    /**
     * 用户id 自动生成
     * 
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 0 未认证 1已经认证
     * 
     */
    @TableField(value="is_auth")
    private Boolean isAuth;
    /**
     * 帐号被封 0正常 1已经被封
     * 
     */
    @TableField(value="is_disabled")
    private Boolean isDisabled;
    /**
     * 身份证照片上传状态
     * 
     */
    @TableField(value="id_card_photo_status")
    private Boolean idCardPhotoStatus;
    /**
     * 第一次生成的用户的渠道
     * 
     */
    @TableField(value="channel")
    private String channel;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
