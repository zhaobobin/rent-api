        
package com.rent.controller.backstage;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.HotSearchSaveReqDto;
import com.rent.service.marketing.HotSearchConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "热门搜索关键词")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/hotSearchConfig")
@RequiredArgsConstructor
public class HotSearchConfigController {

    private final HotSearchConfigService hotSearchConfigService;

    @Operation(summary = "获取热门搜索关键词配置")
    @GetMapping("/list")
    public CommonResponse<List<String>> list(@RequestParam("channelId")String channelId) {
        return CommonResponse.<List<String>>builder().data(hotSearchConfigService.list(channelId)).build();
    }

    @Operation(summary = "保存热门搜索关键词配置")
    @PostMapping("/save")
    public CommonResponse<Boolean> save(@RequestBody HotSearchSaveReqDto request) {
        hotSearchConfigService.save(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

}
