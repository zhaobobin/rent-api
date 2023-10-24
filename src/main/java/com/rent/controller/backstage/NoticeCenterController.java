
package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.*;
import com.rent.service.marketing.OpeNoticeItemService;
import com.rent.service.marketing.OpeNoticeService;
import com.rent.service.marketing.OpeNoticeTabService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 商家中心公告通知
 * @Date: 2021/8/16
 */
@Tag(name = "商家中心公告通知")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/noticeCenter")
@RequiredArgsConstructor
public class NoticeCenterController {

    private final OpeNoticeService opeNoticeService;
    private final OpeNoticeTabService opeNoticeTabService;
    private final OpeNoticeItemService opeNoticeItemService;


    @Operation(summary = "获取首页公告集合")
    @PostMapping("/queryOpeNoticeDetailList")
    public CommonResponse<List<OpeNoticeDto>> queryOpeNoticeDetailList() {
        List<OpeNoticeDto> list = opeNoticeService.queryOpeNoticeDetailList(OpeNoticeReqDto.builder().build());
        return CommonResponse.<List<OpeNoticeDto>>builder().data(list).build();
    }


    @Operation(summary = "获取首页公告分页")
    @PostMapping("/queryOpeNoticePage")
    public CommonResponse<Page<OpeNoticeDto>> queryOpeNoticePage(@RequestBody OpeNoticeReqDto request) {
        Page<OpeNoticeDto> opeNoticeDtoPage = opeNoticeService.queryOpeNoticePage(request);
        return CommonResponse.<Page<OpeNoticeDto>>builder().data(opeNoticeDtoPage).build();
    }


    @Operation(summary = "添加首页公告")
    @PostMapping("/addOpeNotice")
    public CommonResponse<Boolean> addOpeNotice(@RequestBody OpeNoticeDto request) {
        opeNoticeService.addOpeNotice(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "修改首页公告")
    @PostMapping("/modifyOpeNotice")
    public CommonResponse<Boolean> modifyOpeNotice(@RequestBody OpeNoticeDto request) {
        opeNoticeService.modifyOpeNotice(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "删除首页公告")
    @GetMapping("/deleteOpeNotice")
    public CommonResponse<Boolean> deleteOpeNotice(@RequestParam("id") Long id) {
        opeNoticeService.deleteOpeNotice(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "获取商家常见问题tab集合")
    @PostMapping("/queryOpeNoticeTabDetailList")
    public CommonResponse<List<OpeNoticeTabDto>> queryOpeNoticeTabDetailList() {
        List<OpeNoticeTabDto> opeNoticeTabDtos = opeNoticeTabService.queryOpeNoticeTabDetailList(OpeNoticeTabReqDto.builder().build());
        return CommonResponse.<List<OpeNoticeTabDto>>builder().data(opeNoticeTabDtos).build();
    }

    @Operation(summary = "添加商家常见问题tab")
    @PostMapping("/addOpeNoticeTab")
    public CommonResponse<Boolean> addOpeNoticeTab(@RequestBody OpeNoticeTabDto request) {
        opeNoticeTabService.addOpeNoticeTab(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "修改商家常见问题tab")
    @PostMapping("/modifyOpeNoticeTab")
    public CommonResponse<Boolean> modifyOpeNoticeTab(@RequestBody OpeNoticeTabDto request) {
        opeNoticeTabService.modifyOpeNoticeTab(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "删除商家常见问题tab")
    @GetMapping("/deleteOpeNoticeTab")
    public CommonResponse<Boolean> deleteOpeNoticeTab(@RequestParam("id") Long id) {
        opeNoticeTabService.deleteOpeNoticeTab(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "菜单tab-常见问题集合")
    @PostMapping("/queryOpeNoticeTabList")
    public CommonResponse<List<OpeNoticeTabDto>> queryOpeNoticeTabList() {
        List<OpeNoticeTabDto> opeNoticeTabDtos = opeNoticeTabService.queryOpeNoticeTabList();
        return CommonResponse.<List<OpeNoticeTabDto>>builder().data(opeNoticeTabDtos).build();
    }

    @Operation(summary = "商家常见问题tab下常见问题分页")
    @PostMapping("/queryOpeNoticeItemPage")
    public CommonResponse<Page<OpeNoticeItemDto>> queryOpeNoticeItemPage(@RequestBody OpeNoticeItemReqDto request) {
        Page<OpeNoticeItemDto> opeNoticeItemDtoPage = opeNoticeItemService.queryOpeNoticeItemPage(request);
        return CommonResponse.<Page<OpeNoticeItemDto>>builder().data(opeNoticeItemDtoPage).build();
    }



    @Operation(summary = "商家常见问题tab下常见问题新增")
    @PostMapping("/addOpeNoticeItem")
    public CommonResponse<Boolean> addOpeNoticeItem(@RequestBody OpeNoticeItemDto request) {
        opeNoticeItemService.addOpeNoticeItem(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }




    @Operation(summary = "商家常见问题tab下常见问题修改")
    @PostMapping("/modifyOpeNoticeItem")
    public CommonResponse<Boolean> modifyOpeNoticeItem(@RequestBody OpeNoticeItemDto request) {
        opeNoticeItemService.modifyOpeNoticeItem(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "商家常见问题tab下常见问题查看")
    @PostMapping("/queryOpeNoticeItemDetail")
    public CommonResponse<OpeNoticeItemDto> queryOpeNoticeItemDetail(@RequestBody OpeNoticeItemReqDto request) {
        OpeNoticeItemDto opeNoticeItemDto = opeNoticeItemService.queryOpeNoticeItemDetail(request);
        return CommonResponse.<OpeNoticeItemDto>builder().data(opeNoticeItemDto).build();
    }




    @Operation(summary = "商家常见问题tab下常见问题删除")
    @GetMapping("/deleteOpeNoticeItem")
    public CommonResponse<Boolean> deleteOpeNoticeItem(@RequestParam("id") Long id) {
        opeNoticeItemService.deleteOpeNoticeItem(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }




}
