package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.request.*;
import com.rent.common.dto.backstage.resp.BackstageTabProductResp;
import com.rent.common.dto.backstage.resp.TabDetailResp;
import com.rent.common.dto.backstage.resp.TabListResp;
import com.rent.common.dto.product.AvailableTabProductResp;
import com.rent.service.product.TabService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author zhaowenchao
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/tab")
@Slf4j
@Tag(name = "标签配置")
@RequiredArgsConstructor
public class TabV2Controller {

    private final TabService tabService;

    @Operation(summary = "新增tab")
    @PostMapping("/add")
    public CommonResponse<Boolean> add(@RequestBody AddTabReq request) {
        tabService.add(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "修改tab")
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody UpdateTabReq request) {
        tabService.update(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "删除Tab")
    @GetMapping("/delete")
    public CommonResponse<Boolean> delete(@RequestParam("id") Long id) {
        tabService.delete(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "获取tab列表")
    @GetMapping("/list")
    public CommonResponse<List<TabListResp>> list(@RequestParam("channelId") String channelId) {
        return CommonResponse.<List<TabListResp>>builder().data(tabService.list(channelId)).build();
    }

    @Operation(summary = "查看tab详情")
    @GetMapping("/getById")
    public CommonResponse<TabDetailResp> getById(@RequestParam("id") Long id) {
        return CommonResponse.<TabDetailResp>builder().data(tabService.getById(id)).build();
    }

    @Operation(summary = "获取tab可以添加的商品列表")
    @PostMapping("/getAvailableProduct")
    public CommonResponse<Page<AvailableTabProductResp>> getAvailableProduct(@RequestBody QueryAvailableTabProductReq request) {
        return CommonResponse.<Page<AvailableTabProductResp>>builder().data(tabService.getAvailableProduct(request)).build();
    }

    @Operation(summary = "tab下添加商品")
    @PostMapping("/addProduct")
    public CommonResponse<Boolean> addProduct(@RequestBody AddTabProductReq request) {
        tabService.addProduct(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "查询tab下的商品")
    @GetMapping("/listProduct")
    public CommonResponse<Page<BackstageTabProductResp>> listProduct(
            @RequestParam("tabId") Long tabId,
            @RequestParam("pageNum")Integer pageNum,
            @RequestParam("pageSize")Integer pageSize) {
        return CommonResponse.<Page<BackstageTabProductResp>>builder().data(tabService.listProduct(tabId,pageNum,pageSize)).build();
    }

    @Operation(summary = "修改tab下商品排序")
    @PostMapping("/updateProductSort")
    public CommonResponse<Boolean> updateProductSort(@RequestBody UpdateTabProductSortReq request) {
        tabService.updateProductSort(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "删除tab下的商品")
    @GetMapping("/deleteProduct")
    public CommonResponse<Boolean> deleteProduct(@RequestParam("id") Long id) {
        tabService.deleteProduct(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


}
