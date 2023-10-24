        
package com.rent.service.product;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.ShopFundFlowDto;
import com.rent.common.dto.product.ShopFundFlowReqDto;
import com.rent.common.dto.product.ShopSplitBillAccountDto;
import com.rent.common.dto.product.request.WithdrawApplyPageReq;
import com.rent.common.dto.product.resp.WithdrawApplyPageResp;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhaowenchao
 */
public interface ShopFundService {

        /**
         * 获取店铺资金账户余额
         * @param shopId
         * @return
         */
        BigDecimal getShopFundBalance(String shopId);

        /**
         * 运营查询店铺余额
         * @param request
         * @return
         */
        Page<ShopFundFlowDto> pageShopFundBalance(ShopFundFlowReqDto request);

        /**
         * 查询店铺资金账户信息
         * @param shopId
         * @return
         */
        ShopSplitBillAccountDto getShopAccountInfo(String shopId);


        /**
         * 分页查询店铺资金账户明细
         * @param request
         * @return
         */
        Page<ShopFundFlowDto> pageShopFundFlow(ShopFundFlowReqDto request);

        /**
         * 提现
         * @param amount
         * @return
         */
        Boolean withDraw(BigDecimal amount);

        /**
         * 商家提现审批 分页列表页面
         * @param request
         * @return
         */
        Page<WithdrawApplyPageResp> withDrawApplyPage(WithdrawApplyPageReq request);

        /**
         * 提现审核通过付款
         * @param id
         */
        void withDrawPass(Long id);

        /**
         * 充值
         * @param request
         * @return
         */
        String recharge(ShopFundFlowDto request);

        /**
         * 充值回调
         * @param outTradeNo
         * @param tradeNo
         * @param buyerId
         * @return
         */
        void rechargeCallBack(String outTradeNo,String tradeNo,String buyerId);

        /**
         * 凭证
         * @param id
         * @param shopId
         * @return
         */
        JSONObject prof(Long id, String shopId);

        /**
         * 佣金结算
         * @param shopId
         * @param changeAmount
         * @param accountPeriod
         * @return
         */
        Boolean brokerageSettle(String shopId,BigDecimal changeAmount,Long accountPeriod);

        /**
         * 获取结算的佣金的账期ID
         * @param id
         * @param shopId
         * @return
         */
        Long getBrokerageAccountPeriodId(Long id, String shopId);

        /**
         * 租押分离费用结算
         * @param shopId
         * @param amount
         * @return
         */
        Long assessmentFeeSettle(String shopId,BigDecimal amount);

        /**
         * 订单风控报告费用结算
         * @param shopId
         * @param amount
         * @param orderId
         * @return
         */
        Long shopFundOrderReportFee(String shopId,BigDecimal amount,String orderId);

        /**
         * 订单电子合同报告费用结算
         * @param shopId
         * @param amount
         * @param orderId
         * @return
         */
        Long shopFundContractFee(String shopId,BigDecimal amount,String orderId);

        /**
         * 分页查询渠道资金账户明细
         * @param uid
         * @return
         */
        List<ShopFundFlowDto> pageChannelFundFlow(String uid);

}