package com.rent.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.*;
import com.rent.common.dto.product.CouponRangeProductReqDto;
import com.rent.common.dto.product.ListProductDto;
import com.rent.common.dto.product.ProductCouponDto;
import com.rent.common.dto.product.ProductShopCateReqDto;
import com.rent.exception.HzsxBizException;
import com.rent.service.marketing.LiteCouponCenterService;
import com.rent.service.order.UserOrdersQueryService;
import com.rent.service.product.ProductService;
import com.rent.util.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券小程序接口
 * @author zhaowenchao
 */
@Slf4j
@RestController
@Tag(name = "小程序优惠券模块")
@RequestMapping("/zyj-api-web/hzsx/lite/couponCenter")
@RequiredArgsConstructor
public class LiteCouponCenterController {

    private static final String ORDERED_USER_KEY = "ORDERED_USER";

    private final LiteCouponCenterService liteCouponCenterService;
    private final ProductService productService;
    private final UserOrdersQueryService userOrdersQueryService;

    @GetMapping({"/index"})
    @Operation(summary = "领券中心")
    CommonResponse<LiteCouponCenterCouponDto> index(@RequestParam("uid") String uid, @RequestParam("scene")String scene){
        Boolean isNewUser = isNewUser(uid);
        return CommonResponse.<LiteCouponCenterCouponDto>builder().data(liteCouponCenterService.index(uid,isNewUser,scene)).build();
    }

    @GetMapping({"/indexPackage"})
    @Operation(summary = "领券中心")
    CommonResponse<List<LiteCouponCenterPackageDto>> indexPackage(@RequestParam("uid") String uid){
        Boolean isNewUser = isNewUser(uid);
        return CommonResponse.<List<LiteCouponCenterPackageDto>>builder().data(liteCouponCenterService.indexPackage(uid,isNewUser)).build();
    }

    @GetMapping({"/my"})
    @Operation(summary = "我的优惠券")
    CommonResponse<List<MyCouponDto>> my(@RequestParam("uid") String uid, @RequestParam("scene")String scene){
        return CommonResponse.<List<MyCouponDto>>builder().data(liteCouponCenterService.my(uid,scene)).build();
    }

    @GetMapping({"/myUsed"})
    @Operation(summary = "我的优惠券")
    CommonResponse<List<MyCouponDto>> myUsed(@RequestParam("uid") String uid){
        return CommonResponse.<List<MyCouponDto>>builder().data(liteCouponCenterService.myUsed(uid)).build();
    }

    @GetMapping({"/myExpire"})
    @Operation(summary = "我的优惠券")
    CommonResponse<List<MyCouponDto>> myExpire(@RequestParam("uid") String uid){
        return CommonResponse.<List<MyCouponDto>>builder().data(liteCouponCenterService.myExpire(uid)).build();
    }

    @PostMapping({"/bindCoupon"})
    @Operation(summary = "领券")
    CommonResponse<String> bindCoupon(@RequestBody BindCouponReqDto reqDto){
        if(reqDto.getTemplateId()==null){
            throw new HzsxBizException("-1","未查询到优惠券，请联系客服");
        }
        Boolean isNewUser = isNewUser(reqDto.getUid());
        return CommonResponse.<String>builder().data(liteCouponCenterService.bindCoupon(reqDto.getUid(), reqDto.getPhone(), reqDto.getTemplateId(),isNewUser)).build();
    }


    @PostMapping({"/bindCouponPackage"})
    @Operation(summary = "领取大礼包")
    CommonResponse<String> bindCouponPackage(@RequestBody BindCouponPackageReqDto reqDto){
        Boolean isNewUser = isNewUser(reqDto.getUid());
        return CommonResponse.<String>builder().data(liteCouponCenterService.bindCouponPackage(reqDto.getUid(), reqDto.getPhone(), reqDto.getPackageId(),isNewUser)).build();
    }


    @GetMapping({"/getProductCoupon"})
    @Operation(summary = "获取商品可用的优惠券")
    CommonResponse<List<ProductCouponDto>> getProductCoupon(@RequestParam("productId") String productId, @RequestParam("uid") String uid){
        ProductShopCateReqDto dto = productService.selectProductCateByProductId(productId);
        if(dto==null || null == dto.getCategoryId()){
            CommonResponse.<List<ProductCouponDto>>builder().data(new ArrayList(0)).build();
        }
        String categoryId =  dto.getCategoryId().toString();
        Boolean isNewUser = isNewUser(uid);
        List<ProductCouponDto> coupons = liteCouponCenterService.getProductCoupon(categoryId,productId,dto.getShopId(),uid,isNewUser);
        return CommonResponse.<List<ProductCouponDto>>builder().data(coupons).build();
    }

    @GetMapping({"/getCouponProduct"})
    @Operation(summary = "获取优惠券可用的商品")
    CommonResponse<Page<ListProductDto>> getCouponProduct(@RequestParam("templateId") Long templateId, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize){
        CouponRangeDto range = liteCouponCenterService.getCouponRange(templateId);
        CouponRangeProductReqDto reqDto = new CouponRangeProductReqDto();
        reqDto.setType(range.getType());
        reqDto.setValue(range.getValue());
        reqDto.setPageNumber(pageNumber);
        reqDto.setPageSize(pageSize);
        return CommonResponse.<Page<ListProductDto>>builder().data(productService.getLiteProductInRange(reqDto)).build();
    }

    private Boolean isNewUser(String uid) {
        if (RedisUtil.hHasKey(ORDERED_USER_KEY, uid)) {
            return Boolean.FALSE;
        }
        Boolean hasOrder = userOrdersQueryService.checkUserHasPlaceOrder(uid);
        if (hasOrder) {
            RedisUtil.hset(ORDERED_USER_KEY, uid, "Y");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
