package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代客支付详情
 *
 * @author xiaoyao
 * @Date 2020-06-11 17:25
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "代客支付详情")
public class OrderByStagesForValetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "操作时间")
    private Date updateTime;

    @Schema(description = "当期应付租额")
    private BigDecimal currentPeriodsRent;

    @Schema(description = "操作人")
    private String userName;

    @Schema(description = "支付凭证")
    private String proof;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
