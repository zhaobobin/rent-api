package com.rent.common.dto.user;


import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 配置信息
 *
 * @author zhao
 * @Date 2020-11-11 16:52
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "配置信息")
public class ConfigReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 值
     */
    @Schema(description = "值")
    private String value;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
