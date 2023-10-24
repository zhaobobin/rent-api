package com.rent.common.converter.components;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.AlipayFundAuthOrderAppFreezeModel;
import com.alipay.api.request.AlipayFundAuthOrderAppFreezeRequest;
import com.rent.common.cache.product.ProductDistributeCache;
import com.rent.common.dto.product.ProductShopCateReqDto;
import com.rent.common.util.StringUtil;
import com.rent.config.outside.OutsideConfig;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-24 上午 10:34:52
 * @since 1.0
 */
@Slf4j
public class AliPayConverter {

    private static final BigDecimal assessmentAmountDivisor = new BigDecimal(5);
    private static final String defaultZfbCategoryCode = "RENT_DIGITAL";
    private static final String urlPattern = "alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3DAPPID%26page%3D%252fpages%252fproductDetail%252findex%253fitemId%253dITEMID";


    public static AlipayFundAuthOrderAppFreezeRequest buildAliPayFreezeRequest(String outOrderNo, String orderId,
                                                                               ProductShopCateReqDto productShopCateReqDto, BigDecimal amount, String channelId, String aliFreezeCallbackUrl,
                                                                               String outRequestNo, String productId, BigDecimal rentAmount, Integer rentPeriod) {

        String serviceId = OutsideConfig.SERVICEID;
        String payeeUserId = OutsideConfig.PARENT_ID;
        String appId = OutsideConfig.APPID;
        AlipayFundAuthOrderAppFreezeRequest request = new AlipayFundAuthOrderAppFreezeRequest();
        AlipayFundAuthOrderAppFreezeModel model = new AlipayFundAuthOrderAppFreezeModel();
        model.setOutOrderNo(outOrderNo);
        model.setOutRequestNo(outRequestNo);
        model.setOrderTitle("预授权冻结,订单号: " + orderId);
        model.setAmount(amount.toString());
//        model.setProductCode("PRE_AUTH_ONLINE"); // 支付宝预授权
        model.setProductCode("PREAUTH_PAY");  // 预授权支付
        model.setPayeeUserId(payeeUserId);

        JSONObject extraParam = new JSONObject();
        String zfbCategoryCode = productShopCateReqDto.getZfbCode();
        extraParam.put("serviceId", serviceId);
        extraParam.put("category", StringUtil.isNotEmpty(zfbCategoryCode) ? zfbCategoryCode : defaultZfbCategoryCode);

        JSONObject creditExtInfo = new JSONObject();
        BigDecimal assessmentAmount = amount.divide(assessmentAmountDivisor).setScale(2, RoundingMode.UP);
        creditExtInfo.put("assessmentAmount", assessmentAmount.toString());

        JSONObject productInfo = ProductDistributeCache.getSeamProduct(channelId, productId);
        if (productInfo != null) {
            String url = urlPattern.replaceAll("APPID", appId).replaceAll("ITEMID", productId);
            creditExtInfo.put("rentGoodsId", productInfo.getString("seamProductId"));
            creditExtInfo.put("carrierDesc", productInfo.getString("productName"));
            creditExtInfo.put("carrierUrl", url);
            creditExtInfo.put("rentPeriod", rentPeriod.toString());
            creditExtInfo.put("rentAmount", rentAmount.toPlainString());
        }
        extraParam.put("creditExtInfo", creditExtInfo);
        model.setExtraParam(extraParam.toJSONString());
        request.setBizModel(model);
        request.setNotifyUrl(aliFreezeCallbackUrl);
        return request;
    }
}
