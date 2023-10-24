package com.rent.controller.backstage;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.CouponTemplateConstant;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.marketing.*;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.user.UidAndPhone;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.exception.HzsxBizException;
import com.rent.service.marketing.LiteCouponTemplateService;
import com.rent.service.product.ShopService;
import com.rent.service.user.UserCertificationService;
import com.rent.util.AppParamUtil;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "优惠券管理")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/liteCouponTemplate")
@RequiredArgsConstructor
public class LiteCouponTemplateController {

    private final LiteCouponTemplateService liteCouponTemplateService;
    private final UserCertificationService userCertificationService;
    private final ShopService shopService;

    @Operation(summary = "新增优惠券模版")
    @PostMapping("/add")
    public CommonResponse<Long> addCouponTemplate(@RequestBody AddOrUpdateCouponTemplateDto request) {
        packAddOrUpdateCouponTemplateDto(request);
        return CommonResponse.<Long>builder().data(liteCouponTemplateService.addCouponTemplate(request)).build();
    }

    @Operation(summary = "编辑优惠券")
    @PostMapping("/modify")
    public CommonResponse<Boolean> modifyCouponTemplate(@RequestBody AddOrUpdateCouponTemplateDto request) {
        packAddOrUpdateCouponTemplateDto(request);
        liteCouponTemplateService.modifyCouponTemplate(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "编辑优惠券页面数据获取")
    @GetMapping("/getUpdatePageData")
    public CommonResponse<AddOrUpdateCouponTemplateDto> getCouponTemplateUpdatePageData(@RequestParam("id")Long id) {
        return CommonResponse.<AddOrUpdateCouponTemplateDto>builder().data(liteCouponTemplateService.getCouponTemplateUpdatePageData(id)).build();
    }

    @Operation(summary = "优惠券详情页面数据获取")
    @GetMapping("/getDetail")
    public CommonResponse<CouponTemplatePageDto> getCouponTemplateDetail(@RequestParam("id")Long id) {
        return CommonResponse.<CouponTemplatePageDto>builder().data(liteCouponTemplateService.getCouponTemplateDetail(id)).build();
    }

    @Operation(summary = "删除优惠券")
    @GetMapping("/delete")
    public CommonResponse<Boolean> deleteCouponTemplate(@RequestParam("id")Long id) {
        liteCouponTemplateService.deleteCouponTemplate(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "分页获取优惠券")
    @PostMapping("/queryPage")
    public CommonResponse<Page<CouponTemplatePageListDto>> queryCouponTemplatePage(@RequestBody CouponTemplateReqDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        request.setSourceShopId(loginUser.getShopId());
        return CommonResponse.<Page<CouponTemplatePageListDto>>builder().data(liteCouponTemplateService.queryCouponTemplatePage(request)).build();
    }

    @Operation(summary = "导出券码券 券码")
    @GetMapping("/exportEntityNum")
    public CommonResponse<String> exportEntityNum(@RequestParam("id") Long templateId) {
        return CommonResponse.<String>builder().data(liteCouponTemplateService.exportEntityNum(templateId)).build();
    }


    @Operation(summary = "获取到可以分配给大礼包的优惠券")
    @PostMapping("/getAssignAbleTemplate")
    public CommonResponse<Page<CouponTemplatePageListDto>> getAssignAbleTemplate(@RequestBody CouponTemplateReqDto request) {
        request.setSourceShopId(EnumBackstageUserPlatform.OPE.getCode());
        return CommonResponse.<Page<CouponTemplatePageListDto>>builder().data(liteCouponTemplateService.getAssignAbleTemplate(request)).build();
    }

    @Operation(summary = "给优惠券添加领取链接")
    @PostMapping("/addBindUrl")
    public CommonResponse<Boolean> addBindUrl(@RequestBody CouponAddBindUrlReqDto request) {
        liteCouponTemplateService.addBindUrl(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    private void packAddOrUpdateCouponTemplateDto(AddOrUpdateCouponTemplateDto request){
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if(EnumBackstageUserPlatform.OPE.getCode().equals(loginUser.getShopId())){
            request.setSourceShopId(loginUser.getShopId());
            request.setSourceShopName(loginUser.getShopId());
        }else{
            ShopDto shopDto = shopService.queryByShopId(loginUser.getShopId());
            request.setSourceShopName(shopDto.getName());
            request.setSourceShopId(shopDto.getShopId());
        }
        if(CouponTemplateConstant.RANG_USER_ALL.equals(request.getUserRange())){
            request.setForNew(CouponTemplateConstant.FOR_NEW_F);
        }
        if(CouponTemplateConstant.RANG_USER_NEW.equals(request.getUserRange())){
            request.setForNew(CouponTemplateConstant.FOR_NEW_T);
        }
        if(CouponTemplateConstant.RANG_USER_PART.equals(request.getUserRange())){
            request.setForNew(CouponTemplateConstant.FOR_NEW_F);
        }
        if(CollectionUtil.isNotEmpty(request.getPhones())){
            request.setForNew(CouponTemplateConstant.FOR_NEW_F);
            List<UidAndPhone> uidAndPhones = userCertificationService.getUidAndPhoneSet(request.getPhones());
            if(CollectionUtil.isEmpty(uidAndPhones)){
                throw new HzsxBizException("-1","根据上传的手机号码查询到用户信息为空");
            }
            request.setUidAndPhoneList(uidAndPhones);
        }
        request.setChannelId(AppParamUtil.getChannelId());
        request.setPhones(null);
    }
}
