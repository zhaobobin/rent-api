package com.rent.common.dto.backstage;


import com.rent.common.dto.order.OrderByStagesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 分账信息
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "分账详细信息页面数据")
public class SplitBillDetailRentDto extends SplitBillDetailBaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分期信息")
    private List<OrderByStagesDto> orderByStagesDtoList;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
