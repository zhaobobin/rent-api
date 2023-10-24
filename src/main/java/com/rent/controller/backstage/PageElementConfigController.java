        
package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.PageElementConfigRequest;
import com.rent.common.dto.marketing.PageElementConfigResp;
import com.rent.common.enums.marketing.PageElementConfigTypeEnum;
import com.rent.service.marketing.PageElementConfigService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhaowenchao
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/pageElementConfig")
@RequiredArgsConstructor
public class PageElementConfigController {

    private final PageElementConfigService pageElementConfigService;

    @Operation(summary = "新增")
    @PostMapping("/add")
    public CommonResponse<Long> add(@RequestBody PageElementConfigRequest request) {
        return CommonResponse.<Long>builder().data(pageElementConfigService.add(request)).build();
    }

    @Operation(summary = "删除")
    @GetMapping("/delete")
    public CommonResponse<Long> delete(@RequestParam("id") Long id) {
        return CommonResponse.<Long>builder().data(pageElementConfigService.delete(id)).build();
    }

    @Operation(summary = "修改")
    @PostMapping("/update")
    public CommonResponse<Long> update(@RequestBody PageElementConfigRequest request) {
        return CommonResponse.<Long>builder().data(pageElementConfigService.update(request)).build();
    }

    @Operation(summary = "查询列表 首页")
    @PostMapping("/listIndex")
    public CommonResponse<Map<String, List<PageElementConfigResp>>> listIndex(@RequestBody PageElementConfigRequest request) {
        Map<String, List<PageElementConfigResp>> map = new HashMap<>();
        packOneTypeData(PageElementConfigTypeEnum.INDEX_BANNER,request,map);
        packOneTypeData(PageElementConfigTypeEnum.SPECIAL_AREA_MAIN,request,map);
        packOneTypeData(PageElementConfigTypeEnum.SPECIAL_AREA_SUB,request,map);
        packOneTypeData(PageElementConfigTypeEnum.SPECIAL_TITLE_MAIN,request,map);
        packOneTypeData(PageElementConfigTypeEnum.SPECIAL_TITLE_SUB,request,map);
        packOneTypeData(PageElementConfigTypeEnum.ICON_AREA,request,map);
        return CommonResponse.<Map<String, List<PageElementConfigResp>>>builder().data(map).build();
    }

    private void packOneTypeData(PageElementConfigTypeEnum typeEnum,
                                 PageElementConfigRequest request,
                                 Map<String, List<PageElementConfigResp>> map){
        String key = typeEnum.getCode();
        request.setType(typeEnum);
        map.put(key,pageElementConfigService.list(request));
    }

    @Operation(summary = "查询列表-商品页面")
    @PostMapping("/listProduct")
    public CommonResponse<Map<String,List<PageElementConfigResp>>> listProduct(@RequestBody PageElementConfigRequest request) {
        Map<String, List<PageElementConfigResp>> map = new HashMap<>();
        packOneTypeData(PageElementConfigTypeEnum.PRODUCT_BANNER,request,map);
        return CommonResponse.<Map<String, List<PageElementConfigResp>>>builder().data(map).build();
    }

    @Operation(summary = "查询列表-我的页面")
    @PostMapping("/listMy")
    public CommonResponse<Map<String,List<PageElementConfigResp>>> listMy(@RequestBody PageElementConfigRequest request) {
        Map<String, List<PageElementConfigResp>> map = new HashMap<>();
        packOneTypeData(PageElementConfigTypeEnum.MY_ORDER,request,map);
        packOneTypeData(PageElementConfigTypeEnum.MY_SERVICE,request,map);
        return CommonResponse.<Map<String, List<PageElementConfigResp>>>builder().data(map).build();
    }






}
