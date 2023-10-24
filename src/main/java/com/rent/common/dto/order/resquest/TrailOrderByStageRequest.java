package com.rent.common.dto.order.resquest;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-24 下午 6:01:18
 * @since 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "账单试算请求类")
public class TrailOrderByStageRequest implements Serializable {

    private static final long serialVersionUID = -240898011031761579L;

    // @NotBlank
    @Schema(description = "订单编号")
    private String orderId;

    @Min(1)
    @Schema(description = "租期")
    private int duration;

    @NotNull
    @Schema(description = "价格")
    private BigDecimal price;

    @NotNull
    @Schema(description = "数量")
    private int num;

    @Schema(description = "优惠券编号")
    private String couponId;

    @NotNull
    @Schema(description = "租期开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @NotNull
    @Schema(description = "租期结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end;

    @NotBlank
    @Schema(description = "用户uid")
    private String uid;

    @NotBlank
    @Schema(description = "产品id")
    private String productId;

    @NotNull
    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "首期减免金额")
    private BigDecimal retainDeductionAmount;

    @Schema(description = "增值服务id列表")
    private List<String> additionalServicesIds;



}
