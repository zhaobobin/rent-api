package com.rent.controller.api;

/**
 * @program: lang
 * @description:
 * @author: yr
 * @create: 2020-07-15 17:43
 **/

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.product.CategoryProductResp;
import com.rent.common.dto.product.LiteCategoryDto;
import com.rent.service.product.OpeCategoryService;
import com.rent.util.AppParamUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台类目小程序接口
 * @author zhaowenchao
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/zyj-api-web/hzsx/aliPay/category")
@Tag(name = "小程序分类页面")
public class CategoryController {

    private final OpeCategoryService opeCategoryService;

    @Operation(summary = "查看类目信息")
    @GetMapping("/listLite")
    public CommonResponse<List<LiteCategoryDto>> listLite(@RequestParam("parentId") @Parameter(name = "position",description = "父级类目ID，顶级为0",required = true)Integer parentId){
        List<LiteCategoryDto> liteCategoryDtoList = this.opeCategoryService.liteCategory(parentId, AppParamUtil.getChannelId());
        return CommonResponse.<List<LiteCategoryDto>>builder().data(liteCategoryDtoList).build();
    }

    @Operation(summary = "查看类目下商品信息")
    @GetMapping("/listLiteCategoryProduct")
    public CommonResponse<Page<CategoryProductResp>> listLiteCategoryProduct(
            @RequestParam("categoryId")  @Parameter(name = "position",description = "类目ID",required = true)Integer categoryId,
            @RequestParam("pageNum") @Parameter(name = "pageNum",description = "页码",required = true)Integer pageNum,
            @RequestParam("pageSize") @Parameter(name = "pageSize",description = "页面大小",required = true)Integer pageSize){
        return CommonResponse.<Page<CategoryProductResp>>builder().data(opeCategoryService.listProduct(categoryId, pageNum, pageSize)).build();
    }




}
