package com.rent.common.dto.product.resp;

import com.rent.common.enums.product.EnumShopWithdrawApplyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "商家提现审批 分页列表页面返回结果")
public class WithdrawApplyPageResp {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "申请时间")
    private Date createTime;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "申请金额")
    private BigDecimal amount;

    @Schema(description = "申请状态")
    private EnumShopWithdrawApplyStatus status ;

    @Schema(description = "审核时间")
    private Date updateTime;

    @Schema(description = "审核操作人")
    private String auditUser;

}
