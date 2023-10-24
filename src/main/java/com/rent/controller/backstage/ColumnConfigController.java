        
package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.ColumnConfigListDto;
import com.rent.common.dto.marketing.ColumnConfigReqDto;
import com.rent.service.marketing.ColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 栏目配置
 * @author xiaotong
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/columnConfig")
public class ColumnConfigController {

    private ColumnConfigService columnConfigService;
    @Autowired
    public ColumnConfigController(ColumnConfigService columnConfigService) {
        this.columnConfigService = columnConfigService;
    }

    /**
     * 查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/list")
    public CommonResponse<List<ColumnConfigListDto>> list(@RequestParam("channelId")String channelId) {
        String type = "BUSINESS";
        return CommonResponse.<List<ColumnConfigListDto>>builder().data(columnConfigService.list(channelId, type)).build();
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public CommonResponse<ColumnConfigListDto> detail(@RequestParam("id")Long id) {
        return CommonResponse.<ColumnConfigListDto>builder().data(columnConfigService.detail(id)).build();
    }

    /**
     * 编辑栏目配置
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody ColumnConfigReqDto dto) {
        return CommonResponse.<Boolean>builder().data(columnConfigService.update(dto)).build();
    }

}
