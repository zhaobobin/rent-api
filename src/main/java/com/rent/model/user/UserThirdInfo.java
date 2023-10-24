
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
 * 用户第三方信息表
 *
 * @author zhao
 * @Date 2020-06-28 14:24
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_third_info")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserThirdInfo {


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
     * 用户头像
     * 
     */
    @TableField(value="avatar")
    private String avatar;
    /**
     * 用户昵称
     * 
     */
    @TableField(value="nick_name")
    private String nickName;
    /**
     * 省
     * 
     */
    @TableField(value="province")
    private String province;
    /**
     * 市
     * 
     */
    @TableField(value="city")
    private String city;
    /**
     * 性别
     * 
     */
    @TableField(value="gender")
    private String gender;
    /**
     * 用户类型值 1代表公司账户2代表个人账户 
     * 
     */
    @TableField(value="user_type")
    private String userType;
    /**
     * 是否实名认证
     * 
     */
    @TableField(value="is_certified")
    private String isCertified;
    /**
     * 是否学生
     * 
     */
    @TableField(value="is_student_cetified")
    private String isStudentCetified;
    /**
     * 第三方用户唯一标识
     * 
     */
    @TableField(value="third_id")
    private String thirdId;
    /**
     * 用户状态 Q代表快速注册用户  T代表已认证用户   B代表被冻结账户  W代表已注册，未激活的账户
     * 
     */
    @TableField(value="user_status")
    private String userStatus;
    /**
     * 用户id
     * 
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 用户姓名
     * 
     */
    @TableField(value="user_name")
    private String userName;
    /**
     * 手机号
     * 
     */
    @TableField(value="telephone")
    private String telephone;
    /**
     * ALIPAY:支付宝  TOUTIAO：头条
     * 
     */
    @TableField(value="channel")
    private String channel;

    /**
     * app名称-针对于字节跳动
     */
    @TableField(value="app_name")
    private String appName;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
