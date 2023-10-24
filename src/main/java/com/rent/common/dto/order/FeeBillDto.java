package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumFeeBillType;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.EnumSettleStatus;
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
public class FeeBillDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 账期ID
     */
    private Long fundFlowId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单所属用户ID
     */
    private String uid;

    /**
     * 订单所属用户支付宝ID
     */
    private String aliUid;

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 店铺ID
     */
    private String shopName;

    /**
     * 费用金额
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    private EnumSettleStatus status;

    /**
     * 费用类型
     */
    private EnumFeeBillType type;

    /**
     * 渠道
     */
    private String channelId;

    /**
     * 用户下单时间
     */
    private EnumOrderStatus orderStatus;

    /**
     * 用户下单时间
     */
    private Date orderTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
