package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-10-26 下午 4:33:47
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "订单操作响应类")
public class UserOrdersStatusTransferDto implements Serializable {

    private static final long serialVersionUID = -3745969371841121878L;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderId;
    /**
     * 操作人id
     */
    @Schema(description = "操作人id")
    private String operatorId;
    /**
     * 操作人姓名
     */
    @Schema(description = "操作人姓名")
    private String operatorName;
    /**
     * 原状态
     */
    @Schema(description = "原状态")
    private String oldStatus;
    /**
     * 新状态
     */
    @Schema(description = "新状态")
    private String newStatus;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;
    /**
     * 操作
     */
    @Schema(description = "操作")
    private String operate;

}
