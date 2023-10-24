package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.OpeCategoryDto;
import com.rent.common.dto.product.OpeCategoryReqDto;
import com.rent.common.enums.product.AntChainProductClassEnum;
import com.rent.service.product.OpeCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hzsx-rent-parent
 * @description: 运营前台类目
 * @author: yr
 * @create: 2020-06-08 14:50
 **/
@Slf4j
@RestController
@Tag(name = "运营后台商品类目", description = "运营后台商品类目")
@RequestMapping("/zyj-backstage-web/hzsx/category")
@RequiredArgsConstructor
public class OpeCategoryController {

    private final OpeCategoryService opeCategoryService;


    @Operation(summary = "查询蚂蚁链类目枚举")
    @GetMapping("/getAntChainCategory")
    public CommonResponse<Map<String,String>> getAntChainCategory() {
        Map<String,String> resultMap = new HashMap<>();
        for (AntChainProductClassEnum value : AntChainProductClassEnum.values()) {
            resultMap.put(value.getCode(),value.getName());
        }
        return CommonResponse.<Map<String,String>>builder().data(resultMap).build();
    }

    @Operation(summary = "运营查询一级类目查询前台分类数据 parentId=0")
    @GetMapping("/selectParentCategoryList")
    public CommonResponse<List<OpeCategoryDto>> selectParentCategoryList() {
        return CommonResponse.<List<OpeCategoryDto>>builder().data(opeCategoryService.selectParentCategoryList()).build();
    }

    @Operation(summary = "二级分页类目下分页")
    @PostMapping("/queryOpeCategoryPage")
    public CommonResponse<Page<OpeCategoryDto>> queryOpeCategoryPage(@RequestBody OpeCategoryReqDto request) {
        return CommonResponse.<Page<OpeCategoryDto>>builder().data(opeCategoryService.queryOpeCategoryPage(request)).build();
    }

    @Operation(summary = "添加前台类目数据")
    @PostMapping("/addOperateCategory")
    public CommonResponse<Integer> addOperateCategory(@RequestBody OpeCategoryDto opeCategoryDto) {
        return CommonResponse.<Integer>builder().data(opeCategoryService.addOpeCategory(opeCategoryDto)).build();
    }


    @Operation(summary = "修改前台类目")
    @PostMapping("/updateOperateCategory")
    public CommonResponse<Boolean> updateOperateCategory(@RequestBody OpeCategoryDto opeCategoryDto) {
        opeCategoryService.modifyOpeCategory(opeCategoryDto);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "删除前台类目")
    @GetMapping("/removeOperateCategory")
    public CommonResponse<Boolean> removeOperateCategory(@RequestParam("id") Integer id) {
        opeCategoryService.removeOperateCategory(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

//
//
//    @Operation(summary = "查询前台类目商品")
//    @PostMapping("/selectOperateCategoryProduct")
//    public CommonResponse<Page<OpeCategoryProductDto>> selectOperateCategoryProduct(@RequestBody OpeCategoryProductReqDto request) {
//        return this.opeCategoryService.selectOperateCategoryProduct(request);
//    }
//
//
//    @Operation(summary = "获取待添加的分类商品")
//    @GetMapping("/checkedProduct")
//    public CommonResponse<Page<ProductDto>> checkedProduct(@RequestParam("keyWord") String keyWord, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize")  Integer pageSize) {
//        return this.opeCategoryService.checkedProduct(keyWord, pageNumber, pageSize);
//    }
//
//
//    @Operation(summary = "删除类目下商品")
//    @GetMapping("/deleteProduct")
//    public CommonResponse<Boolean> deleteProduct(@RequestParam("categoryId") Integer categoryId, @RequestParam("itemId") String itemId) {
//
//        return this.opeCategoryService.deleteProduct(categoryId, itemId);
//    }
//
//
//    @Operation(summary = "多商品批量添加")
//    @PostMapping("/manyInsertCategoryProduct")
//    public CommonResponse<Boolean> manyInsertCategoryProduct(@RequestBody ManyInsertCategoryProductRequestDto model) {
//        return this.opeCategoryService.manyInsertCategoryProduct(model);
//    }


}
