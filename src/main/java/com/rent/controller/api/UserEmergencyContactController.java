package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.user.UserEmergencyContactDto;
import com.rent.service.user.UserEmergencyContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "小程序用户紧急联系人模块")
@RequestMapping("/zyj-api-web/hzsx/aliPay/userEmergencyContact")
public class UserEmergencyContactController {
    private final UserEmergencyContactService userEmergencyContactService;

    @Operation(summary = "校验用户紧急联系人是否完成")
    @GetMapping("/checkUserEmergencyContactCompleted")
    public CommonResponse<Boolean> checkUserEmergencyContactCompleted(
            @Parameter(name = "uid", description = "用户ID", required = true) @RequestParam("uid") String uid
    ) {
        return CommonResponse.<Boolean>builder().data(userEmergencyContactService.checkUserEmergencyContactCompleted(uid)).build();
    }

    @Operation(summary = "获取全部紧急联系人")
    @GetMapping("/getUserEmergencyContacts")
    public CommonResponse<List<UserEmergencyContactDto>> getUserEmergencyContacts(
            @Parameter(name = "uid", description = "用户ID", required = true) @RequestParam("uid") String uid,
            @Parameter(name = "checked", description = "认证状态") @RequestParam("checked") Integer checked
    ) {
        return CommonResponse.<List<UserEmergencyContactDto>>builder().data(userEmergencyContactService.getUserEmergencyContacts(uid, checked)).build();
    }

    @Operation(summary = "新增紧急联系人")
    @PostMapping("/addUserEmergencyContact")
    public CommonResponse<Boolean> addUserAddress(@Parameter(name = "uid", description = "用户ID", required = true) @RequestParam("uid") String uid,
                                                  @RequestBody @Valid UserEmergencyContactDto request) {
        return CommonResponse.<Boolean>builder().data(userEmergencyContactService.addUserEmergencyContact(request, uid)).build();
    }

}
