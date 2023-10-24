
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
 * 后台用户可以用的功能点
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Accessors(chain = true)
@TableName("ct_backstage_user_function")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BackstageUserFunction {


    /**
     * Id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @TableField(value="backstage_user_id")
    private Long backstageUserId;
    /**
     * 功能ID
     */
    @TableField(value="function_id")
    private Long functionId;

    /**
     * 来源类型：DEPARTMENT：部门 ADD：其他管理员手动添加
     */
    @TableField(value="source_type")
    private String sourceType;
    /**
     * 来源的值 部门ID|管理员ID
     */
    @TableField(value="source_value")
    private Long sourceValue;


    /**
     * CreateTime
     */
    @TableField(value="create_time")
    private Date createTime;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
