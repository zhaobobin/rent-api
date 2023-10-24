package com.rent.common.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.common.enums.order.PaymentMethod;
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
 * @date 2020-8-10 下午 1:43:55
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户续租确认提交请求类")
public class UserOrderReletSubmitRequest implements Serializable {

    private static final long serialVersionUID = 4675284035553287742L;

    @Schema(description = "原订单Id")
    @NotBlank
    private String originalOrderId;
    @Schema(description = "订单id", required = true)
    @NotBlank
    private String orderId;
    @Schema(description = "下单用户id", required = true)
    @NotBlank(message = "uid不能为空")
    private String uid;
    @Schema(description = "skuId", required = true)
    @NotNull
    private Long skuId;
    @Schema(description = "产品id", required = true)
    @NotBlank(message = "商品id不能为空")
    private String productId;
    @Schema(description = "周期天数", required = true)
    @Min(value = 1, message = "租期不得小于1天")
    private int duration;
    @Schema(description = "租期起始时间", required = true)
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @Schema(description = "租期结束时间", required = true)
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end;
    @Schema(description = "产品数量", required = true)
    @Min(value = 1, message = "产品数量不得小于1")
    private int num;
    @Schema(description = "增值服务id列表")
    private List<String> additionalServicesIds;
    @Schema(description = "总租金")
    @NotNull
    private BigDecimal rentAmount;
    @Schema(description = "月租金")
    @NotNull
    private BigDecimal monthRentAmount;
    @Schema(description = "总期数")
    @NotNull
    private Integer installmentCount;
    @Schema(description = "押金金额")
    @NotNull
    private BigDecimal deposit;
    @Schema(description = "渠道编号")
    private String channelId;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "价格", required = true)
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
    @Schema(description = "原始价格", required = true)
    @NotNull(message = "原始价格不能为空")
    private BigDecimal originalPrice;


    @Schema(description = "支付方式")
    private PaymentMethod paymentMethod;

    @Schema(description = "已绑定卡Id")
    private String bindCardId;


    @Schema(description = "短信验证码-银行卡支付需要验证码")
    private String verifyCode;
}
