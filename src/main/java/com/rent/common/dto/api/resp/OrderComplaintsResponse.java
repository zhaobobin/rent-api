package com.rent.common.dto.api.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2020-09-27 17:39
 **/
@Data
@Schema(description = "用户可投诉的订单信息")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderComplaintsResponse implements Serializable {

    private static final long serialVersionUID = 9129675550421135874L;

    @Schema(description = "订单ID")
    private String orderId;
    @Schema(description = "商户ID")
    private String shopId;
    @Schema(description = "商户名称")
    private String shopName;
    @Schema(description = "拼接字符串")
    private String contactString;
}
