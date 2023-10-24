        
package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.api.request.AddUserAddressReq;
import com.rent.common.dto.api.request.ModifyUserAddressReq;
import com.rent.common.dto.api.resp.ListUserAddressResp;
import com.rent.common.dto.user.UserAddressDto;
import com.rent.service.user.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "小程序用户收货地址模块")
@RestController
@RequiredArgsConstructor
@RequestMapping("/zyj-api-web/hzsx/userAddress")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @Operation(summary = "获取用户收货地址列表")
    @GetMapping("/getUserAddress")
    public CommonResponse<List<ListUserAddressResp>> getUserAddress(@Parameter(name = "uid",description = "用户ID",required = true) @RequestParam("uid")String uid) {
        return CommonResponse.<List<ListUserAddressResp>>builder().data(userAddressService.getUserAddress(uid)).build();
    }

    @Operation(summary = "获取用户地址信息")
    @GetMapping("/getUserAddressById")
    public CommonResponse<UserAddressDto> getUserAddressById(@Parameter(name = "id",description = "用户收货地址ID",required = true) @RequestParam(value = "id") Long id) {
        return CommonResponse.<UserAddressDto>builder().data(userAddressService.getUserAddressById(id)).build();
    }

    @Operation(summary = "新增用户地址")
    @PostMapping("/addUserAddress")
    public CommonResponse<Long> addUserAddress(@RequestBody @Valid AddUserAddressReq request) {
        return CommonResponse.<Long>builder().data(userAddressService.addUserAddress(request)).build();
    }


    @Operation(summary = "更新用户地址")
    @PostMapping("/modifyUserAddress")
    public CommonResponse<Boolean> modifyUserAddress(@RequestBody @Valid ModifyUserAddressReq request) {
        return CommonResponse.<Boolean>builder().data(userAddressService.modifyUserAddress(request)).build();
    }

    @Operation(summary = "删除用户地址")
    @GetMapping("/deleteUserAddress")
    public CommonResponse<Boolean> deleteUserAddress(@Parameter(name = "id",description = "用户收货地址ID",required = true)  @RequestParam("id") Long id) {
        return CommonResponse.<Boolean>builder().data(userAddressService.deleteUserAddress(id)).build();
    }


}
