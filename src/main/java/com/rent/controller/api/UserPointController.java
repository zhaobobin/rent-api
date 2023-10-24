package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.service.user.UserPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: components-center
 * @description:
 * @author: yr
 * @create: 2020-09-25 14:55
 **/
@RestController
@RequestMapping("/zyj-api-web/hzsx/userPoint")
@Tag(name = "小程序渠道用户埋点")
@RequiredArgsConstructor
public class UserPointController {

    private final UserPointService userPointService;

    @Operation(summary = "渠道用户埋点")
    @GetMapping("/insertUserPoint")
    public CommonResponse<Boolean> insertUserPoint(
            @Parameter(name = "position",description = "埋点位置") @RequestParam(value = "position", required = false, defaultValue = "position") String position,
            @Parameter(name = "uid",description = "用户ID") @RequestParam(value = "uid", required = false, defaultValue = "uid") String uid,
            @Parameter(name = "action",description = "用户行为") @RequestParam(value ="action", required = false, defaultValue = "action") String action,
            @Parameter(name = "channelId",description = "渠道ID") @RequestParam(value = "channelId", required = false, defaultValue = "channelId") String channelId) {
        userPointService.insertUserPoint(position, uid, action, channelId);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

}
