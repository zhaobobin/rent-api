        
package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.BannerConfigAddReqDto;
import com.rent.common.dto.marketing.BannerConfigListDto;
import com.rent.service.marketing.BannerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Banner配置
 * @author xiaotong
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/bannerConfig")

public class BannerConfigController {

    private BannerConfigService bannerConfigService;
    @Autowired
    public BannerConfigController(BannerConfigService bannerConfigService) {
        this.bannerConfigService = bannerConfigService;
    }

    /**
     * 查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/list")
    public CommonResponse<List<BannerConfigListDto>> list(@RequestParam("channelId")String channelId) {
        String type = "BUSINESS";
        return CommonResponse.<List<BannerConfigListDto>>builder().data(bannerConfigService.list(channelId,type)).build();
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public CommonResponse<BannerConfigListDto> detail(@RequestParam("id")Long id) {
        return CommonResponse.<BannerConfigListDto>builder().data(bannerConfigService.detail(id)).build();
    }


    /**
     * 添加Banner配置
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public CommonResponse<Boolean> add(@RequestBody BannerConfigAddReqDto dto) {
        return CommonResponse.<Boolean>builder().data(bannerConfigService.add(dto)).build();
    }

    /**
     * 编辑Banner配置
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody BannerConfigAddReqDto dto) {
        return CommonResponse.<Boolean>builder().data(bannerConfigService.update(dto)).build();
    }


    /**
     * 开关
     * @param dto
     * @return
     */
    @PostMapping("/open")
    public CommonResponse<Boolean> open(@RequestBody BannerConfigAddReqDto dto) {
        return CommonResponse.<Boolean>builder().data(bannerConfigService.open(dto)).build();
    }

    /**
     * 删除
     * @param dto
     * @return
     */
    @PostMapping("/delete")
    public CommonResponse<Boolean> delete(@RequestBody BannerConfigAddReqDto dto) {
        return CommonResponse.<Boolean>builder().data(bannerConfigService.delete(dto)).build();
    }
}
