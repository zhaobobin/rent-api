package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.UpdateExamineProductDto;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.product.CategoryNodeResponse;
import com.rent.common.dto.product.ExamineProductQueryReqDto;
import com.rent.common.dto.product.ExamineProductResponse;
import com.rent.common.dto.product.ProductDto;
import com.rent.common.enums.product.EnumProductAuditState;
import com.rent.exception.HzsxBizException;
import com.rent.service.product.CategoryService;
import com.rent.service.product.ExamineProductService;
import com.rent.service.product.ProductService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: hzsx-rent-parent
 * @description:商品审核模块
 * @author: yr
 * @create: 2020-06-24 11:36
 **/
@Slf4j
@RestController
@Tag(name = "运营商品模块", description = "商品审核模块")
@RequestMapping("/zyj-backstage-web/hzsx/examineProduct")
@RequiredArgsConstructor
public class ExamineProductController {

    private final CategoryService categoryService;
    private final ExamineProductService examineProductService;
    private final ProductService productService;

    @Operation(summary = "商品审核列表")
    @PostMapping("/selectExaminePoroductList")
    public CommonResponse<Page<ProductDto>> selectExaminePoroductList(@RequestBody ExamineProductQueryReqDto model) {
        return CommonResponse.<Page<ProductDto>>builder().data(examineProductService.selectExaminePoroductList(model)).build();
    }

    @Operation(summary = "商品审核详细")
    @GetMapping("/selectExamineProductDetail")
    public CommonResponse<ExamineProductResponse> selectExamineProductDetail(@RequestParam("productId") String productId) {
        return CommonResponse.<ExamineProductResponse>builder().data(examineProductService.selectExamineProductDetailV1(productId)).build();
    }



    @Operation(summary = "商品审核确认")
    @GetMapping("/examineProductConfirm")
    public CommonResponse<Boolean> examineProductConfirm(@RequestParam("id") Integer id,
                                                         @RequestParam("auditState") Integer auditState,
                                                         @RequestParam("reason") String reason,
                                                         @RequestParam("isOnLine") Integer isOnLine) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if(null  == loginUser){
            throw new HzsxBizException("-1","获取用户信息异常");

        }
        String operator = loginUser.getMobile();
        examineProductService.examineProductConfirm(id, EnumProductAuditState.find(auditState), reason, operator,isOnLine);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "商品信息修改")
    @PostMapping("/updateExamineProduct")
    public CommonResponse<Boolean> updateExamineProduct(@RequestBody UpdateExamineProductDto model) {
        productService.updateExamineProduct(model);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "查询后台类目")
    @GetMapping("/findCategories")
    public CommonResponse<List<CategoryNodeResponse>> findCategories() {
        return CommonResponse.<List<CategoryNodeResponse>>builder().data(categoryService.findCategories()).build();
    }

    @Operation(summary = "查询商品编辑信息")
    @GetMapping("/selectExamineProductEdit")
    public CommonResponse<Map<String, Object>> selectExamineProductEdit(String productId) {
        return CommonResponse.<Map<String, Object>>builder().data(productService.selectExamineProductEdit(productId)).build();
    }
}
