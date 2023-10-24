package com.rent.service.components.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.AlipayCallback;
import com.rent.common.converter.components.AliPayConverter;
import com.rent.common.converter.components.AlipayTradeSerialConverter;
import com.rent.common.converter.components.AlipayUnfreezeConverter;
import com.rent.common.dto.components.request.*;
import com.rent.common.dto.components.response.*;
import com.rent.common.dto.product.ProductShopCateReqDto;
import com.rent.common.enums.common.EnumComponentsError;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumPayType;
import com.rent.common.enums.components.EnumTradeResult;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.EnumOrderType;
import com.rent.common.util.GsonUtil;
import com.rent.common.util.SequenceTool;
import com.rent.common.util.StringUtil;
import com.rent.config.outside.AliPayNotifyFactory;
import com.rent.config.outside.OutsideConfig;
import com.rent.dao.components.*;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.*;
import com.rent.model.order.UserOrders;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.product.ProductService;
import com.rent.util.AppParamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rent.common.util.AliPayClientFactory.getAlipayClientByType;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-23 下午 6:22:15
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliPayCapitalServiceImpl implements AliPayCapitalService {

    public static final String SUCCESS = "SUCCESS";
    private static final String PAY_CHANNEL_HB_STAGE = "pcreditpayInstallment";

    private final AlipayFreezeDao aliPayFreezeDao;
    private final AlipayTradeSerialDao alipayTradeSerialDao;
    private final AlipayTradePayDao alipayTradePayDao;
    private final AlipayUnfreezeDao alipayUnfreezeDao;
    private final AlipayTradePagePayDao alipayTradePagePayDao;
    private final AlipayTradeRefundDao alipayTradeRefundDao;
    private final AlipayTransferRecordDao alipayTransferRecordDao;
    private final ProductService productService;
    private final AlipayTradeCreateDao alipayTradeCreateDao;
    private final UserOrdersDao userOrdersDao;

    @Override
    @Transactional
    public AliPayFreezeResponse aliPayFreeze(String orderId, String uid, BigDecimal amount, Long skuId,
                                             String productId, Integer freezeAgainFlag, EnumOrderType orderType, BigDecimal rentAmount,
                                             Integer rentPeriod) {
        String outRequestNo = SequenceTool.nextId();
        Date now = new Date();
        String channelId = AppParamUtil.getChannelId();
        log.info("[线上资金授权冻结接口] orderId: {}", orderId);
        String outOrderNo = orderId;
        AlipayFreeze alipayFreeze = new AlipayFreeze();
        alipayFreeze.setAmount(amount);
        alipayFreeze.setCreatedTime(now);
        alipayFreeze.setOrderId(orderId);
        if (freezeAgainFlag.equals(1)) {
            outOrderNo = SequenceTool.nextId();
        }
        alipayFreeze.setOutOrderNo(outOrderNo);
        alipayFreeze.setOutRequestNo(outRequestNo);
        alipayFreeze.setUid(uid);
        alipayFreeze.setStatus(EnumAliPayStatus.PAYING);
        alipayFreeze.setUpdateTime(now);
        EnumTradeType tradeType;
        switch (orderType) {
            case GENERAL_ORDER:
                tradeType = EnumTradeType.GENERAL_PLACE_ORDER;
                break;
            case RELET_ORDER:
                tradeType = EnumTradeType.RELET_PLACE_ORDER;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + orderType);
        }
        AlipayTradeSerial tradeSerial = AlipayTradeSerialConverter.assemblyAlipayTradeSerial(outOrderNo, outRequestNo,
                now, orderId, uid, amount, EnumPayType.FREEZE, tradeType);
        //查询商品类目信息
        ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(productId);
        //调用阿里云支付
        AlipayClient alipayClient = getAlipayClientByType();
        AlipayFundAuthOrderAppFreezeRequest request = AliPayConverter.buildAliPayFreezeRequest(outOrderNo, orderId,
                productShopCateReqDto, amount, channelId, AliPayNotifyFactory.aliFreezeCallbackUrl, outRequestNo, productId,
                rentAmount, rentPeriod);
        AlipayFundAuthOrderAppFreezeResponse response;
        String req = GsonUtil.objectToJsonString(request);
        alipayFreeze.setRequest(req);
        log.info("【线上资金授权冻结接口】调用支付宝接口入参：{}", req);
        try {
            response = alipayClient.sdkExecute(request);
            String res = GsonUtil.objectToJsonString(response);
            log.info("【线上资金授权冻结接口】调用支付宝出参：{}", res);
            alipayFreeze.setResponse(res);
            if (!response.isSuccess()) {
                log.error("aliPayService orderAppFreeze failed -->" + response.getBody());
                throw new HzsxBizException(EnumComponentsError.ALIPAY_FREEZE_ERROR.getCode(),
                        EnumComponentsError.ALIPAY_FREEZE_ERROR.getMsg(), this.getClass());
            }
        } catch (AlipayApiException e) {
            log.error("业务出现异常", e);
            throw new HzsxBizException(EnumComponentsError.ALIPAY_FREEZE_ERROR.getCode(),
                    EnumComponentsError.ALIPAY_FREEZE_ERROR.getMsg(), this.getClass());
        } finally {
            aliPayFreezeDao.save(alipayFreeze);
            alipayTradeSerialDao.save(tradeSerial);
        }
        return AliPayFreezeResponse.builder()
                .freezeUrl(response.getBody())
                .serialNo(outRequestNo)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AliPayTradePayResponse aliPayTradePay(String orderId, String outTradeNo, String authCode, String subject,
                                                 String notifyUrl, BigDecimal totalAmount, String payerUserId, List<String> periodList,
                                                 EnumTradeType tradeType) {
        EnumTradeResult tradeResult;
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        Date now = new Date();
        AlipayTradePayModel model = new AlipayTradePayModel();
        String sellerId = OutsideConfig.PARENT_ID;
        String channelId = AppParamUtil.getChannelId();
        log.info("[支付来源] out_trade_no：{},channelId:{},sellerId:{}", outTradeNo, channelId, sellerId);
        model.setSellerId(sellerId);
        AlipayClient alipayClient = getAlipayClientByType();
        log.info("开始 支付");
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        model.setOutTradeNo(outTradeNo);
        model.setProductCode("PRE_AUTH_ONLINE");
        model.setAuthNo(authCode);
        model.setBuyerId(payerUserId);
        model.setStoreId("");
        model.setSubject(subject);
        model.setAuthConfirmMode("NOT_COMPLETE");
        model.setTotalAmount(totalAmount.toString());
        model.setBody("预授权转支付");
        model.setTimeoutExpress("15d");

        ExtendParams params = new ExtendParams();
        model.setExtendParams(params);
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);

        AlipayTradeSerial tradeSerial = new AlipayTradeSerial();
        tradeSerial.setOrderId(orderId);
        tradeSerial.setPeriod(GsonUtil.objectToJsonString(periodList));
        tradeSerial.setUid(userOrders.getUid());
        tradeSerial.setOutOrderNo(outTradeNo);
        tradeSerial.setAmount(totalAmount);
        tradeSerial.setPayType(EnumPayType.FREEZE_TO_PAY);
        tradeSerial.setTradeType(tradeType);
        tradeSerial.setStatus(EnumAliPayStatus.PAYING);
        tradeSerial.setChannelId(userOrders.getChannelId());
        tradeSerial.setCreateTime(now);
        tradeSerial.setUpdateTime(now);
        //插入流水
        AlipayTradePay alipayTradePay = new AlipayTradePay();
        alipayTradePay.setOrderId(orderId);
        alipayTradePay.setPeriod(GsonUtil.objectToJsonString(periodList));
        alipayTradePay.setAuthCode(authCode);
        alipayTradePay.setOutTradeNo(outTradeNo);
        alipayTradePay.setSubject(subject);
        alipayTradePay.setAmount(totalAmount);
        alipayTradePay.setStatus(EnumAliPayStatus.PAYING);
        alipayTradePay.setCreateTime(now);
        alipayTradePay.setUpdateTime(now);
        String req = GsonUtil.objectToJsonString(request);
        alipayTradePay.setRequest(req);
        String tradeNo = null;
        try {
            log.info("订单：{}冻结转支付入参：{}", orderId, GsonUtil.objectToJsonString(req));
            AlipayTradePayResponse response = alipayClient.certificateExecute(request);
            log.info("订单：{}冻结转支付出参：{}", orderId, GsonUtil.objectToJsonString(response));
            alipayTradePay.setResponse(GsonUtil.objectToJsonString(response));
            //更新交易号
            JSONObject js = JSONObject.parseObject(response.getBody())
                    .getJSONObject("alipay_trade_pay_response");
            tradeNo = js.getString("trade_no");
            alipayTradePay.setTradeNo(tradeNo);
            tradeSerial.setSerialNo(tradeNo);

            if (!response.isSuccess()) {
                // log.info("aliPayService alipayTradePay failed -->" + response.getBody());
                alipayTradePay.setStatus(EnumAliPayStatus.FAILED);
                tradeSerial.setStatus(EnumAliPayStatus.FAILED);
                tradeResult = EnumTradeResult.FAILED;
            } else if ("10003".equals(js.getString("code"))) {
                //用户支付宝 超限额 风控未过
                log.info("aliPayService alipayTradePay failed --> 10003" + response.getBody());
                //限额使用支付中
                alipayTradePay.setStatus(EnumAliPayStatus.PAYING);
                tradeSerial.setStatus(EnumAliPayStatus.PAYING);
                tradeResult = EnumTradeResult.LIMIT_AMOUNT;
            } else {
                alipayTradePay.setStatus(EnumAliPayStatus.PAYING);
                tradeSerial.setStatus(EnumAliPayStatus.PAYING);
                tradeResult = EnumTradeResult.SUCCESS;
            }
            return AliPayTradePayResponse.builder()
                    .resultMessage(response.getSubMsg())
                    .tradeResult(tradeResult)
                    .tradeNo(alipayTradePay.getTradeNo())
                    .outTradeNo(alipayTradePay.getOutTradeNo())
                    .build();
        } catch (AlipayApiException e) {
            log.info("业务出现异常", e);
            throw new HzsxBizException("9999", " 业务出现异常 182 aliPayService getOrderAppTradePay 异常");
        } finally {
            alipayTradePayDao.saveOrUpdate(alipayTradePay, new QueryWrapper<>(AlipayTradePay.builder()
                    .outTradeNo(outTradeNo)
                    .status(EnumAliPayStatus.PAYING)
                    .build()));
            if (StringUtil.isNotEmpty(tradeNo)) {
                alipayTradeSerialDao.saveOrUpdate(tradeSerial, new QueryWrapper<>(AlipayTradeSerial.builder()
                        .serialNo(tradeNo)
                        .status(EnumAliPayStatus.PAYING)
                        .build()));
            } else {
                alipayTradeSerialDao.save(tradeSerial);
            }

        }
    }

    @Override
    public AliPayTradePayResponse orderByStageAliPayTradePay(String orderId, String subject,
                                                             BigDecimal totalAmount, List<String> periodList, EnumTradeType tradeType) {
        AlipayFreeze alipayFreeze = aliPayFreezeDao.selectOneByOrderId(orderId, EnumAliPayStatus.SUCCESS);
        if (null == alipayFreeze) {
            throw new HzsxBizException(EnumComponentsError.FREEZE_RECORD_NOT_EXISTS.getCode(),
                    EnumComponentsError.FREEZE_RECORD_NOT_EXISTS.getMsg(), this.getClass());
        }
        String outOrderNo = "ATP" + orderId + "_" + String.join("", periodList);
        return this.aliPayTradePay(orderId, outOrderNo, alipayFreeze.getAuthNo(), subject, AliPayNotifyFactory.stageOrderAlipayCallback,
                totalAmount, alipayFreeze.getPayerUserId(), periodList, tradeType);
    }

    @Override
    public AliPayOperationDetailResponse alipayOperationDetailQuery(String authNo, String outOrderNo,
                                                                    String outRequestNo, String channelId) {
        AlipayClient alipayClient = getAlipayClientByType();
        AlipayFundAuthOperationDetailQueryRequest request = new AlipayFundAuthOperationDetailQueryRequest();
        AlipayFundAuthOperationDetailQueryModel model = new AlipayFundAuthOperationDetailQueryModel();
        model.setAuthNo(authNo);
        model.setOutOrderNo(outOrderNo);
        model.setOutRequestNo(outRequestNo);
        request.setBizModel(model);

        AlipayFundAuthOperationDetailQueryResponse response;
        try {
            response = alipayClient.certificateExecute(request);
            if (!response.isSuccess()) {
                log.error("aliPayService alipayOperationDetailQuery failed -->" + response.getBody());
                throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(),
                        EnumComponentsError.RPC_ERROR.getMsg(), this.getClass());
            }
        } catch (AlipayApiException e) {
            log.error("业务出现异常", e);
            throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(), EnumComponentsError.RPC_ERROR.getMsg(),
                    this.getClass());
        }
        AliPayOperationDetailResponse resp = new AliPayOperationDetailResponse();
        BeanUtil.copyProperties(response, resp);
        return resp;
    }

    @Override
    public AlipayUnfreezeResponse alipayOrderUnfreeze(AlipayUnfreezeRequest unfreezeRequest) {
        //获取回调地址
        String notifyUrl = AliPayNotifyFactory.getAliPayNotifyUrl(EnumPayType.UNFREEZE);

        AlipayClient alipayClient = getAlipayClientByType();
        log.info("解冻开始,参数:{}", GsonUtil.objectToJsonString(unfreezeRequest));
        String outRequestNo = SequenceTool.nextId();
        Date now = new Date();
        //新增解冻记录表
        AlipayUnfreeze unfreeze = AlipayUnfreezeConverter.assemblyAlipayUnfreeze(unfreezeRequest, outRequestNo, now);
        //插入流水
        AlipayTradeSerial tradeSerial = AlipayTradeSerialConverter.assemblyAlipayTradeSerial(null, outRequestNo, now,
                unfreezeRequest.getOrderId(), unfreezeRequest.getUid(), unfreezeRequest.getAmount(), EnumPayType.UNFREEZE,
                unfreezeRequest.getTradeType());
        //请求参数
        AlipayFundAuthOrderUnfreezeRequest request = new AlipayFundAuthOrderUnfreezeRequest();
        AlipayFundAuthOrderUnfreezeModel model = new AlipayFundAuthOrderUnfreezeModel();
        model.setAuthNo(unfreezeRequest.getAuthNo());
        model.setOutRequestNo(outRequestNo);
        model.setAmount(unfreezeRequest.getAmount()
                .toString());
        model.setRemark(unfreezeRequest.getRemark());
        request.setBizModel(model);
        if (StringUtil.isEmpty(unfreezeRequest.getNotify_url())) {
            request.setNotifyUrl(notifyUrl);
        } else {
            request.setNotifyUrl(unfreezeRequest.getNotify_url());
        }
        String req = GsonUtil.objectToJsonString(request);
        unfreeze.setRequest(req);
        AlipayFundAuthOrderUnfreezeResponse response;
        try {
            log.info("订单：{}授权解冻入参为：{}", unfreezeRequest.getOrderId(), req);
            response = alipayClient.certificateExecute(request);
            String res = GsonUtil.objectToJsonString(response);
            log.info("授权解冻出参信息:{}", res);
            unfreeze.setResponse(res);
            tradeSerial.setOutOrderNo(response.getOutOrderNo());
            unfreeze.setOperationId(response.getOperationId());
            if (!response.isSuccess()) {
                log.info("授权解冻失败 -->" + response.getBody());
                tradeSerial.setStatus(EnumAliPayStatus.FAILED);
                unfreeze.setStatus(EnumAliPayStatus.FAILED);
                throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(),
                        EnumComponentsError.RPC_ERROR.getMsg(), this.getClass());
            }
            AlipayUnfreezeResponse unfreezeResponse = new AlipayUnfreezeResponse();
            BeanUtil.copyProperties(response, unfreezeResponse);
            return unfreezeResponse;
        } catch (AlipayApiException e) {
            log.info("业务出现异常", e);
            tradeSerial.setStatus(EnumAliPayStatus.FAILED);
            // tradeSerial.setOutOrderNo(response.getOutOrderNo());
            // unfreeze.setOperationId(response.getOperationId());
            unfreeze.setStatus(EnumAliPayStatus.FAILED);
            throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(), EnumComponentsError.RPC_ERROR.getMsg(),
                    this.getClass());
        } finally {
            alipayUnfreezeDao.save(unfreeze);
            alipayTradeSerialDao.save(tradeSerial);
        }
    }

    @Override
    public AlipayAppPayResponse alipayTradePagePay(AliPayTradeAppPageRequest pageRequest) {
        //校验参数
        String notifyUrl = AliPayNotifyFactory.getAliPayNotifyUrl(EnumPayType.PAGE_PAY);
        //流水号
        String serialNo = SequenceTool.nextId();
        //组装交易流水，page支付记录
        Date now = new Date();
        AlipayTradePagePay alipayTradePagePay = getAlipayTradePagePay(pageRequest, now);
        alipayTradePagePay.setTradeNo(serialNo);
        AlipayTradeSerial alipayTradeSerial = AlipayTradeSerialConverter.assemblyAlipayTradeSerial(
                pageRequest.getOutTradeNo(), null, now, pageRequest.getShopId(), null, pageRequest.getTotalAmount(),
                EnumPayType.PAGE_PAY, pageRequest.getTradeType());
        alipayTradeSerial.setSerialNo(serialNo);

        AlipayClient alipayClient = getAlipayClientByType();
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(AliPayNotifyFactory.pagePayReturnUrl);
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setTotalAmount(pageRequest.getTotalAmount()
                .toPlainString());
        model.setSubject(pageRequest.getSubject());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        model.setOutTradeNo(pageRequest.getOutTradeNo());
        model.setEnablePayChannels("pcredit,balance,moneyFund,debitCardExpress,pcreditpayInstallment");
        try {
            String passback_params2 = URLEncoder.encode(serialNo, "UTF-8");
            model.setPassbackParams(passback_params2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new HzsxBizException("999999", e.getMessage());
        }
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);//异步通知地址
        alipayTradePagePay.setRequest(GsonUtil.objectToJsonString(request));
        try {
            log.info("【page收单支付接口】request:{}", GsonUtil.objectToJsonString(request));
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
            log.info("【page收单支付接口】response:{}", GsonUtil.objectToJsonString(response));

            alipayTradePagePay.setResponse(GsonUtil.objectToJsonString(response));
            if (!response.isSuccess()) {
                log.error("aliPayService alipayTradePagePay failed -->" + response.getBody());
                alipayTradePagePay.setStatus(EnumAliPayStatus.FAILED);
                alipayTradeSerial.setStatus(EnumAliPayStatus.FAILED);
                throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(),
                        EnumComponentsError.RPC_ERROR.getMsg(), this.getClass());
            }
            //返回支付url
            AlipayAppPayResponse alipayAppPayResponse = new AlipayAppPayResponse();
            alipayAppPayResponse.setPayUrl(response.getBody());
            BeanUtil.copyProperties(response, alipayAppPayResponse);
            alipayAppPayResponse.setSerialNo(serialNo);
            return alipayAppPayResponse;
        } catch (AlipayApiException e) {
            alipayTradePagePay.setStatus(EnumAliPayStatus.FAILED);
            alipayTradeSerial.setStatus(EnumAliPayStatus.FAILED);
            log.error("业务出现异常", e);
            throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(), EnumComponentsError.RPC_ERROR.getMsg(),
                    this.getClass());
        } finally {
            alipayTradePagePayDao.save(alipayTradePagePay);
            alipayTradeSerialDao.save(alipayTradeSerial);
        }
    }

    @Override
    public AliPayTradeCreateResponse alipayTradeCreate(AliPayTradeCreateRequest createRequest) {

        AlipayClient alipayClient = getAlipayClientByType();
        String notifyUrl = AliPayNotifyFactory.getAliPayNotifyUrl(EnumPayType.TRADE_CREATE);
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();

        String serialNo = SequenceTool.nextId();
        Date now = new Date();
        AlipayTradeSerial alipayTradeSerial = AlipayTradeSerialConverter.assemblyAlipayTradeSerial(
                createRequest.getOutTradeNo(), null, now, createRequest.getOrderId(), createRequest.getUid(),
                createRequest.getTotalAmount(), EnumPayType.TRADE_CREATE, createRequest.getTradeType());
        alipayTradeSerial.setSerialNo(serialNo);
        if (createRequest.getPeriodList() != null) {
            alipayTradeSerial.setPeriod(GsonUtil.objectToJsonString(createRequest.getPeriodList()));
        }
        AlipayTradeCreate alipayTradeCreate = getAlipayTradeCreate(createRequest, now);
        alipayTradeCreate.setTradeNo(serialNo);

        model.setOutTradeNo(createRequest.getOutTradeNo());
        model.setTotalAmount(createRequest.getTotalAmount()
                .toPlainString());
        model.setSubject(createRequest.getSubject());
        model.setBuyerId(createRequest.getBuyerId());
        // TODO 当前支付宝openid 需要申请如果是获取得user_id得话openid传空
        //        https://opendocs.alipay.com/pre-open/07tx1k?pathHash=85dcc9f9
//        model.setBuyerOpenId(createRequest.getBuyerId());
        if (StringUtils.isNotEmpty(createRequest.getProductId())) {
            List<GoodsDetail> goodsDetails = new ArrayList<>();
            GoodsDetail goodsDetail = new GoodsDetail();
            goodsDetail.setGoodsId(createRequest.getProductId());
            goodsDetail.setGoodsName("goodsName");
            goodsDetail.setQuantity(1L);
            goodsDetail.setPrice(createRequest.getTotalAmount()
                    .toString());
            goodsDetails.add(goodsDetail);
            model.setGoodsDetail(goodsDetails);
        }
        if (StringUtil.isNotEmpty(createRequest.getHbPeriodNum())) {
            model.setEnablePayChannels(PAY_CHANNEL_HB_STAGE);
            ExtendParams extendParams = new ExtendParams();
            extendParams.setHbFqSellerPercent(createRequest.getShopPayHbFee() ? "100" : "0");
            extendParams.setHbFqNum(createRequest.getHbPeriodNum());
            model.setExtendParams(extendParams);
        }
        //讲流水号设置为回传参数
        try {
            String passback_params = URLEncoder.encode(serialNo, "UTF-8");
            model.setPassbackParams(passback_params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new HzsxBizException("999999", e.getMessage());
        }
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);//异步通知地址
        alipayTradeCreate.setRequest(GsonUtil.objectToJsonString(request));
        try {
            log.info("【app收单创建接口】request:{}", GsonUtil.objectToJsonString(request));
            AlipayTradeCreateResponse response = alipayClient.certificateExecute(request);
            log.info("【app收单创建接口】response:{}", GsonUtil.objectToJsonString(response));
            alipayTradeCreate.setResponse(GsonUtil.objectToJsonString(response));
            if (!response.isSuccess()) {
                log.error("aliPayService alipayTradeCreate failed -->" + response.getBody());
                alipayTradeCreate.setStatus(EnumAliPayStatus.FAILED);
                alipayTradeSerial.setStatus(EnumAliPayStatus.FAILED);
                throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(),
                        EnumComponentsError.RPC_ERROR.getMsg(), this.getClass());
            }
            AliPayTradeCreateResponse alipayAppPayResponse = new AliPayTradeCreateResponse();
            alipayAppPayResponse.setTradeNo(response.getTradeNo());
            return alipayAppPayResponse;
        } catch (Exception e) {
            log.error("系统异常", e);
            alipayTradeSerial.setStatus(EnumAliPayStatus.FAILED);
            alipayTradeCreate.setStatus(EnumAliPayStatus.FAILED);
            throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(), EnumComponentsError.RPC_ERROR.getMsg(),
                    this.getClass());
        } finally {
            alipayTradeCreateDao.save(alipayTradeCreate);
            alipayTradeSerialDao.save(alipayTradeSerial);
        }
    }

    @Override
    public AlipayRefundResponse alipayTradeRefund(AlipayOrderRefundRequest refundRequest) {
        String outRequestNo = SequenceTool.nextId();
        //组装交易流水，退款流水记录
        Date now = new Date();
        AlipayTradeRefund alipayTradeRefund = getAlipayTradeRefund(refundRequest, outRequestNo, now);
        AlipayTradeSerial alipayTradeSerial = AlipayTradeSerialConverter.assemblyAlipayTradeSerial(
                refundRequest.getOutTradeNo(), outRequestNo, now, refundRequest.getOrderId(), refundRequest.getUid(),
                refundRequest.getRefundAmount(), EnumPayType.REFUND, refundRequest.getTradeType());

        //调用支付宝退款接口
        AlipayClient alipayClient = getAlipayClientByType();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setTradeNo(refundRequest.getTradeNo());
        model.setOutRequestNo(outRequestNo);
        model.setRefundAmount(refundRequest.getRefundAmount().toString());
        model.setRefundReason(refundRequest.getRefundReason());
        request.setBizModel(model);
        String req = GsonUtil.objectToJsonString(request);
        alipayTradeRefund.setRequest(req);
        AlipayTradeRefundResponse response;
        try {
            log.info("订单：{}退款请求参数：{}", refundRequest.getOrderId(), req);
            response = alipayClient.certificateExecute(request);
            String res = GsonUtil.objectToJsonString(response);
            log.info("订单：{}退款响应参数：{}", refundRequest.getOrderId(), res);
            alipayTradeRefund.setResponse(res);
            for (int i = 0; i < 3; i++) {
                if (!response.isSuccess()) {
                    response = alipayClient.certificateExecute(request);
                    log.error("aliPayService alipayTradeRefund failed -->" + response.getBody());
                    alipayTradeRefund.setStatus(EnumAliPayStatus.FAILED);
                    alipayTradeSerial.setStatus(EnumAliPayStatus.FAILED);
                } else {
                    alipayTradeRefund.setStatus(EnumAliPayStatus.SUCCESS);
                    alipayTradeSerial.setStatus(EnumAliPayStatus.SUCCESS);
                    break;
                }
            }
            if (!response.isSuccess()) {
                throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(), "调用退款接口失败", this.getClass());
            }
            return AlipayRefundResponse.builder()
                    .build();
        } catch (AlipayApiException e) {
            log.error("业务出现异常", e);
            alipayTradeRefund.setStatus(EnumAliPayStatus.FAILED);
            alipayTradeSerial.setStatus(EnumAliPayStatus.FAILED);
            throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(), "调用退款接口失败", this.getClass());
        } finally {
            alipayTradeRefundDao.save(alipayTradeRefund);
            alipayTradeSerialDao.save(alipayTradeSerial);
        }
    }

    @Override
    public AliPayTransferRespModel transfer(String outBizNo, BigDecimal amount, String identity, String name,
                                            String remark, String payerShowName, String userId) {

        AliPayTransferRespModel aliPayTransferRespModel = new AliPayTransferRespModel().setOutBizNo(outBizNo);

        //记录
        Date now = new Date();
        AlipayTransferRecord alipayTransferRecord = new AlipayTransferRecord().setOutBizNo(outBizNo)
                .setIdentity(identity)
                .setName(name)
                .setAmount(amount)
                .setRemark(remark)
                .setCreateTime(now);
        JSONObject bizContent = new JSONObject();
        log.info("【转账到支付宝账户】outBizNo={},identity={},amount={}", outBizNo, identity, amount);
        try {
            //1.获取客户端
            AlipayClient alipayClient = getAlipayClientByType();

            //2.拼装请求参数
            AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
            bizContent.put("out_biz_no", outBizNo);
            bizContent.put("trans_amount", amount);
            bizContent.put("product_code", "TRANS_ACCOUNT_NO_PWD");
            bizContent.put("biz_scene", "DIRECT_TRANSFER");
            bizContent.put("order_title", "");
            JSONObject payeeInfo = new JSONObject();

            if (StringUtil.isNotEmpty(userId)) {
                payeeInfo.put("identity_type", "ALIPAY_USER_ID");
                payeeInfo.put("identity", userId);
            } else {
                payeeInfo.put("identity_type", "ALIPAY_LOGON_ID");
                payeeInfo.put("identity", identity);
                payeeInfo.put("name", name);
            }
            bizContent.put("payee_info", payeeInfo.toJSONString());
            bizContent.put("remark", remark);
            JSONObject businessParams = new JSONObject();
            businessParams.put("payer_show_name", payerShowName);
            bizContent.put("business_params", businessParams.toJSONString());
            request.setBizContent(bizContent.toJSONString());

            alipayTransferRecord.setReqParams(GsonUtil.objectToJsonString(request));
            //3.执行请求
            AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);
            log.info("【转账到支付宝账户】outBizNo={},identity={},name={},amount={},resp={}", outBizNo, identity, name, amount,
                    response.getBody());
            if (response.isSuccess()) {
                alipayTransferRecord.setStatus(EnumAliPayStatus.SUCCESS);
                alipayTransferRecord.setResp(response.getBody());
            } else {
                alipayTransferRecord.setStatus(EnumAliPayStatus.FAILED)
                        .setErrorMsg(response.getSubMsg())
                        .setResp(response.getBody());
            }
            aliPayTransferRespModel.setPayFundOrderId(response.getPayFundOrderId());
            aliPayTransferRespModel.setStatus(response.getStatus());
        } catch (Exception e) {
            //4.保存请求记录
            alipayTransferRecord.setStatus(EnumAliPayStatus.FAILED).setErrorMsg(e.getMessage());
            log.error("【转账到支付宝账户-异常】", e);
            aliPayTransferRespModel.setStatus("ERROR");
        } finally {
            alipayTransferRecordDao.save(alipayTransferRecord);
            return aliPayTransferRespModel;
        }
    }

    @Override
    public void alipayTradeOrderInfoSync(String orderId, String tradeNo, String outRequestNo, String bizType,
                                         String orderBizInfo) {
        log.info("【支付宝订单信息同步接口】- tradeNo={},bizType={},orderBizInfo={}", tradeNo, bizType, orderBizInfo);
        AlipayClient alipayClient = getAlipayClientByType();
        AlipayTradeOrderinfoSyncRequest request = new AlipayTradeOrderinfoSyncRequest();
        AlipayTradeOrderinfoSyncModel model = new AlipayTradeOrderinfoSyncModel();
        model.setTradeNo("".equals(tradeNo) ? null : tradeNo);
        model.setOutRequestNo(outRequestNo);
        model.setBizType(bizType);
        model.setOrderBizInfo(orderBizInfo);
        request.setBizModel(model);

        AlipayTradeOrderinfoSyncResponse response;
        try {
            response = alipayClient.certificateExecute(request);
            if (!response.isSuccess()) {
                log.error("aliPayService alipayTradeOrderinfoSync failed -->" + response.getBody());
            } else {
                log.info("【支付宝订单信息同步接口】- 请求成功 -tradeNo={}", tradeNo, bizType, orderBizInfo);
            }

        } catch (AlipayApiException e) {
            log.error("业务出现异常", e);
            throw new HzsxBizException("99999", "aliPayService alipayTradeOrderinfoSync 异常");
        }
    }

    @Override
    public void alipayTradeOrderInfoSync(AliPayTradeSyncReq req) {
        for (String period : req.getPeriods()) {
            String outTradeNo = new StringBuilder("ATP").append(req.getOrderId())
                    .append("_")
                    .append(period)
                    .toString();
            AlipayTradePay alipayTradePay = alipayTradePayDao.getByTradeNoIfAllFailed(outTradeNo);
            if (alipayTradePay != null) {
                alipayTradeOrderInfoSync(alipayTradePay.getOrderId(), alipayTradePay.getTradeNo(),
                        alipayTradePay.getOutTradeNo(), "CREDIT_AUTH", AlipayCallback.TRADE_SYNC_BIZ_INFO_COMPLETE);
            }
        }
    }

    @Override
    public AliPayOperationDetailResponse alipayOperationDetailQuery(String orderId, String channelId) {
        AlipayFreeze alipayFreeze = aliPayFreezeDao.selectOneByOrderId(orderId, EnumAliPayStatus.SUCCESS);
        if (null == alipayFreeze) {
            log.error("丁订单没有成功预授权记录");
            return null;
        }
        return this.alipayOperationDetailQuery(alipayFreeze.getAuthNo(), alipayFreeze.getOutOrderNo(),
                alipayFreeze.getOutRequestNo(), channelId);
    }


    @Override
    public AlipayTradeQueryResponse alipayTradeQuery(AlipayTradePayQueryRequest request) {
        AlipayClient alipayClient = getAlipayClientByType();
        AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setTradeNo(request.getTradeNo());
        model.setOutTradeNo(request.getOutTradeNo());
        model.setOrgPid(request.getOrgPid());
        alipayTradeQueryRequest.setBizModel(model);

        AlipayTradeQueryResponse response;
        try {
            log.info("订单：{}查询交易结果请求参数：{}", request.getOrderId(), GsonUtil.objectToJsonString(alipayTradeQueryRequest));
            response = alipayClient.certificateExecute(alipayTradeQueryRequest);
            log.info("订单：{}查询交易结果响应参数：{}", request.getOrderId(), response.getBody());
        } catch (AlipayApiException e) {
            log.error("业务出现异常", e);
            throw new HzsxBizException("999999", "aliPayService alipayTradeQuery 异常");
        }
        return response;
    }

    @Override
    public AlipayTradeCloseResponse alipayTradeClose(String outTradeNo, String tradeNo) {
        AlipayClient alipayClient = getAlipayClientByType();
        AlipayTradeCloseRequest alipayTradeCloseRequest = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo(outTradeNo);
        model.setTradeNo(tradeNo);
        alipayTradeCloseRequest.setBizModel(model);
        AlipayTradeCloseResponse response;
        try {
            log.info("订单：{}查询交易结果请求参数：{}", outTradeNo, GsonUtil.objectToJsonString(alipayTradeCloseRequest));
            response = alipayClient.certificateExecute(alipayTradeCloseRequest);
            log.info("订单：{}查询交易结果响应参数：{}", outTradeNo, response.getBody());
        } catch (AlipayApiException e) {
            log.error("业务出现异常", e);
            throw new HzsxBizException("999999", "aliPayService alipayTradeClose 异常");
        }
        return response;
    }

    private AlipayTradeRefund getAlipayTradeRefund(AlipayOrderRefundRequest refundRequest, String outRequestNo,
                                                   Date now) {
        AlipayTradeRefund alipayTradeRefund = new AlipayTradeRefund();
        alipayTradeRefund.setOrderId(refundRequest.getOrderId());
        alipayTradeRefund.setOutTradeNo(refundRequest.getOutTradeNo());
        alipayTradeRefund.setTradeNo(refundRequest.getTradeNo());
        alipayTradeRefund.setOutRequestNo(outRequestNo);
        alipayTradeRefund.setRefundAmount(refundRequest.getRefundAmount());
        alipayTradeRefund.setRefundReason(refundRequest.getRefundReason());
        alipayTradeRefund.setStatus(EnumAliPayStatus.PAYING);
        alipayTradeRefund.setCreateTime(now);
        alipayTradeRefund.setUpdateTime(now);
        return alipayTradeRefund;
    }

    private AlipayTradePagePay getAlipayTradePagePay(AliPayTradeAppPageRequest pagePayRequest, Date now) {
        AlipayTradePagePay alipayTradePagePay = new AlipayTradePagePay();
        alipayTradePagePay.setShopId(pagePayRequest.getShopId());
        alipayTradePagePay.setOutTradeNo(pagePayRequest.getOutTradeNo());
        alipayTradePagePay.setSubject(pagePayRequest.getSubject());
        alipayTradePagePay.setTradeType(pagePayRequest.getTradeType());
        alipayTradePagePay.setAmount(pagePayRequest.getTotalAmount());
        alipayTradePagePay.setStatus(EnumAliPayStatus.PAYING);
        alipayTradePagePay.setCreateTime(now);
        alipayTradePagePay.setUpdateTime(now);
        return alipayTradePagePay;
    }


    private AlipayTradeCreate getAlipayTradeCreate(AliPayTradeCreateRequest appPayRequest, Date now) {
        AlipayTradeCreate alipayTradeCreate = new AlipayTradeCreate();
        alipayTradeCreate.setOrderId(appPayRequest.getOrderId());
        alipayTradeCreate.setOutTradeNo(appPayRequest.getOutTradeNo());
        alipayTradeCreate.setSubject(appPayRequest.getSubject());
        alipayTradeCreate.setTradeType(appPayRequest.getTradeType());
        alipayTradeCreate.setAmount(appPayRequest.getTotalAmount());
        alipayTradeCreate.setStatus(EnumAliPayStatus.PAYING);
        alipayTradeCreate.setCreateTime(now);
        alipayTradeCreate.setUpdateTime(now);
        return alipayTradeCreate;
    }
}
