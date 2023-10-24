        
package com.rent.controller.backstage;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.resp.SplitBillShopResp;
import com.rent.common.dto.product.ShopSplitBillReqDto;
import com.rent.common.dto.product.SpiltBillConfigDto;
import com.rent.common.dto.product.SplitBillConfigListDto;
import com.rent.service.product.SplitBillConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhaowenchao
 */
@RestController
@Tag(name = "小程序佣金设置模块")
@RequestMapping("/zyj-backstage-web/hzsx/splitBillConfigLite")
@RequiredArgsConstructor
public class SplitBillConfigLiteController {

    private final SplitBillConfigService splitBillConfigService;

    @Operation(summary = "佣金设置列表页面")
    @PostMapping("/page")
    public CommonResponse<Page<SplitBillConfigListDto>> page(@RequestBody ShopSplitBillReqDto request) {
        Page<SplitBillConfigListDto> response = splitBillConfigService.page(request);
        return CommonResponse.<Page<SplitBillConfigListDto>>builder().data(response).build();
    }

    @Operation(summary = "添加佣金设置页面获取店铺列表接口")
    @GetMapping("/getShopList")
    public CommonResponse<JSONObject> getShopList(@RequestParam(value = "shopName",required = false) String shopName) {
        List<SplitBillShopResp> shopList = splitBillConfigService.getShopList(shopName);
        JSONObject result = new JSONObject();
        result.put("identity","identity");
        result.put("name","name");
        result.put("shopInfoList",shopList);
        return CommonResponse.<JSONObject>builder().data(result).build();
    }

    @Operation(summary = "添加佣金设置页")
    @PostMapping("/add")
    public CommonResponse<Boolean> add(@RequestBody SpiltBillConfigDto spiltBillConfigDto) {
        splitBillConfigService.add(spiltBillConfigDto);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "更新佣金设置页")
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody SpiltBillConfigDto spiltBillConfigDto) {
        splitBillConfigService.update(spiltBillConfigDto);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "查看佣金设置详情")
    @GetMapping("/detail")
    public CommonResponse<SpiltBillConfigDto> detail(@RequestParam("id") Long id) {
        SpiltBillConfigDto detail = splitBillConfigService.detail(id);
        return CommonResponse.<SpiltBillConfigDto>builder().data(detail).build();
    }
}
