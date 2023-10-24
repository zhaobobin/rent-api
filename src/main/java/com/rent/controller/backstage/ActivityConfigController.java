        
package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.*;
import com.rent.service.marketing.ActivityConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 活动专区配置
 * @author xiaotong
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/activityConfig")
@RequiredArgsConstructor
public class ActivityConfigController {

    private final ActivityConfigService activityConfigService;

    /**
     * 查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/list")
    public CommonResponse<List<ActivityConfigListDto>> list(@RequestParam("channelId")String channelId) {
        return CommonResponse.<List<ActivityConfigListDto>>builder().data(activityConfigService.list(channelId)).build();
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public CommonResponse<ActivityConfigListDto> detail(@RequestParam("id")Long id) {
        return CommonResponse.<ActivityConfigListDto>builder().data(activityConfigService.detail(id)).build();
    }

    /**
     * 编辑栏目配置
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody ActivityConfigReqDto dto) {
        return CommonResponse.<Boolean>builder().data(activityConfigService.update(dto)).build();
    }

}
