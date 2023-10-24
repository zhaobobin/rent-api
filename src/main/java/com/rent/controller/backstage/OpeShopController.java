package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.product.ToExamineListRequestModel;
import com.rent.common.enums.product.ShopStatus;
import com.rent.service.product.ShopService;
import com.rent.service.user.BackstageUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2020-06-22 14:48
 **/
@Slf4j
@RestController
@Tag(name = "运营店铺模块", description = "店铺审核模块")
@RequestMapping("/zyj-backstage-web/hzsx/opeShop")
@RequiredArgsConstructor
public class OpeShopController {

    private final ShopService shopService;
    private final BackstageUserService backstageUserService;

    @Operation(summary = "店铺审核列表")
    @PostMapping("/toShopExamineList")
    public CommonResponse<Page<ShopDto>> toShopExamineList(@RequestBody ToExamineListRequestModel toExamineListRequestModel){
        return CommonResponse.<Page<ShopDto>>builder().data(shopService.toShopExamineList(toExamineListRequestModel)).build();
    }


    @Operation(summary = "店铺审核确认")
    @GetMapping("/toShopExamineConform")
    public CommonResponse<Boolean> toShopExamineConfirm(@Parameter(description = "id",required = true) @RequestParam(value = "id")Integer id,
                                                        @Parameter(description = "审核状态",required = true) @RequestParam(value = "status")Integer status,
                                                        @Parameter(description = "审核理由",required = true) @RequestParam(value = "reason")String reason,
                                                        @Parameter(description = "是否冻结 0未冻结 1冻结",required = true) @RequestParam(value = "isLocked")Integer isLocked){
        String shopId = shopService.toShopExamineConfirm(id, status, reason,isLocked);
        if(ShopStatus.SHOP_STATUS_AUDIT_PASS.getCode().equals(status)){
            backstageUserService.updateDepartmentAfterShopExaminePass(shopId);
        }
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "冻结(解冻)店铺信息")
    @GetMapping("/lockedShop")
    public CommonResponse<Boolean> lockedShop(@Parameter(description = "店铺Id",required = true)@RequestParam(value = "shopId")String shopId,
                                              @Parameter(description = "是否冻结 0未冻结 1冻结",required = true)@RequestParam(value = "status")Integer status){
        shopService.lockedShop(shopId, status);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "查询所有店铺id和名称")
    @GetMapping("/listAllShop")
    public CommonResponse<List<ShopDto>> listAllShop(@Parameter(description = "店铺名称",required = false)@RequestParam(value = "shopName",required = false) String shopName){
        List<ShopDto> dtoList = shopService.listAllShop(shopName);
        return CommonResponse.<List<ShopDto>>builder().data(dtoList).build();
    }
}
