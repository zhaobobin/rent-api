        
package com.rent.controller.backstage;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.order.AccountPeriodDetailDto;
import com.rent.common.dto.product.ShopFundFlowDto;
import com.rent.common.dto.product.ShopFundFlowReqDto;
import com.rent.common.dto.product.ShopSplitBillAccountDto;
import com.rent.common.dto.product.request.WithdrawApplyPageReq;
import com.rent.common.dto.product.resp.WithdrawApplyPageResp;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.service.order.AccountPeriodService;
import com.rent.service.product.ShopFundService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


/**
 * @author zhaowenchao
 */
@Tag(name = "商家资金管理")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/shopFund")
@RequiredArgsConstructor
public class ShopFundController {

    private final ShopFundService shopFundService;
    private final AccountPeriodService accountPeriodService;

    @Operation(summary = "分页查看商家资金余额")
    @PostMapping("/pageShopFundBalance")
    public CommonResponse<Page<ShopFundFlowDto>> pageShopFundBalance(@RequestBody ShopFundFlowReqDto request) {
        Page<ShopFundFlowDto> response = shopFundService.pageShopFundBalance(request);
        return CommonResponse.<Page<ShopFundFlowDto>>builder().data(response).build();
    }

    @GetMapping("/getShopAccountInfo")
    public CommonResponse<ShopSplitBillAccountDto> getShopAccountInfo() {
        String shopId = LoginUserUtil.getLoginUser().getShopId();
        ShopSplitBillAccountDto shopAccountInfo = shopFundService.getShopAccountInfo(shopId);
        return CommonResponse.<ShopSplitBillAccountDto>builder().data(shopAccountInfo).build();
    }

    @Operation(summary = "分页查看商家资金流水")
    @PostMapping("/pageShopFundFlow")
    public CommonResponse<Page<ShopFundFlowDto>> pageShopFundFlow(@RequestBody ShopFundFlowReqDto request) {
        if(EnumBackstageUserPlatform.SHOP.getCode().equals(LoginUserUtil.getLoginUser().getType().getCode())){
            request.setShopId(LoginUserUtil.getLoginUser().getShopId());
        }
        Page<ShopFundFlowDto> shopFundFlowDtoPage = shopFundService.pageShopFundFlow(request);
        return CommonResponse.<Page<ShopFundFlowDto>>builder().data(shopFundFlowDtoPage).build();
    }

    @Operation(summary = "商家余额提现")
    @GetMapping("/withDraw")
    public CommonResponse<Boolean> withDraw(@Parameter(name = "amount",description = "提现金额",required = true) @RequestParam("amount") BigDecimal amount) {
        shopFundService.withDraw(amount);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(description = "商家提现审批 分页列表页面")
    @PostMapping("/withDrawApplyPage")
    public CommonResponse<Page<WithdrawApplyPageResp>> withDrawApplyPage(@RequestBody WithdrawApplyPageReq request) {
        if(LoginUserUtil.getLoginUser().getType().equals(EnumBackstageUserPlatform.SHOP)){
            request.setShopId(LoginUserUtil.getLoginUser().getShopId());
        }
        return CommonResponse.<Page<WithdrawApplyPageResp>>builder().data(shopFundService.withDrawApplyPage(request)).build();
    }

    @Operation(description = "商家提现审批 审核")
    @GetMapping("/withDrawPass")
    public CommonResponse<Boolean> withDrawPass(@RequestParam("id") Long id) {
        shopFundService.withDrawPass(id);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @GetMapping("/recharge")
    public CommonResponse<String> recharge(@RequestParam("amount") BigDecimal amount) {
        ShopFundFlowDto request = new ShopFundFlowDto();
        LoginUserBo loginUserBo = LoginUserUtil.getLoginUser();
        request.setShopId(loginUserBo.getShopId());
        request.setOperator(loginUserBo.getMobile());
        request.setChangeAmount(amount);
        String rechargeUrl = shopFundService.recharge(request);
        return CommonResponse.<String>builder().data(rechargeUrl).build();
    }

    @GetMapping("/prof")
    public CommonResponse<JSONObject> prof(@RequestParam("id") Long id) {
        LoginUserBo loginUserBo = LoginUserUtil.getLoginUser();
        String shopId = loginUserBo.getShopId();
        return CommonResponse.<JSONObject>builder().data(shopFundService.prof(id,shopId)).build();
    }

    @GetMapping("/brokerageDetail")
    public CommonResponse<AccountPeriodDetailDto> brokerageDetail(@RequestParam("id") Long id) {
        LoginUserBo loginUserBo = LoginUserUtil.getLoginUser();
        String shopId = loginUserBo.getShopId();
        Long accountPeriod = shopFundService.getBrokerageAccountPeriodId(id,shopId);
        if(accountPeriod!=null){
            AccountPeriodDetailDto dto = accountPeriodService.detail(accountPeriod,shopId);
            return CommonResponse.<AccountPeriodDetailDto>builder().data(dto).build();
        }else{
            return CommonResponse.<AccountPeriodDetailDto>builder().data(null).build();
        }
    }

    @Operation(description = "商家用户查看当前余额")
    @GetMapping("/getShopFundBalance")
    public CommonResponse<BigDecimal> getShopFundBalance() {
        String shopId = LoginUserUtil.getLoginUser().getShopId();
        return CommonResponse.<BigDecimal>builder().data(shopFundService.getShopFundBalance(shopId)).build();
    }


}
