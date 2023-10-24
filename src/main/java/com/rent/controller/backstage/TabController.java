package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.product.AbleProductDto;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.common.util.StringUtil;
import com.rent.exception.HzsxBizException;
import com.rent.service.product.ProductService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "标签配置-老版本")
@RequestMapping("/zyj-backstage-web/hzsx/index")
@Slf4j
@RequiredArgsConstructor
public class TabController {

    private final ProductService productService;

    @Operation(summary = "新增优惠券时-指定商品-商品筛选")
    @GetMapping("/ableProductV1")
    public CommonResponse<Page<AbleProductDto>> ableProductV1(
                                    @RequestParam(value = "keyWord",required = false) String keyWord,
                                    @RequestParam("pageNumber") Integer pageNumber,
                                    @RequestParam("pageSize") Integer pageSize) {

        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = EnumBackstageUserPlatform.OPE.getCode().equals(loginUser.getShopId()) ? null : loginUser.getShopId();
        return CommonResponse.<Page<AbleProductDto>>builder().data(productService.ableProductV1(keyWord, pageNumber, pageSize, shopId)).build();
    }


}
