        
package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.MaterialCenterCategoryDto;
import com.rent.common.dto.marketing.MaterialCenterCategoryReqDto;
import com.rent.common.dto.marketing.MaterialCenterItemDto;
import com.rent.common.dto.marketing.MaterialCenterItemReqDto;
import com.rent.service.marketing.MaterialCenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 活动素材关联控制器
 *
 * @author xiaoyao
 * @Date 2020-12-22 11:20
 */
@Tag(name = "素材中心")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/materialCenter")
@RequiredArgsConstructor
public class MaterialCenterController {

    private final MaterialCenterService materialCenterService;

    @Operation(summary = "分页查询素材分类信息")
    @PostMapping("/pageCategory")
    public CommonResponse<Page<MaterialCenterCategoryDto>> pageCategory(@RequestBody MaterialCenterCategoryReqDto dto) {
        return CommonResponse.<Page<MaterialCenterCategoryDto>>builder().data(materialCenterService.pageCategory(dto)).build();
    }

    @Operation(summary = "添加素材分类")
    @PostMapping("/addCategory")
    public CommonResponse<Long> addCategory(@RequestBody MaterialCenterCategoryDto request) {
        return CommonResponse.<Long>builder().data(materialCenterService.addCategory(request)).build();
    }

    @Operation(summary = "查看素材分类详情")
    @GetMapping("/detailCategory")
    public CommonResponse<MaterialCenterCategoryDto> detailCategory(@RequestParam("id") Long id) {
        return CommonResponse.<MaterialCenterCategoryDto>builder().data(materialCenterService.detailCategory(id)).build();
    }

    @Operation(summary = "删除素材分类")
    @GetMapping("/deleteCategory")
    public CommonResponse<Boolean> deleteCategory(@RequestParam("id") Long id) {
        materialCenterService.deleteCategory(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "分页查询素材中心素材信息")
    @PostMapping("/pageItem")
    public CommonResponse<Page<MaterialCenterItemDto>> pageItem(@RequestBody MaterialCenterItemReqDto dto) {
        return CommonResponse.<Page<MaterialCenterItemDto>>builder().data(materialCenterService.pageItem(dto)).build();
    }

    @Operation(summary = "添加素材")
    @PostMapping("/addItem")
    public CommonResponse<Long> addItem(@RequestBody MaterialCenterItemDto dto) {
        return CommonResponse.<Long>builder().data(materialCenterService.addItem(dto)).build();
    }

    @Operation(summary = "删除素材")
    @GetMapping("/detailItem")
    public CommonResponse<MaterialCenterItemDto> detailItem(@RequestParam("id")Long id) {
        return CommonResponse.<MaterialCenterItemDto>builder().data(materialCenterService.detailItem(id)).build();
    }

    @Operation(summary = "修改素材信息")
    @PostMapping("/updateItem")
    public CommonResponse<Boolean> updateItem(@RequestBody MaterialCenterItemDto dto) {
        materialCenterService.updateItem(dto);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "删除素材")
    @GetMapping("/deleteItem")
    public CommonResponse<Boolean> deleteItem(@RequestParam("id")Long id) {
        materialCenterService.deleteItem(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }
}
