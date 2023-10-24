package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumFeeBillType;
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
public class FeeBillDetailReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 账期ID
     */
    private Long fundFlowId;


    /**
     * 店铺ID
     */
    private String shopId;


    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 状态
     */
    private String status;

    /**
     * 费用类型
     */
    private EnumFeeBillType type;


    /**
     * 创建时间
     */
    private Date startTime;

    /**
     * 创建时间
     */
    private Date endTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
