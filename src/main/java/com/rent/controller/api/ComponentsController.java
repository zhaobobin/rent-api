package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.components.request.AliFaceNoticeRequest;
import com.rent.exception.HzsxBizException;
import com.rent.service.components.AlipayFaceAuthRecordService;
import com.rent.util.AliPayUtils;
import com.rent.common.util.OSSFileUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@Tag(name = "小程序通用接口")
@RequestMapping("/zyj-api-web/hzsx/api/components")
public class ComponentsController {

    private final AlipayFaceAuthRecordService alipayFaceAuthRecordService;
    private final OSSFileUtils ossFileUtils;

    @PostMapping(value = "/uploadFile")
    @Operation(summary = "上传文件")
    public CommonResponse<String> uploadFile(MultipartFile multipartFile) {
        return CommonResponse.<String>builder().data(ossFileUtils.uploadByMultipartFile(multipartFile,"api")).build();
    }

    @Operation(summary = "人脸识别回调")
    @GetMapping("/faceAuthInitAsync")
    public CommonResponse<Boolean> faceAuthInitAsync(AliFaceNoticeRequest model) {
        alipayFaceAuthRecordService.superCloudFaceAuthInitAsync(model.getCertifyId());
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "支付宝小程序，解密")
    @GetMapping("/decrypt")
    public CommonResponse<String> decrypt(@RequestParam("content") @Parameter(description = "父级类目ID，顶级为0",required = true)String content) {
        try {
            String decrypt = AliPayUtils.decrypt(content);
            return CommonResponse.<String>builder().data(decrypt).build();
        } catch (Exception e) {
            throw new HzsxBizException("99999", e.getMessage(), this.getClass());
        }
    }
}
