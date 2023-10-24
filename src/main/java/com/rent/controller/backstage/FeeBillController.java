        
package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.order.response.FeeBillPageResp;
import com.rent.common.dto.order.resquest.FeeBillReqDto;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.service.order.FeeBillService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Tag(name = "账期模块")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/feeBill")
@RequiredArgsConstructor
public class FeeBillController {

    private final FeeBillService feeBillService;

    @Operation(description = "分页查询费用账单")
    @PostMapping("/page")
    public CommonResponse<Page<FeeBillPageResp>> page(@RequestBody FeeBillReqDto reqDto){
        String shopId = LoginUserUtil.getLoginUser().getShopId();
        if(!EnumBackstageUserPlatform.OPE.getCode().equals(shopId)){
            reqDto.setShopId(shopId);
        }
        Page<FeeBillPageResp> response =  feeBillService.page(reqDto);
        return CommonResponse.<Page<FeeBillPageResp>>builder().data(response).build();
    }

    @Operation(description = "导入预授权转支付费用")
    @PostMapping("/importAssessmentFee")
    public CommonResponse<Boolean> importAssessmentFee(MultipartFile file) throws IOException {
        List<String> outOrderNoList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "GBK"));
            String line = null;
            while((line=reader.readLine())!=null){
                String[] lineContent = line.split(",");
                if(lineContent.length>=17){
                    outOrderNoList.add(lineContent[16]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        feeBillService.importAssessmentFee(outOrderNoList);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }
}

