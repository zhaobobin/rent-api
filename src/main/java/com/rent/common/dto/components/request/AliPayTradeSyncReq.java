package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户通过其他【非官方】渠道支付后，同步代扣信息为已经支付，避免影响用户芝麻信用
 * @author zhaowenchao
 */
@Data
@Schema(description = "支付宝解密入参")
public class AliPayTradeSyncReq {

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "租期数组，例如[1,2]表示需要同步第一期和第二期")
    private List<String> periods;



}
