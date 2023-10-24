package com.rent.controller.backstage;

import com.rent.common.cache.api.IndexCache;
import com.rent.common.cache.backstage.NoticeCache;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.api.ApiNoticeDto;
import com.rent.common.dto.backstage.NoticeDto;
import com.rent.config.outside.PlatformChannelDto;
import com.rent.service.product.PlatformChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2020-11-05 11:36
 **/
@Slf4j
@RestController
@Tag(name = "公告接口", description = "公告接口")
@RequestMapping("/zyj-backstage-web/hzsx/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final PlatformChannelService platformChannelService;

    @Operation(summary = "运营公告查询接口")
    @GetMapping("/selectNotice")
    public CommonResponse<NoticeDto> selectNotice() {
        NoticeDto dto = NoticeCache.getNoticeCache();
        return CommonResponse.<NoticeDto>builder().data(dto).build();
    }

    @Operation(summary = "运营公告修改接口")
    @PostMapping("/updateNotice")
    public CommonResponse<Boolean> updateNotice(@RequestBody NoticeDto dto) {
        NoticeCache.setNoticeCache(dto);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "小程序公告查询接口")
    @GetMapping("/selectApiNotice")
    public CommonResponse<List<ApiNoticeDto>> selectApiNotice() {
        List<PlatformChannelDto> platformChannelDtoList =  platformChannelService.queryPlatformChannelDetailList();
        List<ApiNoticeDto> list = new ArrayList<>(platformChannelDtoList.size());
        for (PlatformChannelDto platformChannelDto : platformChannelDtoList) {
            ApiNoticeDto apiNoticeDto = new ApiNoticeDto();
            apiNoticeDto.setChannelId(platformChannelDto.getChannelId());
            apiNoticeDto.setChannelName(platformChannelDto.getChannelName());
            apiNoticeDto.setContent(IndexCache.getIndexApiNoticeCache(platformChannelDto.getChannelId()));
            list.add(apiNoticeDto);
        }
        return CommonResponse.<List<ApiNoticeDto>>builder().data(list).build();
    }


    @Operation(summary = "小程序公告修改接口")
    @PostMapping("/updateApiNotice")
    public CommonResponse<Boolean> updateApiNotice(@RequestBody ApiNoticeDto request) {
        IndexCache.setIndexApiNoticeCache(request.getChannelId(),request.getContent());
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }



}
