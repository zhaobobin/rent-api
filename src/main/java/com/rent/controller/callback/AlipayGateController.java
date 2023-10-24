package com.rent.controller.callback;

import com.rent.common.util.GsonUtil;
import com.rent.model.components.AlipayGateWay;
import com.rent.service.components.AliPayGateWayService;
import com.rent.util.AliPayUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author zhaowenchao
 */
@Slf4j
@RestController
@Tag(name = "回调-支付宝网关")
@RequestMapping("/zyj-component/alipay")
public class AlipayGateController {

    private AliPayGateWayService aliPayGateWayService;

    @Autowired
    public AlipayGateController(AliPayGateWayService aliPayGateWayService) {
        this.aliPayGateWayService = aliPayGateWayService;
    }

    /**
     * 网关
     *
     * @param request
     * @param response
     */
    @RequestMapping("/gateWay")
    public void gateWay(HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> params = AliPayUtils.amendCodeMap(request.getParameterMap());
        log.info("支付宝网关请求:{}" + GsonUtil.objectToJsonString(params));
        AlipayGateWay alipayGateWay = new AlipayGateWay();
        alipayGateWay.setAppId(params.get("app_id"));
        alipayGateWay.setBizContent(params.get("biz_content"));
        alipayGateWay.setMsgMethod(params.get("msg_method"));
        alipayGateWay.setNotifyId(params.get("notify_id"));
        alipayGateWay.setDataUtcTimestamp(params.get("utc_timestamp"));
        alipayGateWay.setDataVersion(params.get("version"));
        alipayGateWay.setNotifyType(params.get("notify_type"));
        alipayGateWay.setNotifyType(params.get("notify_type"));
        aliPayGateWayService.save(alipayGateWay);
        try (PrintWriter writer = response.getWriter()) {
            writer.write("success");
            writer.flush();
        } catch (Exception e) {
            log.info("支付宝网关请求处理异常", e);
        }
    }


}
