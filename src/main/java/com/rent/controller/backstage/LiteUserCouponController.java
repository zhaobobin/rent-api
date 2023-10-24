        
package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.UserCouponDto;
import com.rent.common.dto.marketing.UserCouponReqDto;
import com.rent.service.marketing.LiteUserCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠券模版控制器
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Tag(name = "用户领取的优惠券")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/liteUserCoupon")
@RequiredArgsConstructor
public class LiteUserCouponController {

    private final LiteUserCouponService liteUserCouponService;

    @Operation(summary = "分页获取用户领取的优惠券")
    @PostMapping("/queryPage")
    public CommonResponse<Page<UserCouponDto>> queryPage(@RequestBody UserCouponReqDto request) {
        return CommonResponse.<Page<UserCouponDto>>builder().data(liteUserCouponService.queryPage(request)).build();
    }

}
