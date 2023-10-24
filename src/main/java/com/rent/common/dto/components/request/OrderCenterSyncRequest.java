package com.rent.common.dto.components.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author udo
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCenterSyncRequest implements Serializable {

    private static final long serialVersionUID = 8615135602881574386L;

    /**
     * 产品ID
     */
    private String productId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 用户ID（在支付宝的ID）
     */
    private String buyerId;

    /**
     * 交易号
     */
    private String tradeNo;

    /**
     * 金额
     */
    private String amount;

    /**
     * 物流号
     */
    private String trackingNo;

    /**
     * 物流公司code
     */
    private String logisticsCode;

    /**
     * 产品图片在oss中的key
     */
    private String objectKey;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品标价。单位分
     */
    private Long price;

    /**
     * 产品标价。单位分
     */
    private String channelId;
}
