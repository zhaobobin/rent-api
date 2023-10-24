package com.rent.common.dto.backstage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2021-1-19 上午 10:40:22
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForceConfirmReceiptRequest implements Serializable {

    private static final long serialVersionUID = -5545947711995233265L;

    /**订单号*/
    @Schema(description = "订单号")
    @NotBlank(message = "订单编号不能为空")
    private String orderId;

    /**收货日期*/
    @Schema(description = "收货日期")
    @NotNull(message = "收货日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date confirmDate;

    /**操作者Id*/
    @Schema(description = "操作者Id",hidden = true)
    private String operatorId;

    /**操作者名称*/
    @Schema(description = "操作者名称",hidden = true)
    private String operatorName;
}
