        
package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.*;
import com.rent.service.marketing.CubesConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品分类配置
 * @author xiaotong
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/cubesConfig")
public class CubesConfigController {

    private CubesConfigService cubesConfigService;
    @Autowired
    public CubesConfigController(CubesConfigService cubesConfigService) {
        this.cubesConfigService = cubesConfigService;
    }

    /**
     * 查询列表
     * @param channelId
     * @return
     */
    @GetMapping("/list")
    public CommonResponse<List<CubesConfigListDto>> list(@RequestParam("channelId")String channelId) {
        String type = "BACKSTAGE";
        return CommonResponse.<List<CubesConfigListDto>>builder().data(cubesConfigService.list(channelId,type)).build();
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public CommonResponse<CubesConfigListDto> detail(@RequestParam("id")Long id) {
        return CommonResponse.<CubesConfigListDto>builder().data(cubesConfigService.detail(id)).build();
    }


    /**
     * 添加商品分类配置
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public CommonResponse<Boolean> add(@RequestBody CubesConfigAddReqDto dto) {
        return CommonResponse.<Boolean>builder().data(cubesConfigService.add(dto)).build();
    }

    /**
     * 编辑商品分类配置
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody CubesConfigAddReqDto dto) {
        return CommonResponse.<Boolean>builder().data(cubesConfigService.update(dto)).build();
    }


    /**
     * 删除
     * @param dto
     * @return
     */
    @PostMapping("/delete")
    public CommonResponse<Boolean> delete(@RequestBody CubesConfigAddReqDto dto) {
        return CommonResponse.<Boolean>builder().data(cubesConfigService.delete(dto)).build();
    }

}
