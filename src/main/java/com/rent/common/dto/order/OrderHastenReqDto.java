package com.rent.common.dto.order;

import com.rent.common.dto.Page;
import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.common.enums.order.EnumOrderResult;
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
@Schema(description = "催收记录")
public class OrderHastenReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 催收记录来源 01：运营    02：商户
     */
    @Schema(description = "催收记录来源 01：运营    02：商户")
    private EnumOrderRemarkSource source;

    /**
     * 01为常规订单 02为拼团订单    03 续租订单  04买断订单
     */
    @Schema(description = "订单类型 01为常规订单 02为拼团订单    03 续租订单  04买断订单")
    private EnumOrderType orderType;

    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private String orderId;


    /**
     * 催收人
     */
    @Schema(description = "催收人")
    private String userName;

    /**
     * 结果
     */
    @Schema(description = "结果",hidden = true)
    private EnumOrderResult result;

    /**
     * 小记
     */
    @Schema(description = "小记",hidden = true)
    private String notes;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间",hidden = true)
    private Date createTime;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间",hidden = true)
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
