        
package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.IconConfigAddReqDto;
import com.rent.common.dto.marketing.IconConfigListDto;
import com.rent.service.marketing.IconConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 金刚区配置
 * @author xiaotong
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/iconConfig")
public class IconConfigController {

    private IconConfigService iconConfigService;
    @Autowired
    public IconConfigController(IconConfigService iconConfigService) {
        this.iconConfigService = iconConfigService;
    }

    /**
     * 查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/list")
    public CommonResponse<List<IconConfigListDto>> page(@RequestParam("channelId")String channelId) {
        return CommonResponse.<List<IconConfigListDto>>builder().data(iconConfigService.list(channelId)).build();
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public CommonResponse<IconConfigListDto> detail(@RequestParam("id")Long id) {
        return CommonResponse.<IconConfigListDto>builder().data(iconConfigService.detail(id)).build();
    }


    /**
     * 添加金刚区配置
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public CommonResponse<Boolean> add(@RequestBody IconConfigAddReqDto dto) {
        return CommonResponse.<Boolean>builder().data(iconConfigService.add(dto)).build();
    }

    /**
     * 编辑金刚区配置
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody IconConfigAddReqDto dto) {
        return CommonResponse.<Boolean>builder().data(iconConfigService.update(dto)).build();
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public CommonResponse<Boolean> delete(@RequestParam("id")Long id) {
        return CommonResponse.<Boolean>builder().data(iconConfigService.delete(id)).build();
    }

}
