package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.product.ChannelSplitBillDto;
import com.rent.common.dto.product.ChannelStoreDto;
import com.rent.common.dto.product.ChannelStoreReqDto;
import com.rent.service.product.ChannelSplitBillService;
import com.rent.service.product.ChannelStoreService;
import com.rent.service.product.ShopFundService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaotong
 */
@RestController
@Tag(name = "运营平台渠道管理模块")
@RequestMapping("/zyj-backstage-web/hzsx/marketingStore")
@RequiredArgsConstructor
public class MarketingStoreController {

    private final ChannelSplitBillService channelSplitBillService;
    private final ChannelStoreService channelStoreService;
    private final ShopFundService shopFundFlowService;

    @Operation(summary = "门店列表分页")
    @PostMapping("/page")
    public CommonResponse<Page<ChannelStoreDto>> page(@RequestBody ChannelStoreReqDto request) {
        return CommonResponse.<Page<ChannelStoreDto>>builder().data(channelStoreService.page(request)).build();
    }

    @Operation(summary = "获取门店详情")
    @GetMapping("/getOne")
    public CommonResponse<ChannelStoreDto> getOne(@RequestParam String uid) {
        return CommonResponse.<ChannelStoreDto>builder().data(channelStoreService.getByMarketingId(uid)).build();
    }

    @Operation(summary = "修改门店信息")
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody ChannelSplitBillDto channelSplitBillDto) {
        LoginUserBo loginUserBo = LoginUserUtil.getLoginUser();
        channelSplitBillDto.setAddUser(loginUserBo.getName());
        return CommonResponse.<Boolean>builder().data(channelSplitBillService.update(channelSplitBillDto)).build();
    }

    @Operation(summary = "添加添加门店")
    @PostMapping("/add")
    public CommonResponse<Boolean> add(@RequestBody ChannelStoreDto channelStoreDto) {
        return CommonResponse.<Boolean>builder().data(channelStoreService.add(channelStoreDto)).build();
    }
}
