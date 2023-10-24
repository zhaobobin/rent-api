package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.service.order.OrderAntChainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaowenchao
 */
@Slf4j
@RestController
@Tag(name = "运营订单模块")
@RequestMapping("/zyj-backstage-web/hzsx/order/antChain")
@RequiredArgsConstructor
public class OrderAntChainController {

    private final OrderAntChainService orderAntChainService;

    @Operation(summary = "查询蚁盾分")
    @GetMapping("/queryShieldScore")
    public CommonResponse<String> queryAntChainShieldScore(@RequestParam("orderId") String orderId){
        String score = orderAntChainService.queryAntChainShieldScore(orderId);
        return CommonResponse.<String>builder().data(score).build();
    }

    @Operation(summary = "订单上链")
    @GetMapping("/syncToAntChain")
    public CommonResponse<Boolean> syncToAntChain(@RequestParam("orderId") String orderId){
        orderAntChainService.syncToAntChain(orderId);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "蚂蚁链投保")
    @GetMapping("/insure")
    public CommonResponse<Boolean> insure(@RequestParam("orderId") String orderId){
        orderAntChainService.antChainInsure(orderId);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "蚂蚁链退保")
    @GetMapping("/cancelInsurance")
    public CommonResponse<Boolean> cancelInsurance(@RequestParam("orderId") String orderId){
        orderAntChainService.antChainCancelInsurance(orderId);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


}
