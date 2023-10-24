
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
 * 用户认证表
 *
 * @author zhao
 * @Date 2020-06-18 15:06
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_certification")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserCertification {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 用户平台唯一标识
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
     * 用户身份号码
     * 
     */
    @TableField(value="id_card")
    private String idCard;
    /**
     * 手机号
     * 
     */
    @TableField(value="telephone")
    private String telephone;
    /**
     * 身份证照片 前面
     * 
     */
    @TableField(value="id_card_front_url")
    private String idCardFrontUrl;
    /**
     * 身份证照片背面
     * 
     */
    @TableField(value="id_card_back_url")
    private String idCardBackUrl;
    /**
     * 证件到期日
     *
     */
    @TableField(value="limit_date")
    private Date limitDate;

    /**
     * 有效期开始时间
     *
     */
    @TableField(value="start_date")
    private Date startDate;

    /**
     * 地址
     *
     */
    @TableField(value="address")
    private String address;

    /**
     * 性别
     *
     */
    @TableField(value="sex")
    private String sex;

    /**
     * 民族
     *
     */
    @TableField(value="nation")
    private String nation;

    /**
     * 发证机关
     *
     */
    @TableField(value="issue_org")
    private String issueOrg;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
