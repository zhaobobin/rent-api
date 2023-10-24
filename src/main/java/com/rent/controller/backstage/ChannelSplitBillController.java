        
package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.SplitBillDetailRentDto;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.resquest.AccountPeriodRemarkReqDto;
import com.rent.common.dto.order.resquest.ChannelAccountPeriodReqDto;
import com.rent.service.order.ChannelAccountPeriodRemarkService;
import com.rent.service.order.ChannelAccountPeriodService;
import com.rent.service.product.ChannelSplitBillService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 账期模块控制器
 *
 * @author xiaotong
 * @Date 2020-08-11 09:59
 */
@Tag(name = "渠道账期模块")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/channelAccountPeriod")
@RequiredArgsConstructor
public class ChannelSplitBillController {

    private final ChannelAccountPeriodService channelAccountPeriodService;
    private final ChannelAccountPeriodRemarkService channelAccountPeriodRemarkService;

    @Operation(summary = "渠道佣金管理列表")
    @PostMapping("/queryChannelAccountPeriodPage")
    public CommonResponse<Page<ChannelAccountPeriodDto>> queryChannelAccountPeriodPage(@RequestBody ChannelAccountPeriodReqDto reqDto){
        return CommonResponse.<Page<ChannelAccountPeriodDto>>builder().data(channelAccountPeriodService.queryChannelAccountPeriodPage(reqDto)).build();
    }

    @Operation(summary = "获取详细信息")
    @GetMapping("/detail")
    public CommonResponse<ChannelAccountPeriodDetailDto> detail(@RequestParam("id") Long id){
        return CommonResponse.<ChannelAccountPeriodDetailDto>builder().data(channelAccountPeriodService.detail(id)).build();
    }

    @Operation(summary = "提交结算")
    @PostMapping("/submitSettle")
    public CommonResponse<Void> submitSettle(@RequestBody AccountPeriodSubmitSettleDto req){
        req.setBackstageUserName(LoginUserUtil.getLoginUser().getName());
        channelAccountPeriodService.submitSettle(req);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "提交审核")
    @PostMapping("/submitAudit")
    public CommonResponse<Void> submitAudit(@RequestBody AccountPeriodSubmitAuditDto req){
        req.setBackstageUserName(LoginUserUtil.getLoginUser().getName());
        channelAccountPeriodService.submitAudit(req);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "新增备注")
    @PostMapping("/add")
    public CommonResponse<Void> add(@RequestBody AccountPeriodRemarkReqDto reqDto){
        reqDto.setBackstageUserName(LoginUserUtil.getLoginUser().getName());
        channelAccountPeriodRemarkService.add(reqDto);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "查询备注列表")
    @PostMapping("/listByAccountPeriodId")
    CommonResponse<Page<AccountPeriodRemarkDto>> listByAccountPeriodId(@RequestBody AccountPeriodRemarkReqDto request){
        return CommonResponse.<Page<AccountPeriodRemarkDto>>builder().data(channelAccountPeriodRemarkService.listByAccountPeriodId(request)).build();
    }


    @Operation(summary = "查询 租赁 分账 详细信息")
    @GetMapping("/rentDetail")
    public CommonResponse<SplitBillDetailRentDto> rentDetail(@RequestParam("orderId") String orderId){
        return CommonResponse.<SplitBillDetailRentDto>builder().data(channelAccountPeriodService.rentDetail(orderId)).build();
    }

    @Operation(summary = "订单详情")
    @PostMapping("/listRent")
    public CommonResponse<Page<ChannelSplitBillRentDto>> listRent(@RequestBody AccountPeriodItemReqDto request){
        return CommonResponse.<Page<ChannelSplitBillRentDto>>builder().data(channelAccountPeriodService.listRent(request)).build();
    }


    @Operation(summary = "查看明细-常规订单")
    @PostMapping("/rent")
    public CommonResponse<Page<ChannelSplitBillRentDto>> rent(@RequestBody AccountPeriodItemReqDto request){
        return CommonResponse.<Page<ChannelSplitBillRentDto>>builder().data(channelAccountPeriodService.rent(request)).build();
    }
}

