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

import java.util.Date;

/**
 * 用户紧急联系人表
 *
 * @author wu
 * @Date 2023-9-19 21:35:36
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_emergency_contact")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserEmergencyContact {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    @TableField(value = "uid")
    private String uid;

    @TableField(value = "name")
    private String name;

    @TableField(value = "relationship")
    private String relationship;

    @TableField(value = "mobile")
    private String mobile;

    /**
     * 是否实名认证通过
     */
    @TableField(value = "checked")
    private Boolean checked;

    @TableField(value = "create_time")
    private Date createTime;

}
