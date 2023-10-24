package com.rent.common.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户买断
 *
 * @author xiaoyao
 * @Date 2020-06-23 14:50
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotRentDto implements Serializable {

    private static final long serialVersionUID = -5209352611626014391L;

    /**
     * 租赁订单ID
     */
    private String orderId;

    /**
     * 已支付租金
     */
    private String productId;

    /**
     * 买断支付状态
     */
    private String uid;

    /**
     * 买断支付状态
     */
    private String shopId;
}
