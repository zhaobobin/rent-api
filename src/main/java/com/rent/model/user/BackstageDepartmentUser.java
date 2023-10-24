
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
 * 后台部门用户映射表
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Accessors(chain = true)
@TableName("ct_backstage_department_user")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BackstageDepartmentUser {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 部门ID
     * 
     */
    @TableField(value="department_id")
    private Long departmentId;
    /**
     * 用户ID
     * 
     */
    @TableField(value="backstage_user_id")
    private Long backstageUserId;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
