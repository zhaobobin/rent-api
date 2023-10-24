package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.request.MenuPageReq;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.service.user.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/list")
    public CommonResponse<Page<AuthPageResp>> queryPage(@RequestBody MenuPageReq req) {
        return CommonResponse.<Page<AuthPageResp>>builder().data(menuService.page(req)).build();
    }

    @PostMapping("/add")
    public CommonResponse<Boolean> addMenu(@RequestBody MenuPageReq req) {
        return CommonResponse.<Boolean>builder().data(menuService.add(req)).build();
    }
}
