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
public class OrderCenterProductReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private String productId;

    /**
     * 文件在oss服务器中的名称
     */
    @Schema(description = "文件在oss服务器中的名称")
    private String objectKey;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String itemName;

    /**
     * 文件在商品中心的素材标示，创建/更新商品时使用
     */
    @Schema(description = "文件在商品中心的素材标示，创建/更新商品时使用")
    private String materialKey;

    /**
     * 文件在商品中心的素材标识
     */
    @Schema(description = "文件在商品中心的素材标识")
    private String materialId;

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
     * 价格，单位分
     */
    @Schema(description = "价格，单位分")
    private Long price;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 渠道ID
     */
    @Schema(description = "渠道ID")
    private String channelId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
