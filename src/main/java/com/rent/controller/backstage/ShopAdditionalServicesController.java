package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.exception.HzsxBizException;
import com.rent.service.product.ShopAdditionalServicesService;
import com.rent.util.LoginUserUtil;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @program: hzsx-rent-parent
 * @description: 商家店铺增值服务
 * @author: yr
 * @create: 2020-08-04 13:48
 **/
@Slf4j
@RestController
@Tag(name = "商家店铺中心接口", description = "商家店铺中心接口")
@RequestMapping("/zyj-backstage-web/hzsx/shopAdditionalServices")
@RequiredArgsConstructor
public class ShopAdditionalServicesController {

    private final ShopAdditionalServicesService shopAdditionalServicesService;

    @Operation(summary = "店铺增值服务列表")
    @GetMapping("/selectShopAdditionalServicesList")
    public CommonResponse<Page<ShopAdditionalServicesDto>> selectShopAdditionalServicesList(String name, Integer pageNumber, Integer pageSize){
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        return CommonResponse.<Page<ShopAdditionalServicesDto>>builder().data(shopAdditionalServicesService.selectShopAdditionalServicesList(name, shopId,  pageNumber, pageSize)).build();
    }


    @Operation(summary = "新增/修改商家增值服务")
    @PostMapping("/insertShopAdditionServices")
    public CommonResponse<Boolean> insertShopAdditionServices(@RequestBody ShopAdditionalServicesDto shopAdditionalServices){
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        shopAdditionalServices.setShopId(loginUser.getShopId());
        shopAdditionalServicesService.saveOrUpdateShopAdditionServices(shopAdditionalServices);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "删除增值服务")
    @GetMapping("/deletShopAdditionService")
    public CommonResponse<Boolean> deleteShopAdditionService(@RequestParam("id") Integer id){
        shopAdditionalServicesService.deleteShopAdditionService(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();

    }

}
