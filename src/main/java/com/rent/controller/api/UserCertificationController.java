package com.rent.controller.api;

import cn.hutool.core.util.RandomUtil;
import com.rent.common.cache.user.UserCertificationCache;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.api.MsgCodeDto;
import com.rent.common.dto.components.dto.SendMsgDto;
import com.rent.common.dto.components.request.IdCardOcrRequest;
import com.rent.common.dto.components.response.IdCardOcrResponse;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.util.LoginUtil;
import com.rent.common.util.StringUtil;
import com.rent.exception.HzsxBizException;
import com.rent.service.components.SendSmsService;
import com.rent.service.user.UserCertificationService;
import com.rent.util.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户认证表控制器
 * @author zhao
 * @Date 2020-06-18 15:06
 */
@Tag(name = "小程序用户实名认证")
@RestController
@RequestMapping("/zyj-api-web/hzsx/userCertification")
@RequiredArgsConstructor
public class UserCertificationController {

    private final SendSmsService sendSmsService;
    private final UserCertificationService userCertificationService;

    @Operation(summary = "获取用户实名认证状态")
    @GetMapping("/hasCertification")
    public CommonResponse<String> hasCertification(@RequestParam("uid")String uid) {
        return CommonResponse.<String>builder().data(UserCertificationCache.hasCertification(uid)).build();
    }

    @Operation(summary = "用户实名认证获取短信验证码")
    @GetMapping("/sendSmsCode")
    public CommonResponse<MsgCodeDto> sendSmsCode(@RequestParam("mobile") String mobile) {
        String redisKey = "CER_SMS_CODE::" + mobile;
        if (RedisUtil.hasKey(redisKey)) {
            throw new HzsxBizException("-1", "短信已发送，请稍后再试");
        }
        Long dateTime = System.currentTimeMillis() / 1000;
        //返回给前端加密的验证码
        String randomCode = RandomUtil.randomNumbers(4);
        String encryptSmsCode = LoginUtil.encryptSmsCode(randomCode + mobile + dateTime);
        //发送短信
        SendMsgDto dto = new SendMsgDto();
        dto.setTelephone(mobile);
        dto.setMsgCode(randomCode);
        Boolean sendResult = sendSmsService.realName(dto);
        if (sendResult) {
            // 短信发送成功才加redis key
            RedisUtil.set(redisKey, mobile, 60);
            MsgCodeDto msgCodeDto = new MsgCodeDto();
            msgCodeDto.setCodeKey(encryptSmsCode);
            msgCodeDto.setCodeTime(dateTime);
            return CommonResponse.<MsgCodeDto>builder().data(msgCodeDto)
                .build();
        } else {
            throw new HzsxBizException("-1", "短信发送失败");
        }
    }

    @Operation(summary = "用户实名认证")
    @PostMapping("/userCertificationAuth")
    public CommonResponse<String> userCertificationAuth(@RequestBody UserCertificationDto request) {
        String lockKey = "userCertificationAuthLock::"+request.getUid();
        try {
            if (RedisUtil.tryLock(lockKey,10)){
                if (StringUtil.isEmpty(request.getCodeKey()) || null == request.getCodeTime()) {
                    throw new HzsxBizException("999999", "验证码错误");
                }
                String errorMsg = LoginUtil.verifySmsCode(request.getTelephone(), request.getCodeKey(), request.getSmsCode(),
                    request.getCodeTime(), false);
                if (StringUtil.isNotEmpty(errorMsg)) {
                    throw new HzsxBizException("-1", errorMsg);
                }
            }else {
                throw new HzsxBizException("-1", "认证中,请稍等");
            }
            String uid = userCertificationService.userCertificationAuth(request);
            return CommonResponse.<String>builder().data(uid).build();
        }finally {
            RedisUtil.unLock(lockKey);
        }
    }

    @Operation(summary = "身份证ocr认证")
    @PostMapping(value = "/certificationOcr")
    public CommonResponse<IdCardOcrResponse> certificationOcr(@RequestBody IdCardOcrRequest request) {
        return CommonResponse.<IdCardOcrResponse>builder().data(userCertificationService.certificationOcr(request.getFrontUrl(),request.getBackUrl(), request.getUid())).build();
    }

     @Operation(summary = "查询用户实名认证信息")
     @GetMapping("/getByUid")
     public CommonResponse<UserCertificationDto> getByUid(@RequestParam("uid")String uid) {
         UserCertificationDto certificationDto = userCertificationService.getByUid(uid);
         return CommonResponse.<UserCertificationDto>builder().data(certificationDto).build();
     }

    @Operation(summary = "查询用户实名认证信息")
    @GetMapping("/getDesensitizationByUid")
    public CommonResponse<UserCertificationDto> getDesensitizationByUid(@RequestParam("uid")String uid) {
        UserCertificationDto certificationDto = userCertificationService.getDesensitizationByUid(uid);
        return CommonResponse.<UserCertificationDto>builder().data(certificationDto).build();
    }

}
