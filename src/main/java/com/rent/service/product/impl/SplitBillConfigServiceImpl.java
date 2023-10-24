
package com.rent.service.product.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.product.ShopSplitBillAccountConverter;
import com.rent.common.converter.product.ShopSplitBillRuleConverter;
import com.rent.common.dto.backstage.resp.SplitBillShopResp;
import com.rent.common.dto.product.*;
import com.rent.common.enums.product.EnumSplitBillAccountStatus;
import com.rent.dao.product.ShopSplitBillAccountDao;
import com.rent.dao.product.ShopSplitBillRuleDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.ShopSplitBillAccount;
import com.rent.model.product.ShopSplitBillRule;
import com.rent.service.product.SplitBillConfigService;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SplitBillConfigServiceImpl implements SplitBillConfigService {

    private final ShopSplitBillAccountDao shopSplitBillAccountDao;
    private final ShopSplitBillRuleDao shopSplitBillRuleDao;

    @Override
    public Page<SplitBillConfigListDto> page(ShopSplitBillReqDto request) {
        List<Long> accountIds = null;
        if (request.getTypeInfo() != null) {
            accountIds = shopSplitBillRuleDao.getAccountIdsByType(request.getTypeInfo());
            if (CollectionUtil.isEmpty(accountIds)) {
                accountIds.add(-1L);
            }
        }
        Page<ShopSplitBillAccount> page = shopSplitBillAccountDao.page(
                new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<ShopSplitBillAccount>()
                        .in(CollectionUtil.isNotEmpty(accountIds), "id", accountIds)
                        .like(StringUtil.isNotEmpty(request.getShopName()), "shop_name", request.getShopName())
                        .eq(StringUtil.isNotEmpty(request.getStatus()), "status", request.getStatus())
                        .like(StringUtil.isNotEmpty(request.getCycle()), "cycle", request.getCycle())
                        .orderByDesc("update_time")
        );

        List<SplitBillConfigListDto> listDtoList = ShopSplitBillRuleConverter.models2ListDtoList(page.getRecords());
        for (SplitBillConfigListDto dto : listDtoList) {
            dto.setTypeInfo(shopSplitBillRuleDao.getTypeInfoByAccountId(dto.getId()));
        }
        return new Page<SplitBillConfigListDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(listDtoList);
    }

    @Override
    public List<SplitBillShopResp> getShopList(String shopName) {
        return ShopSplitBillAccountConverter.modelsToShopDtoList(shopSplitBillAccountDao.getShopList(shopName));
    }

    @Override
    public Boolean add(SpiltBillConfigDto spiltBillConfigDto) {
        Date now = new Date();
        ShopSplitBillAccount accountModel = ShopSplitBillAccountConverter.dto2Model(spiltBillConfigDto.getAccount());
        accountModel.setStatus(EnumSplitBillAccountStatus.PENDING);
        accountModel.setCreateTime(now);
        accountModel.setUpdateTime(now);
        shopSplitBillAccountDao.saveAccountInfo(accountModel);
        List<ShopSplitBillRule> ruleModelList = ShopSplitBillRuleConverter.dtoList2ModelList(spiltBillConfigDto.getRules());
        for (ShopSplitBillRule shopSplitBillRule : ruleModelList) {
            shopSplitBillRule.setAccountId(accountModel.getId());
            shopSplitBillRule.setCreateTime(now);
        }
        shopSplitBillRuleDao.saveBatch(ruleModelList);
        return Boolean.TRUE;
    }


    @Override
    public Boolean update(SpiltBillConfigDto spiltBillConfigDto) {
        ShopSplitBillAccount accountModel = shopSplitBillAccountDao.getById(spiltBillConfigDto.getAccount().getId());
        if (accountModel == null) {
            throw new HzsxBizException("-1", "没有找到该记录");
        }
        if (accountModel.getStatus().equals(EnumSplitBillAccountStatus.UNSET)) {
            throw new HzsxBizException("-1", "该信息未设置，请直接添加");
        }
        if (accountModel.getStatus().equals(EnumSplitBillAccountStatus.PASS)) {
            //如果是已经审核通过的,或者是未设置的状态，需要做新增操作
            spiltBillConfigDto.getAccount().setId(null);
            spiltBillConfigDto.getAccount().setAuditUser(null);
            spiltBillConfigDto.getAccount().setAuditTime(null);
            spiltBillConfigDto.getAccount().setAuditRemark(null);
            for (ShopSplitBillRuleDto rule : spiltBillConfigDto.getRules()) {
                rule.setId(null);
            }
            add(spiltBillConfigDto);
        } else {
            Date now = new Date();
            //审核拒绝，或者待审核的，直接做修改操作(状态重新改成待审核)
            accountModel = ShopSplitBillAccountConverter.dto2Model(spiltBillConfigDto.getAccount());
            accountModel.setStatus(EnumSplitBillAccountStatus.PENDING);
            accountModel.setUpdateTime(now);
            accountModel.setAuditUser(null);
            accountModel.setAuditTime(null);
            accountModel.setAuditRemark(null);
            shopSplitBillAccountDao.updateById(accountModel);
            //删除原有的规则
            shopSplitBillRuleDao.deleteByAccountId(accountModel.getId());
            //添加新的规则
            List<ShopSplitBillRule> ruleModelList = ShopSplitBillRuleConverter.dtoList2ModelList(spiltBillConfigDto.getRules());

            for (ShopSplitBillRule shopSplitBillRule : ruleModelList) {
                shopSplitBillRule.setId(null);
                shopSplitBillRule.setAccountId(accountModel.getId());
                shopSplitBillRule.setCreateTime(now);
            }
            if (CollectionUtil.isNotEmpty(ruleModelList)) {
                shopSplitBillRuleDao.saveBatch(ruleModelList);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public SpiltBillRuleConfigDto getUseAbleConfigByType(String shopId, Date time, String type, String channelId) {
        List<SpiltBillRuleConfigDto> dtoList = new ArrayList<>();
        List<ShopSplitBillAccount> accounts = shopSplitBillAccountDao.getByPassedShopId(shopId);
        for (ShopSplitBillAccount account : accounts) {
            List<ShopSplitBillRuleDto> ruleDtoList = shopSplitBillRuleDao.getDtoByAccountId(account.getId());
            for (ShopSplitBillRuleDto ruleDto : ruleDtoList) {
                if (ruleDto.getType().startsWith(type)) {
                    SpiltBillRuleConfigDto dto = new SpiltBillRuleConfigDto();
                    dto.setIdentity(account.getIdentity());
                    dto.setName(account.getName());
                    dto.setRuleId(ruleDto.getId());
                    dto.setType(ruleDto.getType());
                    dto.setDelayNum(ruleDto.getDelayNum());
                    dto.setDelayType(ruleDto.getDelayType());
                    dto.setScale(ruleDto.getScale());
                    dto.setCreateTime(account.getAuditTime());
                    dto.setCycle(Arrays.asList(account.getCycle().split(",")));
                    dtoList.add(dto);
                }
            }
        }
        if (CollectionUtil.isEmpty(dtoList)) {
            return null;
        } else {
            SpiltBillRuleConfigDto dto = null;
            dtoList = dtoList.stream().sorted(Comparator.comparing(SpiltBillRuleConfigDto::getCreateTime).reversed()).collect(Collectors.toList());
            for (SpiltBillRuleConfigDto dto1 : dtoList) {
                Date createTime = dto1.getCreateTime();
                if (time.compareTo(createTime) >= 0) {
                    dto = dto1;
                    break;
                }
            }
            dto = dto == null ? dtoList.get(0) : dto;
            return dto;
        }
    }

    @Override
    public SpiltBillConfigDto detail(Long id) {
        ShopSplitBillAccount shopSplitBillAccount = shopSplitBillAccountDao.getById(id);
        return packShopSplitBillAccountToConfigDto(shopSplitBillAccount);
    }

    @Override
    public Boolean audit(SplitBillAuditDto dto) {
        ShopSplitBillAccount shopSplitBillAccount = shopSplitBillAccountDao.getById(dto.getId());
        if (!EnumSplitBillAccountStatus.PENDING.equals(shopSplitBillAccount.getStatus())) {
            throw new HzsxBizException("-1", "当前状态不支持审核");
        }
        shopSplitBillAccount.setStatus(dto.getPass() ? EnumSplitBillAccountStatus.PASS : EnumSplitBillAccountStatus.REJECT);
        shopSplitBillAccount.setAuditRemark(dto.getAuditRemark());
        shopSplitBillAccount.setAuditTime(new Date());
        shopSplitBillAccount.setUpdateTime(new Date());
        shopSplitBillAccount.setAuditUser(dto.getAuditUser());
        shopSplitBillAccountDao.updateById(shopSplitBillAccount);
        return Boolean.TRUE;
    }

    /**
     * 将账户模型转换成包含分账规则的dto
     *
     * @param shopSplitBillAccount
     * @return
     */
    private SpiltBillConfigDto packShopSplitBillAccountToConfigDto(ShopSplitBillAccount shopSplitBillAccount) {
        if (shopSplitBillAccount == null) {
            return null;
        }
        ShopSplitBillAccountDto accountDto = ShopSplitBillAccountConverter.model2Dto(shopSplitBillAccount);
        List<ShopSplitBillRuleDto> ruleDtoList = shopSplitBillRuleDao.getDtoByAccountId(shopSplitBillAccount.getId());
        SpiltBillConfigDto configDto = new SpiltBillConfigDto();
        configDto.setAccount(accountDto);
        configDto.setRules(ruleDtoList);
        return configDto;
    }
}