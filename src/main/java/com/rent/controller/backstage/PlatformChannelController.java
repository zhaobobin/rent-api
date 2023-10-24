package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.config.outside.PlatformChannelDto;
import com.rent.service.product.PlatformChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @program: zwzrent-ope
 * @description:平台来源渠道
 * @author: yr
 * @date: 2020-04-23
 **/
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/platform")
@Tag(name = "运营商品模块", description = "平台来源渠道")
@RequiredArgsConstructor
public class PlatformChannelController {

    private final PlatformChannelService platformChannelService;

    @Operation(summary = "获取上架渠道集合")
    @GetMapping("selectPlateformChannel")
    public CommonResponse<List<PlatformChannelDto>> selectPlateformChannel() {
        return CommonResponse.<List<PlatformChannelDto>>builder().data(platformChannelService.queryPlatformChannelDetailList()).build();
    }

    @Operation(summary = "获取上架渠道集合")
    @GetMapping("listLitePlatformChannel")
    public CommonResponse<List<PlatformChannelDto>> listLitePlatformChannel() {
        return CommonResponse.<List<PlatformChannelDto>>builder().data(platformChannelService.queryPlatformChannelDetailList()).build();
    }
}

