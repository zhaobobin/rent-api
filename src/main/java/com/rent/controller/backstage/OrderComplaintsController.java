package com.rent.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.request.ModifyOrderComplaintsReqVo;
import com.rent.common.dto.backstage.request.QueryOrderComplaintsDetailReqVo;
import com.rent.common.dto.backstage.request.QueryOrderComplaintsPageReqVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsDetailRespVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsPageRespVo;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.common.resp.GetOrderComplaintTypeRespVo;
import com.rent.exception.HzsxBizException;
import com.rent.service.marketing.OrderComplaintsService;
import com.rent.util.LoginUserUtil;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2020-09-28 10:13
 **/
@Tag(name = "小程序订单投诉管理接口")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/orderComplaints")
@RequiredArgsConstructor
public class OrderComplaintsController {

    private final OrderComplaintsService orderComplaintsService;

    @Operation(summary = "分页获取小程序订单投诉")
    @PostMapping("/queryOrderComplaintsPage")
    public CommonResponse<Page<OrderComplaintsPageRespVo>> queryOrderComplaintsPage(@RequestBody QueryOrderComplaintsPageReqVo request) {
        Page<OrderComplaintsPageRespVo> response =  orderComplaintsService.queryOrderComplaintsPage(request);
        return CommonResponse.<Page<OrderComplaintsPageRespVo>>builder().data(response).build();
    }


    @Operation(summary = "获取小程序订单投诉详情")
    @PostMapping("/queryOrderComplaintsDetail")
    public CommonResponse<OrderComplaintsDetailRespVo> queryOrderComplaintsDetail(@RequestBody QueryOrderComplaintsDetailReqVo request) {
        OrderComplaintsDetailRespVo dto = orderComplaintsService.queryOrderComplaintsDetail(request.getId());
        return CommonResponse.<OrderComplaintsDetailRespVo>builder().data(dto).build();
    }


    @Operation(summary = "录入小程序订单投诉处理结果")
    @PostMapping("/modifyOrderComplaints")
    public CommonResponse<Boolean> modifyOrderComplaints(@RequestBody ModifyOrderComplaintsReqVo request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        orderComplaintsService.modifyOrderComplaints(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


    @Operation(summary = "获取订单投诉类型集合")
    @GetMapping("/getOrderComplaintsTypes")
    public CommonResponse<List<GetOrderComplaintTypeRespVo>> getOrderComplaintsTypes() {
        List<GetOrderComplaintTypeRespVo> respone = orderComplaintsService.getOrderComplaintsTypes();
        return CommonResponse.<List<GetOrderComplaintTypeRespVo>>builder().data(respone).build();
    }


}
