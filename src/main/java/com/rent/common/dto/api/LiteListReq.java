package com.rent.common.dto.api;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "用户查看订单列表请求参数")
public class LiteListReq extends Page {

    @Schema(description = "订单状态 " +
            "WAITING_PAYMENT:待付款," +
            "PENDING_DEAL:待发货," +
            "WAITING_USER_RECEIVE_CONFIRM:待收货," +
            "RENTING:租用中," +
            "WAITING_SETTLEMENT:待结算," +
            "OVER_DUE:已逾期," +
            "CLOSED:已关闭," +
            "FINISH:已完成")
    private String status;

    @Schema(description = "用户id")
    @NotBlank(message = "用户ID不能为空")
    private String uid;

}
