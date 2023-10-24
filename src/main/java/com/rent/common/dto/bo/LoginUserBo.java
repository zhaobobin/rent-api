package com.rent.common.dto.bo;


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
public class LoginUserBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型：运营=OPE，商家=BUSINESS
     */
    private EnumBackstageUserPlatform type;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 店铺ID，运营人员为固定OPE
     */
    private String shopId;

    /**
     * 是否是审核人员
     */
    private Boolean isAuditUser;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
