        
package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.request.BackstageQueryUserCouponReq;
import com.rent.common.dto.backstage.resp.BackstageQueryUserCouponResp;
import com.rent.service.marketing.LiteUserCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Tag(name = "用户领取的优惠券")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/userCoupon")
@RequiredArgsConstructor
public class UserCouponController {

    private final LiteUserCouponService liteUserCouponService;

    @Operation(summary = "分页查询某个优惠券的用户领取情况")
    @PostMapping("/queryPage")
    public CommonResponse<Page<BackstageQueryUserCouponResp>> queryPage(@RequestBody @Valid BackstageQueryUserCouponReq request) {
        return CommonResponse.<Page<BackstageQueryUserCouponResp>>builder().data(liteUserCouponService.backstageQueryPage(request)).build();
    }

}
