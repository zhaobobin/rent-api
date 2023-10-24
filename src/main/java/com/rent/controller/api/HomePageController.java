package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.*;
import com.rent.common.dto.vo.ApiPlatformExpressVo;
import com.rent.service.marketing.*;
import com.rent.service.product.PlatformExpressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "小程序首页")
@RequestMapping("/zyj-api-web/hzsx/api/homePage")
public class HomePageController {

    private final BannerConfigService bannerConfigService;
    private final IconConfigService iconConfigService;
    private final ColumnConfigService columnConfigService;
    private final ActivityConfigService activityConfigService;
    private final CubesConfigService cubesConfigService;

    /**
     * banner查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/bannerList")
    public CommonResponse<List<BannerConfigListDto>> bannerList(@RequestParam("channelId")String channelId) {
        String type = "API";
        return CommonResponse.<List<BannerConfigListDto>>builder().data(bannerConfigService.list(channelId,type)).build();
    }

    /**
     * 金刚区查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/iconList")
    public CommonResponse<List<IconConfigListDto>> iconList(@RequestParam("channelId")String channelId) {
        return CommonResponse.<List<IconConfigListDto>>builder().data(iconConfigService.list(channelId)).build();
    }

    /**
     * 栏目查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/columnList")
    public CommonResponse<List<ColumnConfigListDto>> columnList(@RequestParam("channelId")String channelId) {
        String type = "API";
        return CommonResponse.<List<ColumnConfigListDto>>builder().data(columnConfigService.list(channelId,type)).build();
    }

    /**
     * 活动专区查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/activityList")
    public CommonResponse<List<ActivityConfigListDto>> activityList(@RequestParam("channelId")String channelId) {
        return CommonResponse.<List<ActivityConfigListDto>>builder().data(activityConfigService.list(channelId)).build();
    }


    /**
     * 商品分类查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/cubesList")
    public CommonResponse<List<CubesConfigListDto>> cubesList(@RequestParam("channelId")String channelId) {
        String type = "API";
        return CommonResponse.<List<CubesConfigListDto>>builder().data(cubesConfigService.list(channelId, type)).build();
    }
}
