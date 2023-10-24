package com.rent.common.dto.components.request;

import com.rent.common.dto.Page;
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
 * 小程序订单中心商品信息
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "小程序订单中心商品信息")
public class OrderCenterSyncLogReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private String orderId;

    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private String productId;

    /**
     * 支付宝返回skuID
     */
    @Schema(description = "支付宝返回skuID")
    private String skuId;

    /**
     * 支付宝返回itemID
     */
    @Schema(description = "支付宝返回itemID")
    private String itemId;

    /**
     * 同步类型("MERCHANT_DELIVERD","商家发货中")("MERCHANT_FINISHED","订单已完成")("MERCHANT_LOANING","租赁中")
     */
    @Schema(description = "同步类型 MERCHANT_DELIVERD:商家发货中 MERCHANT_FINISHED:订单已完成 MERCHANT_LOANING:租赁中")
    private String type;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String state;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String reqParams;

    /**
     * 响应参数
     */
    @Schema(description = "响应参数")
    private String resp;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 来源id
     */
    @Schema(description = "来源id")
    private String channelId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
