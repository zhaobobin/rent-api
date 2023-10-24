
package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.order.response.EnableApplyFeeBillTicketResp;
import com.rent.common.dto.order.response.FeeBillTicketPageResp;
import com.rent.common.dto.order.resquest.FeeBillTicketApplyReq;
import com.rent.common.dto.order.resquest.FeeBillTicketPageReq;
import com.rent.service.order.FeeBillTicketService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "费用发票模块")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/feeBillTicket")
@RequiredArgsConstructor
public class FeeBillTicketController {

    private final FeeBillTicketService feeBillTicketService;

    @Operation(summary = "查看可以申请开票的费用记录id")
    @GetMapping("/getEnableApplyFeeBill")
    public CommonResponse<EnableApplyFeeBillTicketResp> getEnableApplyFeeBill() {
        String shopId = LoginUserUtil.getLoginUser().getShopId();
        return CommonResponse.<EnableApplyFeeBillTicketResp>builder().data(feeBillTicketService.getEnableApplyFeeBill(shopId)).build();
    }

    @Operation(summary = "申请开票")
    @PostMapping("/apply")
    public CommonResponse<Boolean> apply(@RequestBody FeeBillTicketApplyReq req) {
        String shopId = LoginUserUtil.getLoginUser().getShopId();
        feeBillTicketService.apply(req.getFeeBillIdList(),req.getAmount(),shopId);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "分页查看发票申请")
    @PostMapping("/page")
    public CommonResponse<Page<FeeBillTicketPageResp>> page(@RequestBody FeeBillTicketPageReq req) {
        return CommonResponse.<Page<FeeBillTicketPageResp>>builder().data(feeBillTicketService.page(req)).build();
    }

    @Operation(summary = "确认开票")
    @GetMapping("/confirm")
    public CommonResponse<Boolean> confirm(@RequestParam("id")@Parameter(name = "id",description = "开票ID",required = true) Long id) {
        feeBillTicketService.confirm(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }
}

