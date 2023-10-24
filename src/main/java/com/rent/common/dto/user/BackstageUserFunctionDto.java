package com.rent.common.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户可以用的功能点
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackstageUserFunctionDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    private Long id;

    /**
     * 用户ID
     * 
     */
    private Long backstageUserId;

    /**
     * 功能CODE
     * 
     */
    private Long functionId;

    /**
     *来源类型：DEPARTMENT：部门 ADD：其他管理员手动添加
     */
    private String sourceType;
    /**
     *来源的值 部门ID|管理员ID
     */
    private String sourceValue;

    /**
     * CreateTime
     * 
     */
    private Date createTime;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
