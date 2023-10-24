package com.rent.controller.backstage;

import com.alibaba.fastjson.JSONObject;
import com.rent.common.constant.RedisKey;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.ExportBuyOutOrderReq;
import com.rent.common.dto.backstage.ExportHistory;
import com.rent.common.dto.backstage.ExportRentOrderReq;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.order.AccountPeriodItemReqDto;
import com.rent.common.dto.order.FeeBillDetailReqDto;
import com.rent.common.dto.order.resquest.ChannelUserOrdersReqDto;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.common.util.AsyncUtil;
import com.rent.exception.HzsxBizException;
import com.rent.service.export.AccountPeriodExportService;
import com.rent.service.export.BuyOutExportService;
import com.rent.service.export.FormWordService;
import com.rent.service.export.OrdersExportService;
import com.rent.util.LoginUserUtil;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhaowenchao
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/export")
@Slf4j
@RequiredArgsConstructor
public class ExportController {

    private final OrdersExportService ordersExportService;
    private final BuyOutExportService buyOutExportService;
    private final AccountPeriodExportService accountPeriodExportService;
    private final FormWordService formWordService;

    @Operation(summary = "租赁订单导出-商家和运营公用一个接口")
    @GetMapping("/exportHistory")
    public CommonResponse<List<ExportHistory>> exportRentOrder() {
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        List<ExportHistory> histories = new ArrayList<>();
        List exportHistoryKeys = RedisUtil.lGet(RedisKey.EXPORT_HISTORY_PREFIX+userDto.getId(),0,-1);
        for (Object exportHistoryKey : exportHistoryKeys) {
            ExportHistory exportHistory = JSONObject.parseObject((String) RedisUtil.hget(RedisKey.EXPORT_HISTORY_KEY,(String)exportHistoryKey),ExportHistory.class);
            histories.add(exportHistory);
        }
        return CommonResponse.<List<ExportHistory>>builder().data(histories).build();
    }


    @Operation(summary = "租赁订单导出-商家和运营公用一个接口")
    @PostMapping("/rentOrder")
    public CommonResponse<Void> exportRentOrder(@RequestBody ExportRentOrderReq req) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }

        req.setBackstageUserId(loginUser.getId());
        if(!EnumBackstageUserPlatform.OPE.getCode().equals(loginUser.getShopId())){
            req.setShopId(loginUser.getShopId());
        }
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(loginUser);
            ordersExportService.rentOrder(req);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }


    @Operation(summary = "预审单-导出")
    @GetMapping("/prequalificationSheet")
    public CommonResponse<Void> prequalificationSheet(@RequestParam("orderId") String orderId) {
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(userDto.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(userDto);
            formWordService.prequalificationSheet(orderId);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }

    @Operation(summary = "回执单-导出")
    @GetMapping("/receiptConfirmationReceipt")
    public CommonResponse<Void> receiptConfirmationReceipt(@RequestParam("orderId") String orderId) {
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(userDto.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(userDto);
            formWordService.receiptConfirmationReceipt(orderId);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }



    @Operation(summary = "逾期订单导出-商家和运营公用一个接口")
    @PostMapping("/overdueOrder")
    public CommonResponse<Void> overdueOrder() {
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(userDto.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        ExportRentOrderReq req = new ExportRentOrderReq();
        req.setBackstageUserId(userDto.getId());
        if(!EnumBackstageUserPlatform.OPE.getCode().equals(userDto.getShopId())){
            req.setShopId(userDto.getShopId());
        }
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(userDto);
            ordersExportService.overdueOrder(req);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }

    @Operation(summary = "到期未归还订单导出-商家和运营公用一个接口")
    @PostMapping("/notGiveBack")
    public CommonResponse<Void> notGiveBack() {
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(userDto.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        ExportRentOrderReq req = new ExportRentOrderReq();
        req.setBackstageUserId(userDto.getId());
        if(!EnumBackstageUserPlatform.OPE.getCode().equals(userDto.getShopId())){
            req.setShopId(userDto.getShopId());
        }
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(userDto);
            ordersExportService.notGiveBack(req);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }

    @Operation(summary = "买断订单-商家和运营公用一个接口")
    @PostMapping("/buyOut")
    public CommonResponse<Void> buyOut(@RequestBody ExportBuyOutOrderReq request){
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(userDto.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        request.setBackstageUserId(userDto.getId());
        if(!EnumBackstageUserPlatform.OPE.getCode().equals(userDto.getShopId())){
            request.setShopId(userDto.getShopId());
        }
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(userDto);
            buyOutExportService.buyOut(request);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }


    @Operation(summary = "财务-买断")
    @PostMapping("/accountPeriodBuyOut")
    public CommonResponse<Void> accountPeriodBuyOut(@RequestBody AccountPeriodItemReqDto request){
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        String loginShopId = LoginUserUtil.getLoginUser().getShopId();
        if(!EnumBackstageUserPlatform.OPE.getCode().equals(loginShopId)){
            request.setShopId(loginShopId);
        }
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(userDto);
            accountPeriodExportService.buyOut(request);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }

    @Operation(summary = "财务-常规")
    @PostMapping("/accountPeriodRent")
    public CommonResponse<Void> accountPeriodRent(@RequestBody AccountPeriodItemReqDto request){
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        String loginShopId = LoginUserUtil.getLoginUser().getShopId();
        if(!EnumBackstageUserPlatform.OPE.getCode().equals(loginShopId)){
            request.setShopId(loginShopId);
        }
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(userDto);
            accountPeriodExportService.rent(request);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }

    @Operation(summary = "渠道租赁订单导出")
    @PostMapping("/channelRentOrder")
    public CommonResponse<Void> channelRentOrder(@RequestBody ChannelUserOrdersReqDto request){
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        String loginShopId = LoginUserUtil.getLoginUser().getShopId();
        if(!EnumBackstageUserPlatform.OPE.getCode().equals(loginShopId)){
            request.setMarketingChannelId(loginShopId);
        }
        AsyncUtil.runAsync(()->{
                LoginUserUtil.setLoginUser(userDto);
                ordersExportService.channelRentOrder(request);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }

    @Operation(summary = "费用结算明细导出")
    @PostMapping("/feeBillDetail")
    public CommonResponse<Void> feeBillDetail(@RequestBody FeeBillDetailReqDto request){
        String loginShopId = LoginUserUtil.getLoginUser().getShopId();
        LoginUserBo userDto = LoginUserUtil.getLoginUser();
        if(!"OPE".equals(loginShopId)){
            request.setShopId(loginShopId);
        }
        AsyncUtil.runAsync(()->{
            LoginUserUtil.setLoginUser(userDto);
            accountPeriodExportService.feeBillDetail(request);
        });
        return CommonResponse.<Void>builder().data(null).build();
    }

}
