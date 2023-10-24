package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.OnShelfProductDto;
import com.rent.common.dto.backstage.OnShelfProductReqDto;
import com.rent.common.dto.backstage.UpdateProductHiddenReqDto;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.product.*;
import com.rent.common.enums.product.ProductStatus;
import com.rent.common.util.AsyncUtil;
import com.rent.exception.HzsxBizException;
import com.rent.service.export.ProductExportService;
import com.rent.service.product.ProductAuditLogService;
import com.rent.service.product.ProductExtraService;
import com.rent.service.product.ProductService;
import com.rent.service.product.ProductStatisticsService;
import com.rent.util.LoginUserUtil;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: hzsx-rent-parent
 * @description: 商家商品管理模块
 * @author: yr
 * @create: 2020-06-29 14:38
 **/
@Slf4j
@RestController
@Tag(name = "商家后台商品模块", description = "商品模块")
@RequestMapping("/zyj-backstage-web/hzsx/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductStatisticsService productStatisticsService;
    private final ProductService productService;
    private final ProductAuditLogService productAuditLogService;
    private final ProductExtraService productExtraService;
    private final ProductExportService productExportService;

    @Operation(summary = "更改成下架状态")
    @GetMapping("/busUpdateProductByDown")
    public CommonResponse<Boolean> busUpdateProductByDown(Integer id) {
        productService.busUpdateProduct(id, ProductStatus.PUT_ON_DEPOT.getCode(), ProductStatus.NO_STATUS.getCode());
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "更改上架状态")
    @GetMapping("/busUpdateProductByUp")
    public CommonResponse<Boolean> busUpdateProductByUp(Integer id) {
        productService.busUpdateProduct(id, ProductStatus.PUT_ON_SHELF.getCode(), ProductStatus.STATUS.getCode());
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "商品后台删除")
    @GetMapping("/busUpdateProductByRecycleDel")
    public CommonResponse<Boolean> busUpdateProductByRecycleDel(Integer id) {
        productService.busUpdateProductByRecycleDel(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "商家后台新增商品")
    @PostMapping("/busInsertProduct")
    public CommonResponse<Boolean> busInsertProduct(@RequestBody @Valid ShopProductAddReqDto model) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        model.setShopId(shopId);
        productService.busInsertProduct(model);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "商家后台根据条件分页查询产品")
    @PostMapping("/selectBusinessPrdouct")
    public CommonResponse<Page<ProductDto>> selectBusinessProduct(@RequestBody ShopProductSerachReqDto model) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        model.setShopId(shopId);
        return CommonResponse.<Page<ProductDto>>builder().data(productService.selectBusinessProduct(model)).build();
    }


    @Operation(summary = "商家后台根据条件查询tab数据统计")
    @PostMapping("/selectBusinessPrdouctCounts")
    public CommonResponse<ProductCountsDto> selectBusinessPrdouctCounts(@RequestBody ShopProductSerachReqDto model) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        model.setShopId(shopId);
        return CommonResponse.<ProductCountsDto>builder().data(productStatisticsService.selectBusinessPrdouctCounts(model)).build();
    }

    @Operation(summary = "商家后台编辑商品时查询")
    @GetMapping("/selectProductEdit")
    public CommonResponse<ShopProductAddReqDto> selectProductEdit(Integer id) {
        return CommonResponse.<ShopProductAddReqDto>builder().data(productService.selectProductEdit(id)).build();
    }

    @Operation(summary = "商家后台修改商品")
    @PostMapping("/updateBusProduct")
    public CommonResponse<Boolean> updateBusProduct(@RequestBody ShopProductAddReqDto model) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        model.setShopId(shopId);
        productService.updateBusProduct(model);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @PostMapping("/queryOnShelfProduct")
    public CommonResponse<Page<OnShelfProductDto>> queryOnShelfProduct(@RequestBody OnShelfProductReqDto request) {
        Page<OnShelfProductDto> resp = productService.queryOnShelfProduct(request);
        return CommonResponse.<Page<OnShelfProductDto>>builder().data(resp).build();
    }


    @PostMapping("/updateProductHidden")
    public CommonResponse<Boolean> updateProductHidden(@RequestBody UpdateProductHiddenReqDto request) {
        productService.updateProductHidden(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @PostMapping("/addScore")
    public CommonResponse<Boolean> addScore(@RequestBody OnShelfProductReqDto request) {
        productService.addScore(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @PostMapping("/selectExamineProductAuditLog")
    public CommonResponse<Page<ProductAuditLogDto>> selectExamineProductAuditLog(@RequestBody ProductAuditLogReqDto request) {
        return CommonResponse.<Page<ProductAuditLogDto>>builder().data(productAuditLogService.queryProductAuditLogPage(request)).build();
    }

    @GetMapping("/busCopyProduct")
    public CommonResponse<Boolean> busCopyProduct(@RequestParam("productId") String productId) {
        productExtraService.busCopyProduct(productId);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @PostMapping("/exportBusinessPrdouct")
    public CommonResponse<Boolean> exportBusinessPrdouct(@RequestBody ShopProductSerachReqDto model) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        String shopId = loginUser.getShopId();
        model.setShopId(shopId);
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(loginUser);
            productExportService.exportBusinessProduct(model);
        });
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

}
