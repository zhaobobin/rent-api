
package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.user.ConfigDto;
import com.rent.common.dto.user.ConfigReqDto;
import com.rent.service.user.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 配置信息控制器
 *
 * @author zhao
 * @Date 2020-11-11 16:52
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @Operation(summary = "更新配置信息")
    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody ConfigDto request) {
        return CommonResponse.<Boolean>builder().data(configService.modifyConfig(request)).build();
    }

    @Operation(summary = "分页查询配置信息")
    @PostMapping("/page")
    public CommonResponse<Page<ConfigDto>> page(@RequestBody ConfigReqDto request) {
        return CommonResponse.<Page<ConfigDto>>builder().data(configService.queryConfigPage(request)).build();
    }

    @Operation(summary = "根据code获取value值")
    @GetMapping("/getByCode")
    public CommonResponse<String> getByCode(@RequestParam("code") String code) {
        return CommonResponse.<String>builder().data(configService.getConfigByCode(code)).build();
    }

}
