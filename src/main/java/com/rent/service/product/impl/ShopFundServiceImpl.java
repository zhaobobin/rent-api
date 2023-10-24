
package com.rent.service.product.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.product.ShopFundFlowConverter;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.components.request.AliPayTradeAppPageRequest;
import com.rent.common.dto.components.request.AliPayTransferRespModel;
import com.rent.common.dto.components.response.AlipayAppPayResponse;
import com.rent.common.dto.product.ShopFundFlowDto;
import com.rent.common.dto.product.ShopFundFlowReqDto;
import com.rent.common.dto.product.ShopSplitBillAccountDto;
import com.rent.common.dto.product.request.WithdrawApplyPageReq;
import com.rent.common.dto.product.resp.WithdrawApplyPageResp;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.product.EnumShopFundOperate;
import com.rent.common.enums.product.EnumShopFundStatus;
import com.rent.common.enums.product.EnumShopWithdrawApplyStatus;
import com.rent.config.outside.OutsideConfig;
import com.rent.dao.product.ShopDao;
import com.rent.dao.product.ShopFundFlowDao;
import com.rent.dao.product.ShopSplitBillAccountDao;
import com.rent.dao.product.ShopWithdrawApplyDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.Shop;
import com.rent.model.product.ShopFundFlow;
import com.rent.model.product.ShopSplitBillAccount;
import com.rent.model.product.ShopWithdrawApply;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.product.ShopFundService;
import com.rent.util.LoginUserUtil;
import com.rent.util.RandomUtil;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ShopFundServiceImpl implements ShopFundService {

    private final ShopFundFlowDao shopFundFlowDao;
    private final ShopSplitBillAccountDao shopSplitBillAccountDao;
    private final ShopDao shopDao;
    private final AliPayCapitalService aliPayCapitalService;
    private final ShopWithdrawApplyDao shopWithdrawApplyDao;

    @Override
    public Page<ShopFundFlowDto> pageShopFundBalance(ShopFundFlowReqDto request) {
        Page<Shop> page = shopDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<Shop>().like(StringUtil.isNotEmpty(request.getShopName()),"name",request.getShopName()));
        List<Shop> shopList = page.getRecords();
        if(CollectionUtil.isEmpty(shopList)){
            return new Page<>(request.getPageNumber(), request.getPageSize());
        }
        List<ShopFundFlowDto> shopFundFlowDtoList = new ArrayList<>(shopList.size());
        for (Shop shop : shopList) {
            ShopFundFlowDto shopFundFlowDto = new ShopFundFlowDto();
            shopFundFlowDto.setShopId(shop.getShopId());
            shopFundFlowDto.setShopName(shop.getName());
            shopFundFlowDto.setAfterAmount(getShopFundBalance(shop.getShopId()));
            shopFundFlowDtoList.add(shopFundFlowDto);
        }
        return new Page<ShopFundFlowDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(shopFundFlowDtoList);
    }

    @Override
    public ShopSplitBillAccountDto getShopAccountInfo(String shopId) {
        ShopSplitBillAccountDto dto = new ShopSplitBillAccountDto();
        List<ShopSplitBillAccount> list = shopSplitBillAccountDao.getByPassedShopId(shopId);
        if(CollectionUtil.isNotEmpty(list)){
            ShopSplitBillAccount account = list.get(0);
            dto.setIdentity(account.getIdentity());
            dto.setShopName(account.getShopName());
            dto.setName(account.getName());
        }
        return dto;
    }

    @Override
    public BigDecimal getShopFundBalance(String shopId) {
        ShopFundFlow flow =  shopFundFlowDao.getLastSuccessFlow(shopId);
        if(flow!=null){
            return flow.getAfterAmount();
        }else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public Page<ShopFundFlowDto> pageShopFundFlow(ShopFundFlowReqDto request) {
        Page<ShopFundFlow> page = shopFundFlowDao.pageShopFundFlow(request);
        return new Page<ShopFundFlowDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(ShopFundFlowConverter.modelList2DtoList(page.getRecords()));
    }

    @Override
    public Boolean withDraw(BigDecimal amount) {
        LoginUserBo loginUserBo = LoginUserUtil.getLoginUser();
        String shopId = loginUserBo.getShopId();
        String redisLockKey = "WITHDRAW::"+shopId;
        if(amount.compareTo(new BigDecimal(50000))>0){
            throw new HzsxBizException("-1","单笔提现金额不得大于5万");
        }
        List<ShopWithdrawApply> pendingApply = shopWithdrawApplyDao.list(new QueryWrapper<ShopWithdrawApply>()
                .eq("shop_id",shopId).eq("status",EnumShopWithdrawApplyStatus.PENDING));
        if(CollectionUtil.isNotEmpty(pendingApply)){
            throw new HzsxBizException("-1","有审核中的提现申请");
        }
        try {
            if(RedisUtil.tryLock(redisLockKey,60)){
                BigDecimal balance = getShopFundBalance(shopId);
                if(amount.compareTo(balance)>0){
                    throw new HzsxBizException("-1","提现金额大于余额");
                }
                ShopSplitBillAccountDto accountDto = getShopAccountInfo(shopId);
                if(StringUtils.isEmpty(accountDto.getIdentity()) || StringUtils.isEmpty(accountDto.getName())){
                    throw new HzsxBizException("-1","提现账户配置错误");
                }
                ShopWithdrawApply apply = new ShopWithdrawApply();
                apply.setShopId(shopId);
                apply.setApplyUid(loginUserBo.getId());
                apply.setAmount(amount);
                apply.setStatus(EnumShopWithdrawApplyStatus.PENDING);
                apply.setCreateTime(new Date());
                String orderNo = System.currentTimeMillis()+"_"+ RandomUtil.randomNumbers(6);
                apply.setOutOrderNo(orderNo);
                shopWithdrawApplyDao.save(apply);
                return Boolean.TRUE;
            }else {
                throw new HzsxBizException("-1","商户资金账户操作中，请稍后再试");
            }
        }catch (Exception e){
            throw e;
        }finally{
            RedisUtil.unLock(redisLockKey);
        }
    }

    @Override
    public Page<WithdrawApplyPageResp> withDrawApplyPage(WithdrawApplyPageReq request) {
        return shopWithdrawApplyDao.withDrawApplyPage(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withDrawPass(Long id) {
        ShopWithdrawApply apply = shopWithdrawApplyDao.getById(id);
        String shopId = apply.getShopId();
        String redisLockKey = "WITHDRAW::"+apply.getId();
        try {
            if(RedisUtil.tryLock(redisLockKey,60)){
                BigDecimal balance = getShopFundBalance(shopId);
                if(balance.compareTo(apply.getAmount())<0){
                    throw new HzsxBizException("-1","商家账户余额不足");
                }
                ShopSplitBillAccountDto accountDto = getShopAccountInfo(shopId);
                AliPayTransferRespModel resp = aliPayCapitalService.transfer(apply.getOutOrderNo(),apply.getAmount(),accountDto.getIdentity(),accountDto.getName(),"商家资金账户提现", OutsideConfig.APP_NAME,null);
                if("SUCCESS".equals(resp.getStatus())){
                    Date now = new Date();
                    ShopFundFlow shopFundFlow = new ShopFundFlow();
                    shopFundFlow.setShopId(shopId);
                    shopFundFlow.setOperator(LoginUserUtil.getLoginUser().getMobile());
                    shopFundFlow.setChangeAmount(apply.getAmount());
                    shopFundFlow.setOperate(EnumShopFundOperate.WITHDRAW);
                    shopFundFlow.setFlowNo(resp.getPayFundOrderId());
                    shopFundFlow.setStatus(EnumShopFundStatus.SUCCESS);
                    shopFundFlow.setBeforeAmount(balance);
                    shopFundFlow.setAfterAmount(shopFundFlow.getBeforeAmount().subtract(shopFundFlow.getChangeAmount()));
                    shopFundFlow.setRemark(ShopFundFlowConverter.generateWithDrawRemark(accountDto,shopFundFlow));
                    shopFundFlow.setCreateTime(now);
                    shopFundFlow.setUpdateTime(now);
                    shopFundFlowDao.save(shopFundFlow);

                    apply.setStatus(EnumShopWithdrawApplyStatus.PASS);
                    apply.setAuditUid(LoginUserUtil.getLoginUser().getId());
                    apply.setUpdateTime(new Date());
                    shopWithdrawApplyDao.updateById(apply);
                }else {
                    throw new HzsxBizException("-1","支付失败，请联系客服人员");
                }
            }else {
                throw new HzsxBizException("-1","商户资金账户操作中，请稍后再试");
            }
        }catch (Exception e){
            throw e;
        }finally{
            RedisUtil.unLock(redisLockKey);
        }
    }

    @Override
    public String recharge(ShopFundFlowDto request) {
        String outTradeNo = "SR"+System.currentTimeMillis()+ RandomUtil.randomString(6);
        ShopFundFlow shopFundFlow = ShopFundFlowConverter.dto2Model(request);
        shopFundFlow.setOperate(EnumShopFundOperate.RECHARGE);
        shopFundFlow.setFlowNo(outTradeNo);
        shopFundFlow.setStatus(EnumShopFundStatus.PROCESSING);
        shopFundFlow.setCreateTime(new Date());
        shopFundFlowDao.save(shopFundFlow);

        AliPayTradeAppPageRequest payPageReq = new AliPayTradeAppPageRequest();
        payPageReq.setOutTradeNo(outTradeNo);
        payPageReq.setTotalAmount(request.getChangeAmount());
        payPageReq.setSubject(request.getShopId());
        payPageReq.setShopId(request.getShopId());
        payPageReq.setTradeType(EnumTradeType.SHOP_RECHARGE);
        AlipayAppPayResponse alipayAppPayResponse =  aliPayCapitalService.alipayTradePagePay(payPageReq);
        return alipayAppPayResponse.getPayUrl();
    }

    @Override
    public void rechargeCallBack(String outTradeNo,String tradeNo,String buyerId) {
        ShopFundFlow shopFundFlow = shopFundFlowDao.getByFlowNo(outTradeNo);
        BigDecimal balance = getShopFundBalance(shopFundFlow.getShopId());
        shopFundFlow.setBeforeAmount(balance);
        shopFundFlow.setAfterAmount(shopFundFlow.getBeforeAmount().add(shopFundFlow.getChangeAmount()));
        shopFundFlow.setStatus(EnumShopFundStatus.SUCCESS);
        shopFundFlow.setFlowNo(tradeNo);
        shopFundFlow.setUpdateTime(new Date());
        shopFundFlow.setRemark(ShopFundFlowConverter.generateWithRechargeRemark(buyerId,shopFundFlow));
        shopFundFlowDao.updateById(shopFundFlow);
    }

    @Override
    public JSONObject prof(Long id, String shopId) {
        ShopFundFlow flow = shopFundFlowDao.getSuccessByIdAndShopId(id,shopId);
        if(flow==null){
            return null;
        }
        if(!flow.getOperate().equals(EnumShopFundOperate.RECHARGE) && !flow.getOperate().equals(EnumShopFundOperate.WITHDRAW)){
            return null;
        }
        return JSON.parseObject(flow.getRemark());
    }

    @Override
    public Boolean brokerageSettle(String shopId,BigDecimal changeAmount,Long accountPeriod) {
        BigDecimal balance = getShopFundBalance(shopId);
        Date now = new Date();
        ShopFundFlow shopFundFlow = new ShopFundFlow();
        shopFundFlow.setShopId(shopId);
        shopFundFlow.setOperate(EnumShopFundOperate.BROKERAGE);
        shopFundFlow.setOperator(LoginUserUtil.getLoginUser().getMobile());
        shopFundFlow.setChangeAmount(changeAmount);
        shopFundFlow.setBeforeAmount(balance);
        shopFundFlow.setAfterAmount(shopFundFlow.getBeforeAmount().add(shopFundFlow.getChangeAmount()));
        shopFundFlow.setFlowNo("brokerAge_"+accountPeriod);
        JSONObject remark = new JSONObject();
        remark.put("accountPeriod",accountPeriod);
        shopFundFlow.setRemark(remark.toJSONString());
        shopFundFlow.setStatus(EnumShopFundStatus.SUCCESS);
        shopFundFlow.setCreateTime(now);
        shopFundFlow.setUpdateTime(now);
        shopFundFlowDao.save(shopFundFlow);
        return Boolean.TRUE;
    }

    @Override
    public Long getBrokerageAccountPeriodId(Long id, String shopId) {
        ShopFundFlow flow = shopFundFlowDao.getSuccessByIdAndShopId(id,shopId);
        if(flow==null){
            return null;
        }
        if(!flow.getOperate().equals(EnumShopFundOperate.BROKERAGE)){
            return null;
        }
        JSONObject remark = JSON.parseObject(flow.getRemark());
        return remark.getLong("accountPeriod");
    }

    @Override
    public Long assessmentFeeSettle(String shopId,BigDecimal amount) {
        BigDecimal balance = getShopFundBalance(shopId);
        Date now = new Date();
        ShopFundFlow shopFundFlow = new ShopFundFlow();
        shopFundFlow.setShopId(shopId);
        shopFundFlow.setOperate(EnumShopFundOperate.ASSESSMENT);
        shopFundFlow.setOperator(LoginUserUtil.getLoginUser().getMobile());
        shopFundFlow.setChangeAmount(amount);
        shopFundFlow.setBeforeAmount(balance);
        shopFundFlow.setAfterAmount(shopFundFlow.getBeforeAmount().subtract(shopFundFlow.getChangeAmount()));
        long serialNo = System.currentTimeMillis();
        shopFundFlow.setFlowNo("assessmentFee"+serialNo+shopId);
        JSONObject remark = new JSONObject();
        remark.put("assessmentFee",serialNo);
        shopFundFlow.setRemark(remark.toJSONString());
        shopFundFlow.setStatus(EnumShopFundStatus.SUCCESS);
        shopFundFlow.setCreateTime(now);
        shopFundFlow.setUpdateTime(now);
        shopFundFlowDao.save(shopFundFlow);
        return shopFundFlow.getId();
    }

    @Override
    public Long shopFundOrderReportFee(String shopId,BigDecimal amount,String orderId) {
        BigDecimal balance = getShopFundBalance(shopId);
        Date now = new Date();
        ShopFundFlow shopFundFlow = new ShopFundFlow();
        shopFundFlow.setShopId(shopId);
        shopFundFlow.setOperate(EnumShopFundOperate.CREDIT_REPORT);
        shopFundFlow.setOperator(LoginUserUtil.getLoginUser().getMobile());
        shopFundFlow.setChangeAmount(amount);
        shopFundFlow.setBeforeAmount(balance);
        shopFundFlow.setAfterAmount(shopFundFlow.getBeforeAmount().subtract(shopFundFlow.getChangeAmount()));

        long serialNo = System.currentTimeMillis();
        shopFundFlow.setFlowNo("credit_report"+serialNo+shopId);
        JSONObject remark = new JSONObject();
        remark.put("creditReport",serialNo);
        shopFundFlow.setRemark(remark.toJSONString());
        shopFundFlow.setStatus(EnumShopFundStatus.SUCCESS);
        shopFundFlow.setCreateTime(now);
        shopFundFlow.setUpdateTime(now);
        shopFundFlowDao.save(shopFundFlow);
        return shopFundFlow.getId();
    }

    @Override
    public Long shopFundContractFee(String shopId, BigDecimal amount, String orderId) {
        BigDecimal balance = getShopFundBalance(shopId);
        Date now = new Date();
        ShopFundFlow shopFundFlow = new ShopFundFlow();
        shopFundFlow.setShopId(shopId);
        shopFundFlow.setOperate(EnumShopFundOperate.CONTRACT);
        shopFundFlow.setOperator(LoginUserUtil.getLoginUser().getMobile());
        shopFundFlow.setChangeAmount(amount);
        shopFundFlow.setBeforeAmount(balance);
        shopFundFlow.setAfterAmount(shopFundFlow.getBeforeAmount().subtract(shopFundFlow.getChangeAmount()));
        long serialNo = System.currentTimeMillis();
        shopFundFlow.setFlowNo("contract"+serialNo+shopId);
        JSONObject remark = new JSONObject();
        remark.put("contract",serialNo);
        shopFundFlow.setRemark(remark.toJSONString());
        shopFundFlow.setStatus(EnumShopFundStatus.SUCCESS);
        shopFundFlow.setCreateTime(now);
        shopFundFlow.setUpdateTime(now);
        shopFundFlowDao.save(shopFundFlow);
        return shopFundFlow.getId();
    }

    @Override
    public List<ShopFundFlowDto> pageChannelFundFlow(String uid) {
        List<ShopFundFlow> list = shopFundFlowDao.listChannelFundFlow(uid);
        return ShopFundFlowConverter.modelList2DtoList(list);
    }

}