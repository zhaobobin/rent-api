package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.common.enums.order.EnumOrderType;
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
 * 订单备注
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单备注")
public class OrderRemarkDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 备注来源 OPE：运营    BUSINESS：商户
     */
    @Schema(description = "备注来源 OPE：运营    BUSINESS：商户")
    private EnumOrderRemarkSource source;

    /**
     * 订单类型
     */
    @Schema(description = "订单类型")
    private EnumOrderType orderType;

    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private String orderId;

    /**
     * 备注人姓名
     */
    @Schema(description = "备注人姓名")
    private String userName;

    /**
     * 备注内容
     */
    @Schema(description = "备注内容")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
