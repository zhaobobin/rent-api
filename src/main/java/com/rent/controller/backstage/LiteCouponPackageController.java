        
package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.marketing.CouponPackageDto;
import com.rent.common.dto.marketing.CouponPackageReqDto;
import com.rent.service.marketing.LiteCouponPackageService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 优惠券大礼包控制器
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Tag(name = "大礼包")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/liteCouponPackage")
@RequiredArgsConstructor
public class LiteCouponPackageController {

    private final LiteCouponPackageService liteCouponPackageService;

    @Operation(summary = "新增优惠券大礼包")
    @PostMapping("/add")
    public CommonResponse<Long> addCouponPackage(@RequestBody CouponPackageDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setSourceShopId(loginUser.getShopId());
        request.setSourceShopName(loginUser.getShopId());
        return CommonResponse.<Long>builder().data(liteCouponPackageService.addCouponPackage(request)).build();
    }

    @Operation(summary = "删除一个大礼包")
    @GetMapping("/delete")
    public CommonResponse<Boolean> deleteCouponPackage(@RequestParam("id") Long id) {
        liteCouponPackageService.deleteCouponPackage(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }
    @Operation(summary = "编辑优惠券大礼包页面")
    @GetMapping("/getUpdatePageData")
    public CommonResponse<CouponPackageDto> updateCouponPackagePageData(@RequestParam("id") Long id) {
        return CommonResponse.<CouponPackageDto>builder().data(liteCouponPackageService.updateCouponPackagePageData(id)).build();
    }

    @Operation(summary = "更新优惠券大礼包")
    @PostMapping("/modify")
    public CommonResponse<Boolean> modifyCouponPackage(@RequestBody CouponPackageDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setSourceShopId(loginUser.getShopId());
        request.setSourceShopName(loginUser.getShopId());
        liteCouponPackageService.modifyCouponPackage(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "分页查询优惠券大礼包")
    @PostMapping("/queryPage")
    public CommonResponse<Page<CouponPackageDto>> queryCouponPackagePage(@RequestBody CouponPackageReqDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setSourceShopId(loginUser.getShopId());
        request.setSourceShopName(loginUser.getShopId());
        return CommonResponse.<Page<CouponPackageDto>>builder().data(liteCouponPackageService.queryCouponPackagePage(request)).build();
    }
}
