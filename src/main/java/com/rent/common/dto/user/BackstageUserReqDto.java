package com.rent.common.dto.user;

import com.rent.common.dto.Page;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 后台用户
 *
 * @author zhao
 * @Date 2020-06-24 11:46
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackstageUserReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    private String email;

    private String mobile;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 店铺ID，运营人员为固定OPE
     *
     */
    private String shopId;

    /**
     * 类型：运营=OPE，商家=BUSINESS
     *
     */
    private EnumBackstageUserPlatform type;




    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
