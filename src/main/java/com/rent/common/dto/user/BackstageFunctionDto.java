package com.rent.common.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 后台功能点
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackstageFunctionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private String name;

    private String code;

    private String type;

    private String platform;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
