package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.DepartmentFunctionChooseReqDto;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.user.BackstageDepartmentDto;
import com.rent.common.dto.user.BackstageDepartmentReqDto;
import com.rent.common.dto.user.BackstageFunctionChooseDto;
import com.rent.common.dto.user.DepartmentFunctionReqDto;
import com.rent.service.user.BackstageDepartmentService;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门管理
 *
 * @author udo
 */
@Slf4j
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/department")
@RequiredArgsConstructor
public class BackStageDepartmentController {

    private final BackstageDepartmentService backstageDepartmentService;

    @PostMapping("/add")
    public CommonResponse<Boolean> add(@RequestBody BackstageDepartmentDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setPlatform(loginUser.getType());
        request.setShopId(loginUser.getShopId());
        Date now = new Date();
        request.setCreateTime(now);
        request.setUpdateTime(now);
        backstageDepartmentService.addBackstageDepartment(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @PostMapping("/queryPage")
    public CommonResponse<Page<BackstageDepartmentDto>> queryPage(@RequestBody BackstageDepartmentReqDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setPlatform(loginUser.getType());
        request.setShopId(loginUser.getShopId());
        return CommonResponse.<Page<BackstageDepartmentDto>>builder().data(backstageDepartmentService.queryBackstageDepartmentPage(request)).build();
    }

    @GetMapping("/queryAll")
    public CommonResponse<List<BackstageDepartmentDto>> queryAll() {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        BackstageDepartmentReqDto request = new BackstageDepartmentReqDto();
        request.setPlatform(loginUser.getType());
        request.setShopId(loginUser.getShopId());
        return CommonResponse.<List<BackstageDepartmentDto>>builder().data(backstageDepartmentService.queryBackstageDepartment(request)).build();
    }

    @GetMapping("/queryOne")
    public CommonResponse<BackstageDepartmentDto> queryOne(@RequestParam Long id) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        BackstageDepartmentReqDto request = new BackstageDepartmentReqDto();
        request.setPlatform(loginUser.getType());
        request.setShopId(loginUser.getShopId());
        request.setId(id);
        return CommonResponse.<BackstageDepartmentDto>builder().data(backstageDepartmentService.queryBackstageDepartmentDetail(request)).build();
    }

    @PostMapping("/update")
    public CommonResponse<Boolean> update(@RequestBody BackstageDepartmentDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setPlatform(loginUser.getType());
        request.setShopId(loginUser.getShopId());
        Date now = new Date();
        request.setUpdateTime(now);
        return CommonResponse.<Boolean>builder().data(backstageDepartmentService.modifyBackstageDepartment(request)).build();
    }

    @GetMapping("/delete")
    public CommonResponse<Boolean> delete(@RequestParam Long id) {
        BackstageDepartmentDto request = new BackstageDepartmentDto();
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setPlatform(loginUser.getType());
        request.setShopId(loginUser.getShopId());
        request.setId(id);
        return CommonResponse.<Boolean>builder().data(backstageDepartmentService.deleteBackstageDepartment(request)).build();
    }

    @GetMapping("/authPage")
    public CommonResponse<List<AuthPageResp>> authPage(@RequestParam("departmentId") Long departmentId) {
        BackstageDepartmentReqDto request = new BackstageDepartmentReqDto();
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setPlatform(loginUser.getType());
        request.setShopId(loginUser.getShopId());
        request.setId(departmentId);
        return CommonResponse.<List<AuthPageResp>>builder().data(backstageDepartmentService.authPage(request)).build();
    }

    @PostMapping("/updateAuth")
    public CommonResponse<Boolean> updateAuth(@RequestBody DepartmentFunctionChooseReqDto request) {
        DepartmentFunctionReqDto reqDto = new DepartmentFunctionReqDto();
        reqDto.setDepartmentId(request.getDepartmentId());
        List<Long> functionIds = new ArrayList<>();
        packFunctionIds(functionIds, request.getBackstageFunctionChooseDtoList());
        reqDto.setFunctionIds(functionIds);
        return CommonResponse.<Boolean>builder().data(backstageDepartmentService.updateDepartmentFunction(reqDto)).build();
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
