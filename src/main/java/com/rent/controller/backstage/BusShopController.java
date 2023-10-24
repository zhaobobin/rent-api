package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.components.dto.ShopContractDto;
import com.rent.common.dto.product.*;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.exception.HzsxBizException;
import com.rent.service.components.SxService;
import com.rent.service.product.ShopContractService;
import com.rent.service.product.ShopGiveBackAddressesService;
import com.rent.service.product.ShopService;
import com.rent.util.LoginUserUtil;
import com.rent.common.util.OSSFileUtils;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @program: hzsx-rent-parent
 * @description:商家店铺信息接口
 * @author: yr
 * @create: 2020-08-03 14:33
 **/
@Slf4j
@RestController
@Tag(name = "商家店铺中心接口", description = "商家店铺中心接口")
@RequestMapping("/zyj-backstage-web/hzsx/busShop")
@RequiredArgsConstructor
public class BusShopController {

    private final ShopService shopService;
    private final ShopContractService shopContractService;
    private final ShopGiveBackAddressesService shopGiveBackAddressesService;
    private final SxService sxService;
    private final OSSFileUtils ossFileUtils;

    @Operation(summary = "商家店铺详情")
    @GetMapping("/selectBusShopInfo")
    public CommonResponse<BusShopDetailDto> selectBusShopInfo(String shopId) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        if(!EnumBackstageUserPlatform.OPE.getCode().equals(loginUser.getShopId())){
            shopId = loginUser.getShopId();
        }
        return CommonResponse.<BusShopDetailDto>builder().data(shopService.selectBusShopInfo(shopId)).build();
    }


    @Operation(summary = "修改/新增 商家店铺企业资质信息")
    @PostMapping("/updateShopAndEnterpriseInfo")
    public CommonResponse<Boolean> updateShopAndEnterpriseInfo(@RequestBody BusShopDetailDto busShopDetailDto) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        ShopDto shop = busShopDetailDto.getShop();
        shop.setShopId(shopId);
        EnterpriseInfoDto enterpriseInfoDto = busShopDetailDto.getEnterpriseInfoDto();
        ShopEnterpriseInfosDto shopEnterpriseInfos = enterpriseInfoDto.getShopEnterpriseInfos();
        shopEnterpriseInfos.setShopId(shopId);
        Boolean cert = sxService.cert(shopEnterpriseInfos.getRealname(), shopEnterpriseInfos.getIdentity());
        if(!cert){
            throw new HzsxBizException("999999", "实名校验失败");
        }
        shopService.updateShopAndEnterpriseInfo(busShopDetailDto);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "根据店铺Id查询新增商品的规则模版和归还地址")
    @GetMapping("/selectShopRuleAndGiveBackByShopId")
    public CommonResponse<List<ShopGiveBackAddressesDto>> selectShopGiveBackByShopId() {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        return CommonResponse.<List<ShopGiveBackAddressesDto>>builder().data(shopGiveBackAddressesService.selectShopGiveBackByShopId(shopId)).build();
    }

    @Operation(summary = "文件上传接口")
    @PostMapping(value = "/doUpLoadwebp", consumes = "multipart/form-data")
    public CommonResponse<String> doUpLoadwebp(@RequestPart(value = "file") MultipartFile file) {
        return CommonResponse.<String>builder().data(ossFileUtils.uploadByMultipartFile(file,"backstage")).build();
    }

    @Operation(summary = "签署商家入驻协议")
    @GetMapping(value = "/signShopContract")
    public CommonResponse<String> signShopContract() {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        shopContractService.signShopContract(shopId);
        return CommonResponse.<String>builder().data("").build();
    }

    @Operation(summary = "获取商家入驻协议内容")
    @GetMapping(value = "/getShopContractData")
    public CommonResponse<ShopContractDto> getShopContractData() {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        ShopContractDto dto = shopContractService.getShopContractData(shopId);
        return CommonResponse.<ShopContractDto>builder().data(dto).build();
    }


}
