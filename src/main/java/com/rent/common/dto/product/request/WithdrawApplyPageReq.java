package com.rent.common.dto.product.request;

import com.rent.common.dto.Page;
import com.rent.common.enums.product.EnumShopWithdrawApplyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "商家提现审批 分页列表页面请求参数")
public class WithdrawApplyPageReq extends Page {

    @Schema(description = "店铺ID")
    private String shopId;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "申请状态")
    private EnumShopWithdrawApplyStatus status ;


}
