package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumAccountPeriodOperator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 账期流程
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "账期表")
public class AccountPeriodProgressDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "operator")
    private String operator;

    @Schema(description = "status")
    private EnumAccountPeriodOperator status;

    @Schema(description = "create_time")
    private Date createTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
