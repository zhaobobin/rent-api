package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账期表
 *
 * @author xiaotong
 * @Date 2020-06-16 10:09
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "账期表")
public class ChannelAccountPeriodSubmitDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "账期ID")
    private Long accountPeriodId;

    private BigDecimal settleAmount;

    private String settleRemark;

    private String backstageUserName;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
