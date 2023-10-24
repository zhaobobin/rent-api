package com.rent.common.dto.user;


import com.rent.common.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

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
public class BackstageUserFunctionReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     * 
     */
    private Long backstageUserId;

    /**
     * 功能CODES
     * 
     */
    private List<Long> functionIds;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
