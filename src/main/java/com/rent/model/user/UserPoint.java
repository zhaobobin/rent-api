
package com.rent.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 用户活动埋点统计
 *
 * @author youruo
 * @Date 2020-09-25 14:38
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_point")
public class UserPoint {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 渠道位置
     * 
     */
    @TableField(value="position")
    private String position;
    /**
     * 用户ID
     * 
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 活动名称
     * 
     */
    @TableField(value="action")
    private String action;
    /**
     * 渠道
     * 
     */
    @TableField(value="channel_id")
    private String channelId;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
