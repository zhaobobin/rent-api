package com.rent.common.dto.user;


import com.rent.common.enums.user.EnumBackstageUserPlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台部门
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackstageDepartmentDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    private Long id;

    /**
     * 部门名称
     * 
     */
    private String name;

    /**
     * 职能描述
     * 
     */
    private String description;


    /**
     * 部门所属平台 OPE：运营平台 BUSINESS:商家平台
     */
    private EnumBackstageUserPlatform platform;

    /**
     * 所属店铺ID ，运营平台固定为OPE
     *
     */
    private String shopId;

    /**
     * 成员数量
     */
    private Integer userCount;

    /**
     * CreateTime
     *
     */
    private Date createTime;

    /**
     * UpdateTime
     *
     */
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
