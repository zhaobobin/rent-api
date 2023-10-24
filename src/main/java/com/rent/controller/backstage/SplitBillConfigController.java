        
package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.product.ShopSplitBillReqDto;
import com.rent.common.dto.product.SpiltBillConfigDto;
import com.rent.common.dto.product.SplitBillAuditDto;
import com.rent.common.dto.product.SplitBillConfigListDto;
import com.rent.service.product.ChannelSplitBillService;
import com.rent.service.product.SplitBillConfigService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author zhaowenchao
 */
@RestController
@Tag(name = "佣金审批模块")
@RequestMapping("/zyj-backstage-web/hzsx/splitBillConfig")
@RequiredArgsConstructor
public class SplitBillConfigController {

    private final SplitBillConfigService splitBillConfigService;
    private final ChannelSplitBillService channelSplitBillService;

    @Operation(summary = "佣金设置列表页面")
    @PostMapping("/page")
    public CommonResponse<Page<SplitBillConfigListDto>> page(@RequestBody ShopSplitBillReqDto request) {
        Page<SplitBillConfigListDto> response = splitBillConfigService.page(request);
        return CommonResponse.<Page<SplitBillConfigListDto>>builder().data(response).build();
    }


    @Operation(summary = "查看佣金设置详情")
    @GetMapping("/detail")
    public CommonResponse<SpiltBillConfigDto> detail(@RequestParam("id") Long id) {
        SpiltBillConfigDto detail = splitBillConfigService.detail(id);
        return CommonResponse.<SpiltBillConfigDto>builder().data(detail).build();
    }

    @Operation(summary = "佣金审核")
    @PostMapping("/audit")
    public CommonResponse<Boolean> audit(@RequestBody SplitBillAuditDto dto) {
        splitBillConfigService.audit(dto);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "财务人员渠道审核")
    @PostMapping("/channelAudit")
    public CommonResponse<Boolean> channelAudit(@RequestBody SplitBillAuditDto auditDto) {
        auditDto.setAuditUser(LoginUserUtil.getLoginUser().getMobile());
        channelSplitBillService.channelAudit(auditDto);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }
}
