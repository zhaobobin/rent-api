package com.rent.common.dto.backstage;


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
 * 转单记录表
 *
 * @author youruo
 * @Date 2021-12-22 17:55
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "转单记录表")
public class TransferOrderRecordReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private String orderId;

    /**
     * 商品Id-被转
     */
    @Schema(description = "商品Id-被转")
    private String transferredProductId;
    /**
     * 快照id-被转
     */
    @Schema(description = "快照id-被转")
    private Long transferredSnapShotId;
    /**
     * 商品sku_id-被转
     */
    @Schema(description = "商品sku_id-被转")
    private Long transferredSkuId;
    /**
     * 店铺id-被转
     */
    @Schema(description = "店铺名字-被转")
    private String transferredShopName;


    /**
     * 商品Id-接手
     */
    @Schema(description = "商品Id-接手")
    private String transferProductId;
    /**
     * 快照id-接手
     */
    @Schema(description = "快照id-接手")
    private Long transferSnapShotId;

    @Schema(description = "店铺名字-接手")
    private String transferShopName;
    /**
     * 商品sku_id-接手
     */
    @Schema(description = "商品sku_id-接手")
    private Long transferSkuId;


    /**
     * 店铺id-被转
     */
    @Schema(description = "店铺id-被转")
    private String transferredShopId;

    /**
     * 店铺id-接手
     */
    @Schema(description = "店铺id-接手")
    private String transferShopId;

    /**
     * 操作员
     */
    @Schema(description = "操作员")
    private String operator;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateTime;

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
