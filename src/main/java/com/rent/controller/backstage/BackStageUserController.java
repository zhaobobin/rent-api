package com.rent.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.user.BackstageUserConverter;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.request.*;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.dto.backstage.resp.PageBackstageUserResp;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.user.BackstageFunctionChooseDto;
import com.rent.common.dto.user.BackstageUserDto;
import com.rent.common.dto.user.BackstageUserFunctionReqDto;
import com.rent.common.dto.user.BackstageUserReqDto;
import com.rent.common.enums.user.EnumBackstageUserStatus;
import com.rent.exception.HzsxBizException;
import com.rent.service.user.BackstageUserService;
import com.rent.util.LoginUserUtil;
import com.rent.util.MD5;
import com.rent.util.RandomUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台用户登录
 *
 * @author udo
 */
@Slf4j
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/user")
@RequiredArgsConstructor
public class BackStageUserController {

    private final BackstageUserService backstageUserService;

    @Operation(summary = "修改后台用户")
    @PostMapping("/modifyBackstageUser")
    public CommonResponse<Boolean> modifyBackstageUser(@RequestBody @Valid ModifyBackstageUserReq request) {
        BackstageUserDto backstageUserDto = new BackstageUserDto();
        backstageUserDto.setId(request.getId());
        backstageUserDto.setMobile(request.getMobile());
        backstageUserDto.setName(request.getName());
        backstageUserDto.setEmail(request.getEmail());
        backstageUserDto.setRemark(request.getRemark());
        backstageUserDto.setDepartmentId(request.getDepartmentId());
        backstageUserDto.setUpdateTime(new Date());
        backstageUserDto.setStatus(request.getStatus());
        backstageUserService.modifyBackstageUser(backstageUserDto);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "新增后台用户")
    @PostMapping("/addBackstageUser")
    public CommonResponse<Long> addBackstageUser(@RequestBody @Valid AddBackstageUserReq request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        BackstageUserReqDto reqDto = new BackstageUserReqDto();
        reqDto.setMobile(request.getMobile());
        reqDto.setType(loginUser.getType());
        BackstageUserDto backstageUserDto = backstageUserService.queryBackstageUserDetail(reqDto);
        if (backstageUserDto != null) {
            throw new HzsxBizException("-1", "该手机号码已经存在");
        }
        backstageUserDto = backstageUserService.queryBackstageUserByPhone(reqDto.getMobile());
        if (backstageUserDto != null) {
            throw new HzsxBizException("-1", "该手机号码已经存在");
        }

        backstageUserDto = new BackstageUserDto();
        backstageUserDto.setMobile(request.getMobile());
        backstageUserDto.setName(request.getName());
        backstageUserDto.setEmail(request.getEmail());
        backstageUserDto.setRemark(request.getRemark());
        backstageUserDto.setDepartmentId(request.getDepartmentId());
        backstageUserDto.setType(loginUser.getType());
        backstageUserDto.setShopId(loginUser.getShopId());
        backstageUserDto.setCreateUserName(loginUser.getName());
        backstageUserDto.setSalt(RandomUtil.randomString(4));
        backstageUserDto.setPassword(MD5.getMD5(request.getPassword() + backstageUserDto.getSalt()));
        Date now = new Date();
        backstageUserDto.setCreateTime(now);
        backstageUserDto.setUpdateTime(now);
        backstageUserDto.setStatus(EnumBackstageUserStatus.VALID);
        Long id = backstageUserService.addBackstageUser(backstageUserDto);
        return CommonResponse.<Long>builder().data(id).build();
    }

    @Operation(summary = "分页查询后台用户")
    @PostMapping("/queryBackstageUserPage")
    public CommonResponse<Page<PageBackstageUserResp>> queryBackstageUserPage(@RequestBody PageBackstageUserReq request) {
        return CommonResponse.<Page<PageBackstageUserResp>>builder().data(backstageUserService.queryBackstageUserPage(request)).build();
    }

    @Operation(summary = "查看后台用户详情")
    @PostMapping("/queryBackstageUserDetail")
    public CommonResponse<BackstageUserDto> queryBackstageUserDetail(@RequestBody BackstageUserReqDto request) {
        return CommonResponse.<BackstageUserDto>builder().data(backstageUserService.queryBackstageUserDetail(request)).build();
    }

    @Operation(summary = "删除后台用户")
    @GetMapping("/delete")
    public CommonResponse<Boolean> delete(@RequestParam("id") @Parameter(name = "id", description = "后台用户id", required = true) Long id) {
        BackstageUserReqDto request = new BackstageUserReqDto();
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setShopId(loginUser.getShopId());
        request.setId(id);
        return CommonResponse.<Boolean>builder().data(backstageUserService.deleteBackstageUser(request)).build();
    }

    @Operation(summary = "查看后台用户拥有的权限")
    @GetMapping("/authPage")
    public CommonResponse<List<AuthPageResp>> authPage(@RequestParam("id") @Parameter(name = "id", description = "后台用户id", required = true) Long id) {
        BackstageUserReqDto request = new BackstageUserReqDto();
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setShopId(loginUser.getShopId());
        request.setType(loginUser.getType());
        request.setId(id);
        return CommonResponse.<List<AuthPageResp>>builder().data(backstageUserService.authPage(request)).build();
    }

    @Operation(summary = "更新后台用户拥有的权限")
    @PostMapping("/updateAuth")
    public CommonResponse<Boolean> updateAuth(@RequestBody @Valid UpdateUserFunctionReq request) {
        BackstageUserFunctionReqDto reqDto = new BackstageUserFunctionReqDto();
        reqDto.setBackstageUserId(request.getBackstageUserId());
        List<Long> functionIds = new ArrayList<>();
        packFunctionIds(functionIds, request.getBackstageFunctionChooseDtoList());
        reqDto.setFunctionIds(functionIds);
        return CommonResponse.<Boolean>builder().data(backstageUserService.updateBackstageUserAuth(reqDto)).build();
    }


    private void packFunctionIds(List<Long> functionIds, List<BackstageFunctionChooseDto> backstageFunctionChooseDtoList) {
        if (CollectionUtils.isEmpty(backstageFunctionChooseDtoList)) {
            return;
        }
        for (BackstageFunctionChooseDto dto : backstageFunctionChooseDtoList) {
            if (dto.getChosen()) {
                functionIds.add(dto.getId());
            }
            packFunctionIds(functionIds, dto.getChild());
        }
    }
}
