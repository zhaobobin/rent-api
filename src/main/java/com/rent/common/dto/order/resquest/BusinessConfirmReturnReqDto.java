package com.rent.common.dto.order.resquest;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-18 16:38
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商家确认归还请求类")
public class BusinessConfirmReturnReqDto implements Serializable {

    private static final long serialVersionUID = 1774438202421526441L;

    @NotBlank(message = "订单编号不能为空")
    @Schema(description = "订单编号", required = true)
    private String orderId;

    @Schema(description = "归还时间",required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    @NotBlank(message = "物流单号不能为空")
    @Schema(description = "物流单号", required = true)
    private String expressNo;

    @Schema(description = "物流公司ID", required = true)
    private Long expressId;

    /**
     * 操作人名称
     */
    private String operatorName;

}
