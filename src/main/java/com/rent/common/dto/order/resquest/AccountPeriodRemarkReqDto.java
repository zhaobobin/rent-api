package com.rent.common.dto.order.resquest;

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
public class AccountPeriodRemarkReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "账期ID")
    private Long accountPeriodId;

    @Schema(description = "备注内容")
    private String content;

    @Schema(description = "备注人姓名")
    private String backstageUserName;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
