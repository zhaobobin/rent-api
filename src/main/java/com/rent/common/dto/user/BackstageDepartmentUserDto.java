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
 * 后台部门用户映射表
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackstageDepartmentUserDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    private Long id;

    /**
     * 部门ID
     * 
     */
    private Long departmentId;

    /**
     * 用户ID
     * 
     */
    private Long backstageUserId;

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
