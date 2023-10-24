        
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.product.ChannelSplitBillConverter;
import com.rent.common.dto.product.ChannelSplitBillDto;
import com.rent.common.dto.product.ChannelSplitBillReqDto;
import com.rent.common.dto.product.SplitBillAuditDto;
import com.rent.common.enums.product.EnumSplitBillAccountStatus;
import com.rent.common.util.StringUtil;
import com.rent.dao.product.MarketingChannelDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.MarketingChannel;
import com.rent.service.product.ChannelSplitBillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author xiaotong
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelSplitBillServiceImpl implements ChannelSplitBillService {

    private final MarketingChannelDao marketingChannelDao;

    @Override
    public Page<ChannelSplitBillDto> page(ChannelSplitBillReqDto request) {
        Page<MarketingChannel> page = marketingChannelDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<MarketingChannel>()
                        .like(StringUtil.isNotEmpty(request.getName()),"name",request.getName())
                        .eq(StringUtil.isNotEmpty(request.getStatus()),"status",request.getStatus())
                        .like(StringUtil.isNotEmpty(request.getAddUser()),"identity",request.getAddUser())
                        .orderByDesc("create_time")
                );
        List<ChannelSplitBillDto> channelSplitBillDtoList = ChannelSplitBillConverter.models2ListDtoList(page.getRecords());
        return new Page<ChannelSplitBillDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(channelSplitBillDtoList);
    }

    @Override
    public Long add(ChannelSplitBillDto channelSplitBillDto) {
        MarketingChannel model = marketingChannelDao.getOne(new QueryWrapper<MarketingChannel>()
                .eq("name",channelSplitBillDto.getName())
                .last("limit 1"));
        if(model != null){
            throw new HzsxBizException("-1","渠道名称已存在");
        }
        channelSplitBillDto.setStatus(EnumSplitBillAccountStatus.PENDING);
        channelSplitBillDto.setCreateTime(new Date());
        MarketingChannel marketingChannel = ChannelSplitBillConverter.dto2Model(channelSplitBillDto);
        if (marketingChannelDao.save(marketingChannel)) {
            return channelSplitBillDto.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Boolean update(ChannelSplitBillDto channelSplitBillDto) {
        MarketingChannel marketingChannel = marketingChannelDao.getById(channelSplitBillDto.getId());
        if(marketingChannel == null){
            throw new HzsxBizException("-1","没有找到该记录");
        }
        marketingChannel.setAddUser(channelSplitBillDto.getAddUser());
        marketingChannel.setAccount(channelSplitBillDto.getAccount());
        marketingChannel.setAliName(channelSplitBillDto.getAliName());
        marketingChannel.setScale(channelSplitBillDto.getScale());
        marketingChannel.setStatus(EnumSplitBillAccountStatus.PENDING);
        marketingChannel.setCreateTime(new Date());
        marketingChannelDao.updateById(marketingChannel);
        return Boolean.TRUE;
    }

    @Override
    public ChannelSplitBillDto getOne(String uid) {
        MarketingChannel marketingChannel = marketingChannelDao.getOne(new QueryWrapper<MarketingChannel>()
                .eq("uid",uid)
                .orderByDesc("create_time")
                .last("limit 1"));
        if(marketingChannel == null){
            throw new HzsxBizException("-1","没有找到该记录");
        }
        return ChannelSplitBillConverter.model2Dto(marketingChannel);
    }

    @Override
    public Boolean channelAudit(SplitBillAuditDto dto) {
        MarketingChannel marketingChannel = marketingChannelDao.getById(dto.getId());
        if(!EnumSplitBillAccountStatus.PENDING.equals(marketingChannel.getStatus())){
            throw new HzsxBizException("-1","当前状态不支持审核");
        }
        marketingChannel.setStatus(dto.getPass() ? EnumSplitBillAccountStatus.PASS : EnumSplitBillAccountStatus.REJECT);
        marketingChannel.setAuditTime(new Date());
        marketingChannel.setUpdateTime(new Date());
        marketingChannel.setAuditUser(dto.getAuditUser());
        marketingChannelDao.updateById(marketingChannel);
        return Boolean.TRUE;
    }

    @Override
    public ChannelSplitBillDto getOneForScale(String uid) {
        MarketingChannel marketingChannel = marketingChannelDao.getOne(new QueryWrapper<MarketingChannel>()
                .eq("uid",uid)
                .eq("status",EnumSplitBillAccountStatus.PASS)
                .orderByDesc("audit_time")
                .last("limit 1"));
        if(marketingChannel == null){
            throw new HzsxBizException("-1","渠道还未审核通过，请使用审核通过的渠道");
        }
        return ChannelSplitBillConverter.model2Dto(marketingChannel);
    }

    @Override
    public List<String> getUidList(String name) {
        List<MarketingChannel> marketingChannel = marketingChannelDao.list(new QueryWrapper<MarketingChannel>()
                .like("name",name)
                .orderByDesc("audit_time"));
        if(marketingChannel == null){
            throw new HzsxBizException("-1","查询错误");
        }
        return marketingChannel.stream().map(MarketingChannel::getUid).collect(Collectors.toList());
    }
}