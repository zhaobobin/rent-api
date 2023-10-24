package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.api.request.BindBankCard;
import com.rent.common.dto.user.BankCardDto;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.EnumBankCardType;
import com.rent.common.properties.SuningProperties;
import com.rent.dao.user.UserBankCardDao;
import com.rent.dao.user.UserDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.user.User;
import com.rent.model.user.UserBankCard;
import com.rent.service.components.SuningOpenApiService;
import com.rent.service.user.UserCertificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@Tag(name = "小程序用户买断订单模块")
@RequestMapping("/zyj-api-web/hzsx/api/bankcard")
@RequiredArgsConstructor
public class UserBankController {
    private final SuningOpenApiService suningOpenApiService;
    private final SuningProperties suningProperties;
    private final UserCertificationService userCertificationService;
    private final UserDao userDao;
    private final UserBankCardDao userBankCardDao;

    @GetMapping("/bindBankCardList")
    public CommonResponse<List<BankCardDto>> applyBankCardSign(@RequestParam("uid") String uid) {
        List<UserBankCard> userBankCards = userBankCardDao.getUserBankCardByUid(uid);
        List<BankCardDto> dtos = new ArrayList<>();
        userBankCards.forEach(bk -> {
            BankCardDto dto = new BankCardDto();
            dto.setCardNo(bk.getCardNo());
            dto.setCardType(bk.getCardType());
            dto.setCheckValue(bk.getCheckValue());
            dto.setExpMonth(bk.getExpMonth());
            dto.setExpYear(bk.getExpYear());
//            dto.setMobileNo(bk.getMobileNo());
            dto.setCreateTime(bk.getCreateTime());
            dtos.add(dto);
        });

        return CommonResponse.<List<BankCardDto>>builder().data(dtos).build();
    }


    // 接口1 签约申请
    @PostMapping("/signApply")
    public CommonResponse<String> applyBankCardSign(@RequestParam("uid") String uid, @RequestBody BindBankCard req) {

        UserCertificationDto certificationDto = userCertificationService.getByUid(uid);
        User user = userDao.getUserByUid(uid);
        if (Objects.isNull(user)) {
            throw new HzsxBizException("-1", "用户不存在");
        }
        if (Objects.isNull(certificationDto)) {
            throw new HzsxBizException("-1", "请先完成实名认证");
        }

        Map<String, String> params = new HashMap<>();
        params.put("cardHolderName", certificationDto.getUserName()); // 持卡人姓名
        params.put("certType", "01"); // 证件类型
        params.put("certNo", certificationDto.getIdCard());// 证件号码
        params.put("mobileNo", req.getMobileNo());// 预留手机号
        params.put("cardNo", req.getCardNo());// 银行卡号
        String msgId = "";
        try {
            msgId = suningOpenApiService.sign(params, String.valueOf(user.getId()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new HzsxBizException("-1", "服务器异常");
        }
        return CommonResponse.<String>builder().data(msgId).build();
    }

    // 接口2 签约确认
    @PostMapping("/signConfirm")
    public CommonResponse<String> confirmBankCardSign(@RequestParam("uid") String uid, @RequestBody BindBankCard req) {
        User user = userDao.getUserByUid(uid);
        UserCertificationDto certificationDto = userCertificationService.getByUid(uid);
        String contractNo = "";
        try {
            contractNo = suningOpenApiService.confirm(req.getMsgId(), req.getCaptchaCode(), String.valueOf(user.getId()));

            UserBankCard userBankCard = new UserBankCard();
            userBankCard.setCardHolderName(certificationDto.getUserName());
            userBankCard.setIcCard(certificationDto.getIdCard());
            userBankCard.setCardType(req.getCardType().getCode());
            if (req.getCardType().equals(EnumBankCardType.CREDIT)) {
                userBankCard.setCheckValue(req.getCvv());
                userBankCard.setExpMonth(req.getExpMonth());
                userBankCard.setExpYear(req.getExpYear());
            }
            userBankCard.setCardNo(req.getCardNo());
            userBankCard.setMobileNo(req.getMobileNo());
            userBankCard.setContractNo(contractNo);
            userBankCard.setCreateTime(new Date());
            userBankCardDao.save(userBankCard);
        } catch (IOException e) {
            throw new HzsxBizException("-1", "服务器异常");
        }
        return CommonResponse.<String>builder().data(contractNo).build();
    }

}
