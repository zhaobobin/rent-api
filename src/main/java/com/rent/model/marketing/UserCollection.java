
package com.rent.model.marketing;

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
 * 用户收藏表
 *
 * @author zhao
 * @Date 2020-07-22 15:28
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_collection")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserCollection {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     * 
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 收藏的资源ID
     * 
     */
    @TableField(value="resource_id")
    private String resourceId;
    /**
     * 收藏的资源类型
     * 
     */
    @TableField(value="resource_type")
    private String resourceType;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
