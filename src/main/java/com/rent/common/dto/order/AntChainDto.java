package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AntChainDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "蚁盾分")
    private String shieldScore;

    @Schema(description = "是否上链")
    private Boolean syncToChain;

    @Schema(description = "是否投保")
    private Boolean insure;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
