package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 店铺分账账户
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺分账账户")
public class SplitBillAuditDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "是否审核通过")
    private Boolean pass;

    @Schema(description = "审核意见")
    private String auditRemark;

    @Schema(description = "审核人员-前端不需要传值")
    private String auditUser;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
