package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.ShopSplitRuleConstant;
import com.rent.common.converter.order.AccountPeriodConverter;
import com.rent.common.converter.order.FeeBillConverter;
import com.rent.common.dto.order.AccountPeriodDetailDto;
import com.rent.common.dto.order.AccountPeriodDto;
import com.rent.common.dto.order.AccountPeriodSubmitAuditDto;
import com.rent.common.dto.order.AccountPeriodSubmitSettleDto;
import com.rent.common.dto.order.resquest.AccountPeriodReqDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.enums.order.EnumAccountPeriodOperator;
import com.rent.common.enums.order.EnumAccountPeriodStatus;
import com.rent.dao.order.AccountPeriodDao;
import com.rent.dao.order.AccountPeriodProgressDao;
import com.rent.dao.order.FeeBillDao;
import com.rent.dao.order.SplitBillDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.AccountPeriod;
import com.rent.model.order.FeeBill;
import com.rent.model.order.SplitBill;
import com.rent.service.order.AccountPeriodService;
import com.rent.service.product.ShopFundService;
import com.rent.service.product.ShopService;
import com.rent.util.DateUtil;
import com.rent.util.LoginUserUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountPeriodServiceImpl implements AccountPeriodService {

    private final AccountPeriodDao accountPeriodDao;
    private final AccountPeriodProgressDao accountPeriodProgressDao;
    private final SplitBillDao splitBillDao;
    private final ShopService shopService;
    private final ShopFundService shopFundService;
    private final FeeBillDao feeBillDao;

    @Override
    public void generateAccountPeriod() {
        log.info("Quartz_task_generateAccountPeriod=============================================================================");
        List<SplitBill> splitBillList = splitBillDao.list(new QueryWrapper<SplitBill>()
                .select("id","shop_id","type","identity","name","user_pay_amount","trans_amount")
                .isNull("account_period_id"));
        Set<String> shopIdSet = new HashSet<>();
        for (SplitBill splitBill : splitBillList) {
            shopIdSet.add(splitBill.getShopId());
        }
        Map<String, ShopDto> shopDtoMap = shopService.selectShopInfoByShopId(new ArrayList<>(shopIdSet));
        Map<String,List<SplitBill>> shopSplitBillMap = splitBillList.stream().collect(Collectors.groupingBy(SplitBill::getShopId));
        Date now = new Date();
        for (String shopId : shopSplitBillMap.keySet()) {
            SplitBill lastSplitBill = null;
            List<SplitBill> shopSplitBillList = shopSplitBillMap.get(shopId);
            AccountPeriod accountPeriod = AccountPeriod.init();
            accountPeriod.setShopId(shopId);
            ShopDto shopDto = shopDtoMap.get(shopId);
            accountPeriod.setShopName(shopDto.getName());
            for (SplitBill splitBill : shopSplitBillList) {
                accountPeriod.addTotalAmount(splitBill.getUserPayAmount());
                accountPeriod.addTotalSettleAmount(splitBill.getTransAmount());
                BigDecimal brokerage = splitBill.getUserPayAmount().subtract(splitBill.getTransAmount());
                accountPeriod.addTotalBrokerage(brokerage);
                if(ShopSplitRuleConstant.TYPE_BUY_OUT.equals(splitBill.getType())){
                    accountPeriod.addBuyoutAmount(splitBill.getUserPayAmount());
                    accountPeriod.addBuyoutSettleAmount(splitBill.getTransAmount());
                    accountPeriod.addBuyoutBrokerage(brokerage);
                }else{
                    accountPeriod.addRentAmount(splitBill.getUserPayAmount());
                    accountPeriod.addRentSettleAmount(splitBill.getTransAmount());
                    accountPeriod.addRentBrokerage(brokerage);
                }
                lastSplitBill = splitBill;
            }
            accountPeriod.setAccountIdentity(lastSplitBill.getIdentity());
            accountPeriod.setAccountName(lastSplitBill.getName());
            accountPeriod.setCreateTime(now);
            accountPeriodDao.save(accountPeriod);
            for (SplitBill splitBill : shopSplitBillList) {
                splitBill.setAccountPeriodId(accountPeriod.getId());
            }
            splitBillDao.updateBatchById(shopSplitBillList);
            accountPeriodProgressDao.insertProgress(accountPeriod.getId(), EnumAccountPeriodOperator.GENERATE);
        }
    }


    @Override
    public Page<AccountPeriodDto> queryAccountPeriodPage(AccountPeriodReqDto request) {
        Date begin = DateUtil.getDayBegin(request.getSettleDate());
        Date end = DateUtil.getDayEnd(request.getSettleDate());
        Page<AccountPeriod> page = accountPeriodDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
            new QueryWrapper<AccountPeriod>()
                    .like(StringUtil.isNotEmpty(request.getShopName()),"shop_name",request.getShopName())
                    .eq(request.getStatus()!=null,"status",request.getStatus())
                    .between(begin!=null,"settle_date",begin,end)
                    .eq(StringUtil.isNotEmpty(request.getShopId()),"shop_id",request.getShopId())
                    .orderByDesc("create_time"));
        return new Page<AccountPeriodDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                AccountPeriodConverter.modelList2DtoList(page.getRecords()));
    }

    @Override
    public AccountPeriodDetailDto detail(Long id, String shopId) {
        AccountPeriod accountPeriod = accountPeriodDao.getById(id);
        if(!accountPeriod.getShopId().equals(shopId)  && !"OPE".equals(shopId)){
            return null;
        }
        AccountPeriodDetailDto dto = new AccountPeriodDetailDto();
        dto.setId(id);
        dto.setSettleDate(accountPeriod.getSettleDate());
        dto.setShopName(accountPeriod.getShopName());
        dto.setAccountIdentity(accountPeriod.getAccountIdentity());
        dto.setAccountName(accountPeriod.getAccountName());
        dto.setStatus(accountPeriod.getStatus());
        dto.setTotalSettleAmount(accountPeriod.getTotalSettleAmount());
        dto.setTotalBrokerage(accountPeriod.getTotalBrokerage());
        dto.setSettleAmount(accountPeriod.getSettleAmount());
        dto.setRentAmount(accountPeriod.getRentAmount());
        dto.setRentBrokerage(accountPeriod.getRentBrokerage());
        dto.setRentSettleAmount(accountPeriod.getRentSettleAmount());
        dto.setPurchaseAmount(accountPeriod.getPurchaseAmount());
        dto.setPurchaseBrokerage(accountPeriod.getPurchaseBrokerage());
        dto.setPurchaseSettleAmount(accountPeriod.getPurchaseSettleAmount());
        dto.setBuyoutAmount(accountPeriod.getBuyoutAmount());
        dto.setBuyoutBrokerage(accountPeriod.getBuyoutBrokerage());
        dto.setBuyoutSettleAmount(accountPeriod.getBuyoutSettleAmount());
        dto.setProgresses(accountPeriodProgressDao.getProgressList(id));
        return dto;
    }

    @Override
    public void submitSettle(AccountPeriodSubmitSettleDto req) {
        AccountPeriod accountPeriod = accountPeriodDao.getById(req.getAccountPeriodId());
        if(!accountPeriod.getStatus().equals(EnumAccountPeriodStatus.WAITING_SETTLEMENT)){
            throw new HzsxBizException("-1","当前状态不允许审核");
        }
        accountPeriod.setSettleAmount(req.getSettleAmount());
        accountPeriod.setSettleTitle(req.getSettleTitle());
        accountPeriod.setSettleRemark(req.getSettleRemark());
        accountPeriod.setStatus(EnumAccountPeriodStatus.TO_AUDIT);
        accountPeriodDao.updateById(accountPeriod);
        accountPeriodProgressDao.insertProgress(accountPeriod.getId(),EnumAccountPeriodOperator.SUBMIT_SETTLE,req.getBackstageUserName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitAudit(AccountPeriodSubmitAuditDto req) {
        AccountPeriod accountPeriod = accountPeriodDao.getById(req.getAccountPeriodId());
        if(!accountPeriod.getStatus().equals(EnumAccountPeriodStatus.TO_AUDIT)){
            throw new HzsxBizException("-1","当前状态不允许审核");
        }
        if(req.getAuditResult()){
            shopFundService.brokerageSettle(accountPeriod.getShopId(),accountPeriod.getSettleAmount(),accountPeriod.getId());
            splitBillDao.updateSettled(accountPeriod.getId());
            accountPeriodProgressDao.insertProgress(accountPeriod.getId(),EnumAccountPeriodOperator.AUDIT_PASS, LoginUserUtil.getLoginUser().getName());
            accountPeriod.setStatus(EnumAccountPeriodStatus.SETTLED);
        }else{
            accountPeriod.setStatus(EnumAccountPeriodStatus.WAITING_SETTLEMENT);
            accountPeriodProgressDao.insertProgress(accountPeriod.getId(),EnumAccountPeriodOperator.AUDIT_DENY,LoginUserUtil.getLoginUser().getName());
        }
        accountPeriodDao.updateById(accountPeriod);
        List<SplitBill> splitBillList = splitBillDao.list(new QueryWrapper<SplitBill>().eq("account_period_id",req.getAccountPeriodId()));
        List<FeeBill> feeBillList = new ArrayList<>(splitBillList.size());
        for (SplitBill splitBill : splitBillList) {
            FeeBill feeBill = FeeBillConverter.generateSplitBillFeeBill(splitBill, accountPeriod.getId());
            if(feeBill!=null){
                feeBillList.add(feeBill);
            }
        }
        if(CollectionUtil.isNotEmpty(feeBillList)){
            feeBillDao.saveBatch(feeBillList);
        }
    }
}
