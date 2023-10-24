
package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.SplitBilRentlDto;
import com.rent.common.dto.backstage.SplitBillDetailBuyOutDto;
import com.rent.common.dto.backstage.SplitBillDetailRentDto;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.response.SplitBillBuyOutDto;
import com.rent.common.dto.order.resquest.AccountPeriodRemarkReqDto;
import com.rent.common.dto.order.resquest.AccountPeriodReqDto;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.service.order.AccountPeriodItemService;
import com.rent.service.order.AccountPeriodRemarkService;
import com.rent.service.order.AccountPeriodService;
import com.rent.util.LoginUserUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 账期模块控制器
 *
 * @author xiaotong
 * @Date 2020-08-11 09:59
 */
@Tag(name = "账期模块")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/accountPeriod")
@RequiredArgsConstructor
public class AccountPeriodController {

    private final AccountPeriodService accountPeriodService;
    private final AccountPeriodRemarkService accountPeriodRemarkService;
    private final AccountPeriodItemService accountPeriodItemService;


    /**
     * 生成账期
     * @return boolean
     */
    @PostMapping("/generateAccountPeriod")
    public CommonResponse<Void> generateAccountPeriod() {
        accountPeriodService.generateAccountPeriod();
        return CommonResponse.<Void>builder().build();
    }

    /**
     * 分页查询账期
     * @param reqDto
     * @return
     */
    @PostMapping("/queryAccountPeriodPage")
    public CommonResponse<Page<AccountPeriodDto>> queryAccountPeriodPage(@RequestBody AccountPeriodReqDto reqDto){
        if(!LoginUserUtil.getLoginUser().getShopId().equals(EnumBackstageUserPlatform.OPE.getCode())){
            reqDto.setShopId(LoginUserUtil.getLoginUser().getShopId());
        }
        return CommonResponse.<Page<AccountPeriodDto>>builder().data(accountPeriodService.queryAccountPeriodPage(reqDto)).build();
    }

    /**
     * 查询详细信息
     * @param id
     * @return
     */
    @GetMapping("/queryAccountPeriodDetail")
    public CommonResponse<AccountPeriodDetailDto> queryAccountPeriodDetail(@RequestParam("id") Long id){
        return CommonResponse.<AccountPeriodDetailDto>builder().data(accountPeriodService.detail(id,LoginUserUtil.getLoginUser().getShopId())).build();
    }

    /**
     * 添加备注
     * @param reqDto
     * @return
     */
    @PostMapping("/addRemark")
    public CommonResponse<Void> addRemark(@RequestBody AccountPeriodRemarkReqDto reqDto) {
        reqDto.setBackstageUserName(LoginUserUtil.getLoginUser().getName());
        accountPeriodRemarkService.add(reqDto);
        return CommonResponse.<Void>builder().build();
    }

    /**
     * 查询备注
     * @param reqDto
     * @return
     */
    @PostMapping("/listRemark")
    public CommonResponse<Page<AccountPeriodRemarkDto>> listRemark(@RequestBody AccountPeriodRemarkReqDto reqDto) {
        return CommonResponse.<Page<AccountPeriodRemarkDto>>builder().data(accountPeriodRemarkService.listByAccountPeriodId(reqDto)).build();
    }
    /**
     * 提交结算
     * @param req
     * @return
     */
    @PostMapping("/submitSettle")
    public CommonResponse<Void> submitSettle(@RequestBody AccountPeriodSubmitSettleDto req){
        req.setBackstageUserName(LoginUserUtil.getLoginUser().getName());
        accountPeriodService.submitSettle(req);
        return CommonResponse.<Void>builder().build();
    }
    /**
     * 提交审核
     * @param req
     * @return
     */
    @PostMapping("/submitAudit")
    public CommonResponse<Void> submitAudit(@RequestBody AccountPeriodSubmitAuditDto req){
        accountPeriodService.submitAudit(req);
        return CommonResponse.<Void>builder().build();
    }



    /**
     * 查看明细-常规订单
     * @param request
     * @return
     */
    @PostMapping("rent")
    public CommonResponse<Page<SplitBilRentlDto>> rent(@RequestBody AccountPeriodItemReqDto request){
        request.setShopId(LoginUserUtil.getLoginUser().getShopId());
        return CommonResponse.<Page<SplitBilRentlDto>>builder().data(accountPeriodItemService.rent(request)).build();
    }

    /**
     * 查看明细-常规订单
     * @param request
     * @return
     */
    @PostMapping("listRent")
    public CommonResponse<Page<SplitBilRentlDto>> listRent(@RequestBody AccountPeriodItemReqDto request){
        if(!LoginUserUtil.getLoginUser().getShopId().equals(EnumBackstageUserPlatform.OPE.getCode())){
            request.setShopId(LoginUserUtil.getLoginUser().getShopId());
        }
        Page<SplitBilRentlDto> page = accountPeriodItemService.listRent(request);
        return CommonResponse.<Page<SplitBilRentlDto>>builder().data(page).build();
    }



    /**
     * 查看明细-买断账单
     * @param request
     * @return
     */
    @PostMapping("buyOut")
    public CommonResponse<Page<SplitBillBuyOutDto>> buyOut(@RequestBody AccountPeriodItemReqDto request){
        request.setShopId(LoginUserUtil.getLoginUser().getShopId());
        return CommonResponse.<Page<SplitBillBuyOutDto>>builder().data(accountPeriodItemService.buyOut(request)).build();
    }

    /**
     * 查看明细-买断账单
     * @param request
     * @return
     */
    @PostMapping("/listBuyOut")
    public CommonResponse<Page<SplitBillBuyOutDto>> listBuyOut(@RequestBody AccountPeriodItemReqDto request){
        if(!LoginUserUtil.getLoginUser().getShopId().equals(EnumBackstageUserPlatform.OPE.getCode())){
            request.setShopId(LoginUserUtil.getLoginUser().getShopId());
        }
        Page<SplitBillBuyOutDto> page = accountPeriodItemService.listBuyOut(request);
        return CommonResponse.<Page<SplitBillBuyOutDto>>builder().data(page).build();
    }


    /**
     * 查询 租赁 分账 详细信息
     * @param id 条件
     * @return BackstageUserOrderDetailDto
     */
    @GetMapping("/rentDetail")
    public CommonResponse<SplitBillDetailRentDto> rentDetail(@RequestParam("id") Long id) {
        SplitBillDetailRentDto dto = accountPeriodItemService.rentDetail(id,LoginUserUtil.getLoginUser().getShopId());
        return CommonResponse.<SplitBillDetailRentDto>builder().data(dto).build();
    }

    /**
     * 查询 买断 分账 详细信息
     * @param id 条件
     * @return BackstageUserOrderDetailDto
     */
    @GetMapping("/buyOutDetail")
    public CommonResponse<SplitBillDetailBuyOutDto> buyOutDetail(@RequestParam("id") Long id) {
        SplitBillDetailBuyOutDto dto = accountPeriodItemService.buyOutDetail(id,LoginUserUtil.getLoginUser().getShopId());
        return CommonResponse.<SplitBillDetailBuyOutDto>builder().data(dto).build();
    }



}

