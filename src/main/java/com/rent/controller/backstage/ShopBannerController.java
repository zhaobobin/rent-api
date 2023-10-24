package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.product.OpeIndexShopBannerDto;
import com.rent.common.dto.product.OpeIndexShopBannerReqDto;
import com.rent.exception.HzsxBizException;
import com.rent.service.product.OpeIndexShopBannerService;
import com.rent.util.LoginUserUtil;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "店铺营销图配置", description = "店铺营销图配置")
@RequestMapping("/zyj-backstage-web/hzsx/shopbanner")
@RequiredArgsConstructor
public class ShopBannerController {

    private final OpeIndexShopBannerService opeIndexShopBannerService;

    @Operation(summary = "分页查询商家详情轮播配置")
    @PostMapping("/queryOpeIndexShopBannerPage")
    public CommonResponse<Page<OpeIndexShopBannerDto>> queryOpeIndexShopBannerPage(@RequestBody OpeIndexShopBannerReqDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        request.setShopId(shopId);
        Page<OpeIndexShopBannerDto> response = opeIndexShopBannerService.queryOpeIndexShopBannerPage(request);
        return CommonResponse.<Page<OpeIndexShopBannerDto>>builder().data(response).build();
    }

    @Operation(summary = "新增商家详情轮播配置")
    @PostMapping("/addOpeIndexShopBanner")
    public CommonResponse<Boolean> addOpeIndexShopBanner(@RequestBody OpeIndexShopBannerDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        request.setShopId(shopId);
        opeIndexShopBannerService.addOpeIndexShopBanner(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "更新商家详情轮播配置")
    @PostMapping("/modifyOpeIndexShopBanner")
    public CommonResponse<Boolean> modifyOpeIndexShopBanner(@RequestBody OpeIndexShopBannerDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        request.setShopId(shopId);
        opeIndexShopBannerService.modifyOpeIndexShopBanner(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "删除商家详情轮播配置")
    @GetMapping("/deleteOpeIndexShopBanner")
    public CommonResponse<Boolean> deleteOpeIndexShopBanner(@Parameter(name = "id",description = "营销图配置ID",required = true) @RequestParam("id") Long id) {
        opeIndexShopBannerService.deleteOpeIndexShopBanner(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }
}
