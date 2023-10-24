package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.user.UserStatisticsDto;
import com.rent.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhaowenchao
 */
@Slf4j
@RestController
@Tag(name = "首页用户数据统计")
@RequestMapping("/zyj-backstage-web/hzsx/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "首页用户数据统计")
    @GetMapping("/getUserStatistics")
    public CommonResponse<UserStatisticsDto> getUserStatistics() {
        UserStatisticsDto dto = userService.getUserStatistics();
        return CommonResponse.<UserStatisticsDto>builder().data(dto).build();
    }

}
