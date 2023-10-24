package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@Schema(description = "订单同步请求类")
@NoArgsConstructor
@AllArgsConstructor
public class AlipayOrderSyncRequest implements Serializable {

    //支付宝订单信息同步接口
    @NotBlank
    @Schema(description = "订单编号")
    private String orderId;

    //支付宝交易号
    @NotBlank
    @Schema(description = "支付宝交易号")
    private String tradeNo;

    //标识一笔交易多次请求，同一笔交易多次信息同步时需要保证唯一
    @NotBlank
    @Schema(description = "标识一笔交易多次请求，同一笔交易多次信息同步时需要保证唯一")
    private String outRequestNo;

    //交易信息同步对应的业务类型，具体值与支付宝约定；
    private String bizType;

    //商户传入同步信息
    private String orderBizInfo;

}
