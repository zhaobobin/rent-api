package com.rent.common.dto.components.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "发送短信入参")
public class SendMsgDto implements Serializable {
    private static final long serialVersionUID = -5342940771537795963L;
    @Schema(description = "手机号码", required = true)
    @NotBlank
    private String telephone;
    @Schema(description = "短信编号", required = true)
    // @NotBlank
    private String msgCode;
    @Schema(description = "订单编号")
    private String orderId;
    @Schema(description = "商品名称")
    private String productName;
    private String logisticsName;
    @Schema(description = "物流单号")
    private String expressNo;
    @Schema(description = "商家服务电话")
    private String shopServiceTel;
    private Integer type;

    private String userName;

    private String reletOrderId;

    private String categoryName;
    private String period;
    private String month;
    private String day;
    private String url;
    private String appName;


    private BigDecimal currentRent;

    private String shopId;
    private String uid;
    private String code;


}
