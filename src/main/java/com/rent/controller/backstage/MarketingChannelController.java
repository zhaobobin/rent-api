package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.product.ChannelStoreDto;
import com.rent.common.dto.product.ChannelSplitBillDto;
import com.rent.common.dto.product.ChannelSplitBillReqDto;
import com.rent.common.dto.product.ShopFundFlowDto;
import com.rent.service.product.ChannelStoreService;
import com.rent.service.product.ChannelSplitBillService;
import com.rent.service.product.ShopFundService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiaotong
 */
@RestController
@Tag(name = "运营平台渠道管理模块")
@RequestMapping("/zyj-backstage-web/hzsx/marketingChannel")
@RequiredArgsConstructor
public class MarketingChannelController {

    private final ChannelSplitBillService channelSplitBillService;
    private final ChannelStoreService channelStoreService;
    private final ShopFundService shopFundFlowService;

    @Operation(summary = "渠道列表分页")
    @PostMapping("/page")
    public CommonResponse<Page<ChannelSplitBillDto>> page(@RequestBody ChannelSplitBillReqDto request) {
        return CommonResponse.<Page<ChannelSplitBillDto>>builder().data(channelSplitBillService.page(request)).build();
    }

    @Operation(summary = "获取渠道详情")
    @GetMapping("/getOne")
    public CommonResponse<ChannelSplitBillDto> getOne(@RequestParam String uid) {
        return CommonResponse.<ChannelSplitBillDto>builder().data(channelSplitBillService.getOne(uid)).build();
    }

    @Operation(summary = "修改渠道信息")
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody ChannelSplitBillDto channelSplitBillDto) {
        LoginUserBo loginUserBo = LoginUserUtil.getLoginUser();
        channelSplitBillDto.setAddUser(loginUserBo.getName());
        return CommonResponse.<Boolean>builder().data(channelSplitBillService.update(channelSplitBillDto)).build();
    }

    @Operation(summary = "渠道营销码页面")
    @GetMapping("/list")
    public CommonResponse<List<ChannelStoreDto>> list(@RequestParam String channelSplitId) {
        return CommonResponse.<List<ChannelStoreDto>>builder().data(channelStoreService.list(channelSplitId)).build();
    }

    @Operation(summary = "添加渠道营销码")
    @PostMapping("/add")
    public CommonResponse<Boolean> add(@RequestBody ChannelStoreDto channelStoreDto) {
        return CommonResponse.<Boolean>builder().data(channelStoreService.add(channelStoreDto)).build();
    }

    @Operation(summary = "删除渠道营销码")
    @GetMapping("/delete")
    public CommonResponse<Boolean> delete(@RequestParam String id) {
        return CommonResponse.<Boolean>builder().data(channelStoreService.delete(id)).build();
    }

    @Operation(summary = "分页查询渠道资金账户明细")
    @GetMapping("/pageChannelFundFlow")
    public CommonResponse<List<ShopFundFlowDto>> pageChannelFundFlow(@RequestParam String uid) {
        return CommonResponse.<List<ShopFundFlowDto>>builder().data(shopFundFlowService.pageChannelFundFlow(uid)).build();
    }
}
