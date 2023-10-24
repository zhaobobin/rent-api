package com.rent.controller.callback;

import com.rent.common.constant.AlipayCallback;
import com.rent.common.util.GsonUtil;
import com.rent.service.components.AliPayCapitalNotifyService;
import com.rent.util.AliPayUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;


@RestController
@RequestMapping("/zyj-component/callBack/aliPayCapitalNotify")
@Tag(name = "回调-资金操作操作回调控制器")
@Slf4j
@RequiredArgsConstructor
public class AliPayCapitalNotifyController {

    private final AliPayCapitalNotifyService aliPayCapitalNotifyService;

    @PostMapping("/aliPayFreezeCallback")
    @Operation(summary = "预授权冻结回调接口")
    public void aliPayFreezeCallback(HttpServletRequest request, HttpServletResponse response) {
        log.info("回调成功  冻结回调成功");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayUtils.amendCodeMap(request.getParameterMap());
        String userId = params.get(AlipayCallback.FreezeCallbackParamName.PAYER_USER_ID);
        String openId = params.get(AlipayCallback.FreezeCallbackParamName.PAYER_OPEN_ID);

        log.info("预授权冻结异步通知:{}" + GsonUtil.objectToJsonString(params));
        try (PrintWriter writer = response.getWriter()) {
            aliPayCapitalNotifyService.freezeCallBack(
                    params.get(AlipayCallback.FreezeCallbackParamName.AUTH_NO),
                    params.get(AlipayCallback.FreezeCallbackParamName.OUT_ORDER_NO),
                    params.get(AlipayCallback.FreezeCallbackParamName.OPERATION_ID),
                    params.get(AlipayCallback.FreezeCallbackParamName.OUT_REQUEST_NO),
                    params.get(AlipayCallback.FreezeCallbackParamName.AMOUNT),
                    params.get(AlipayCallback.FreezeCallbackParamName.STATUS),
                    StringUtils.isNotEmpty(userId) ? userId : openId,
                    params.get(AlipayCallback.FreezeCallbackParamName.GMT_TRANS),
                    params.get(AlipayCallback.FreezeCallbackParamName.PRE_AUTH_TYPE),
                    params.get(AlipayCallback.FreezeCallbackParamName.CREDIT_AMOUNT),
                    params.get(AlipayCallback.FreezeCallbackParamName.FUND_AMOUNT));
            writer.write("success"); //一定要打印success
            writer.flush();
        } catch (Exception e) {
            log.info("预授权冻结异步通知处理异常", e);
        }
    }

    @PostMapping("alipayTradePayCallback")
    @Operation(summary = "支付成功回调")
    public void alipayTradePayCallback(HttpServletRequest request, HttpServletResponse response) {
        log.info("回调成功 支付成功回调");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayUtils.amendCodeMap(request.getParameterMap());
        log.info("【预授权转支付 参数：{}", GsonUtil.objectToJsonString(params));

        //处理业务
        try (PrintWriter writer = response.getWriter()) {
            log.info("异步通知:{}", GsonUtil.objectToJsonString(params));

            aliPayCapitalNotifyService.aliPayCallBack(params.get("trade_no"), params.get("out_trade_no"),
                    params.get("buyer_logon_id"), params.get("total_amount"), params.get("receipt_amount"),
                    params.get("gmt_payment"), params.get("fund_bill_list"), params.get("buyer_id"),
                    params.get("trade_status"));
            writer.write("success"); //一定要打印success
            writer.flush();
        } catch (Exception e) {
            log.info("预授权转支付异常", e);
        }
    }

    @PostMapping("alipayTradePagePayCallback")
    @Operation(summary = "网页支付支付成功回调")
    public void alipayTradePagePayCallback(HttpServletRequest request, HttpServletResponse response) {
        log.info("回调成功 网页支付支付成功回调");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayUtils.amendCodeMap(request.getParameterMap());
        log.info("【PAGE支付 参数：{}", GsonUtil.objectToJsonString(params));

        //处理业务
        try (PrintWriter writer = response.getWriter()) {
            log.info("异步通知:{}", GsonUtil.objectToJsonString(params));

            aliPayCapitalNotifyService.alipayTradePagePayCallback(params.get("passback_params"), params.get("trade_no"), params.get("out_trade_no"),
                    params.get("buyer_logon_id"), params.get("total_amount"), params.get("receipt_amount"),
                    params.get("gmt_payment"), params.get("fund_bill_list"), params.get("buyer_id"),
                    params.get("trade_status"));
            writer.write("success"); //一定要打印success
            writer.flush();
        } catch (Exception e) {
            log.info("132", e);
        }
    }

    @PostMapping("alipayTradeCreateCallback")
    @Operation(summary = "订单支付支付成功回调")
    public void alipayTradeCreateCallback(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = AliPayUtils.amendCodeMap(request.getParameterMap());
        log.info("【alipayTradeCreateCallback-TradeCreate支付支付 参数：{}", GsonUtil.objectToJsonString(params));
        //处理业务
        try (PrintWriter writer = response.getWriter()) {
            log.info("alipayTradeCreateCallback-异步通知:{}", GsonUtil.objectToJsonString(params));
            aliPayCapitalNotifyService.aliPayTradeCreateCallBack(params.get("passback_params"), params.get("trade_no"), params.get("out_trade_no"),
                    params.get("buyer_logon_id"), params.get("total_amount"), params.get("receipt_amount"),
                    params.get("gmt_payment"), params.get("fund_bill_list"), params.get("buyer_id"),
                    params.get("trade_status"));
            writer.write("success");
            writer.flush();
        } catch (Exception e) {
            log.info("132", e);
        }
    }

    @Operation(summary = "分期租金冻结转支付回调")
    @PostMapping("stageOrderAlipayCallback")
    public void stageOrderAlipayCallback(HttpServletRequest request, HttpServletResponse response) {
        log.info("【分期租金-冻结转支付回调】");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayUtils.amendCodeMap(request.getParameterMap());
        log.info("【分期租金-冻结转支付回调】参数：{}", GsonUtil.objectToJsonString(params));
        //处理业务
        try (PrintWriter writer = response.getWriter()) {
            aliPayCapitalNotifyService.stageOrderAlipayCallback(
                    params.get(AlipayCallback.TradePayCallbackParamName.OUT_TRADE_NO),
                    params.get(AlipayCallback.TradePayCallbackParamName.TRADE_STATUS),
                    params.get(AlipayCallback.TradePayCallbackParamName.BUYER_ID),
                    params.get(AlipayCallback.TradePayCallbackParamName.FUND_BILL_LIST),
                    params.get(AlipayCallback.TradePayCallbackParamName.GMT_PAYMENT),
                    params.get(AlipayCallback.TradePayCallbackParamName.TOTAL_AMOUNT),
                    params.get(AlipayCallback.TradePayCallbackParamName.RECEIPT_AMOUNT),
                    params.get(AlipayCallback.TradePayCallbackParamName.TRADE_NO));
            writer.write("success");
            writer.flush();
        } catch (Exception e) {
            log.error("【分期租金-冻结转支付回调】异常", e);
        }
    }

    //解冻回调接口
    @Operation(summary = "解冻回调接口")
    @PostMapping("/alipayUnFreezeCallBack")
    @ResponseBody
    public void alipayUnFreezeCallBack(HttpServletRequest request, HttpServletResponse response) {
        log.info("预授权资金解冻");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayUtils.amendCodeMap(request.getParameterMap());
        log.info("【预授权资金解冻】参数：{}", GsonUtil.objectToJsonString(params));
        try (PrintWriter writer = response.getWriter()) {
            aliPayCapitalNotifyService.alipayUnFreezeCallBack(
                    params.get(AlipayCallback.UnfreezeCallbackParamName.AUTH_NO),
                    params.get(AlipayCallback.UnfreezeCallbackParamName.OUT_ORDER_NO),
                    params.get(AlipayCallback.UnfreezeCallbackParamName.OPERATION_ID),
                    params.get(AlipayCallback.UnfreezeCallbackParamName.OUT_REQUEST_NO),
                    params.get(AlipayCallback.UnfreezeCallbackParamName.AMOUNT),
                    params.get(AlipayCallback.UnfreezeCallbackParamName.STATUS));
            //4、向芝麻反馈处理是否成功
            writer.write("success");
            writer.flush();
        } catch (Exception e) {
            log.error("【解冻回调】异常", e);
        }
    }


}
