package com.rent.common.dto.backstage;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 订单报表统计
 *
 * @author xiaoyao
 * @Date 2020-08-11 16:17
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单报表统计")
public class OrderReportDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 统计时间
     */
    @Schema(description = "统计时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statisticsDate;

    /**
     * 总单量
     */
    @Schema(description = "总单量")
    private Long totalOrderCount;

    /**
     * 成功下单单量
     */
    @Schema(description = "成功下单单量")
    private Long successOrderCount;

    /**
     * 成功下单总租金
     */
    @Schema(description = "成功下单总租金")
    private BigDecimal successOrderRent;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
