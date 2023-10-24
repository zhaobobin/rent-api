package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.product.ShopGiveBackAddressesDto;
import com.rent.exception.HzsxBizException;
import com.rent.service.product.ShopGiveBackAddressesService;
import com.rent.util.LoginUserUtil;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @program: hzsx-rent-parent
 * @description: 商家归还地址管理
 * @author: yr
 * @create: 2020-08-04 11:47
 **/
@Slf4j
@RestController
@Tag(name = "商家归还地址管理", description = "商家归还地址管理")
@RequestMapping("/zyj-backstage-web/hzsx/shopAddress")
@RequiredArgsConstructor
public class ShopAddressController {

    private final ShopGiveBackAddressesService shopGiveBackAddressesService;

    @Operation(summary = "更新归还地址")
    @PostMapping("/updateShopGiveBackAddressById")
    public CommonResponse<Boolean> updateShopGiveBackAddressById(@RequestBody ShopGiveBackAddressesDto shopGiveBackAddresses) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        shopGiveBackAddresses.setShopId(shopId);
        shopGiveBackAddressesService.modifyShopGiveBackAddresses(shopGiveBackAddresses);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();

    }


    @Operation(summary = "商家后台删除租用归还地址")
    @GetMapping("/delShopGiveBackAddressById")
    public CommonResponse<Boolean> delShopGiveBackAddressById(Integer id) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        shopGiveBackAddressesService.delShopGiveBackAddressById(id,loginUser.getShopId());
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "商家后台新增归还地址")
    @PostMapping("/saveShopGiveBackAddress")
    public CommonResponse<Boolean> saveShopGiveBackAddress(@RequestBody ShopGiveBackAddressesDto shopGiveBackAddresses) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        shopGiveBackAddresses.setShopId(shopId);
        shopGiveBackAddressesService.addShopGiveBackAddresses(shopGiveBackAddresses);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


}
