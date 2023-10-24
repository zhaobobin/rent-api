package com.rent.controller.callback;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rent.common.util.GsonUtil;
import com.rent.service.components.SuningOpenApiNotifyService;
import com.rent.util.AliPayUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@RestController
@RequestMapping("/zyj-component/callBack/yfbNotify")
@Tag(name = "回调-易付宝资金操作操作回调控制器")
@Slf4j
@RequiredArgsConstructor
public class YfbNotifyController {
    private final SuningOpenApiNotifyService suningOpenApiNotifyService;

    @Operation(summary = "签约银行卡代扣支付回调")
    @PostMapping("yfbTradeRefundCallback")
    public void yfbTradeRefundCallback(HttpServletRequest request, HttpServletResponse response) {
        log.info("【易付宝退款回调】===============================");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayUtils.amendCodeMap(request.getParameterMap());
        log.info("【易付宝退款回调】参数：{}", GsonUtil.objectToJsonString(params));
        //处理业务
        try (PrintWriter writer = response.getWriter()) {
            suningOpenApiNotifyService.yfbTradeRefundCallback(params);
            writer.write("success");
            writer.flush();
        } catch (Exception e) {
            log.error("【易付宝退款回调】异常", e);
        }
    }
    @Operation(summary = "签约银行卡代扣支付回调")
    @PostMapping("stageOrderCallback")
    public void stageOrderAlipayCallback(HttpServletRequest request, HttpServletResponse response) {
        log.info("【分期租金-已付宝代扣支付回调】");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayUtils.amendCodeMap(request.getParameterMap());
        log.info("【分期租金-已付宝代扣支付回调】参数：{}", GsonUtil.objectToJsonString(params));
        //处理业务
        try (PrintWriter writer = response.getWriter()) {
            suningOpenApiNotifyService.stageOrderCallback(params);
            writer.write("success");
            writer.flush();
        } catch (Exception e) {
            log.error("【分期租金-冻结转支付回调】异常", e);
        }
    }
}
