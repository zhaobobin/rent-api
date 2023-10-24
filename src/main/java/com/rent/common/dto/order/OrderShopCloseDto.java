package com.rent.common.dto.order;

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
 * 商家风控关单
 *
 * @author xiaoyao
 * @Date 2020-06-17 16:54
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商家风控关单")
public class OrderShopCloseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * ShopId
     */
    @Schema(description = "ShopId")
    private String shopId;

    /**
     * ShopOperatorId
     */
    @Schema(description = "ShopOperatorId")
    private String shopOperatorId;

    /**
     * OrderId
     */
    @Schema(description = "OrderId")
    private String orderId;

    /**
     * 关闭原因
     */
    @Schema(description = "关闭原因")
    private String closeReason;

    /**
     * CertificateImages
     */
    @Schema(description = "CertificateImages")
    private String certificateImages;

    /**
     * CreateTime
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * DeleteTime
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
