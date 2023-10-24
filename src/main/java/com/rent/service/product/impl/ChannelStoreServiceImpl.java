
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.product.ChannelStoreConverter;
import com.rent.common.dto.product.ChannelStoreDto;
import com.rent.common.dto.product.ChannelStoreReqDto;
import com.rent.dao.product.ChannelStoreAddressDao;
import com.rent.dao.product.ChannelStoreDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.ChannelStore;
import com.rent.model.product.ChannelStoreAddress;
import com.rent.service.product.ChannelStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author xiaotong
 */
@Service
@RequiredArgsConstructor
public class ChannelStoreServiceImpl implements ChannelStoreService {

    private final ChannelStoreDao channelStoreDao;
    private final ChannelStoreAddressDao channelStoreAddressDao;

    @Override
    public List<ChannelStoreDto> list(String channelSplitId) {
        List<ChannelStore> list = channelStoreDao.list(new QueryWrapper<ChannelStore>()
                .eq("marketing_channel_id", channelSplitId)
                .isNull("delete_time")
                .orderByDesc("create_time")
        );
        return ChannelStoreConverter.modelList2DtoList(list);
    }

    @Override
    public Page<ChannelStoreDto> page(ChannelStoreReqDto request) {
        Page<ChannelStore> page = channelStoreDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<ChannelStore>()
//                        .like(StringUtil.isNotEmpty(request.getName()),"name",request.getName())
//                        .eq(StringUtil.isNotEmpty(request.getStatus()),"status",request.getStatus())
//                        .like(StringUtil.isNotEmpty(request.getAddUser()),"identity",request.getAddUser())
                        .orderByDesc("create_time")
        );
        List<ChannelStoreDto> channelStoreDtoList = ChannelStoreConverter.modelList2DtoList(page.getRecords());
        channelStoreDtoList.forEach(item -> item.setAddress(channelStoreAddressDao.getOne(new QueryWrapper<ChannelStoreAddress>()
                .eq("marketing_id", item.getMarketingId())
                .orderByDesc("create_time")
                .last("limit 1"))));
        return new Page<ChannelStoreDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(channelStoreDtoList);
    }

    @Override
    public ChannelStoreDto getByMarketingId(String uid) {
        ChannelStore channelStore = channelStoreDao.getOne(new QueryWrapper<ChannelStore>()
                .eq("marketing_id", uid)
                .orderByDesc("create_time")
                .last("limit 1"));
        if (channelStore == null) {
            throw new HzsxBizException("-1", "没有找到该记录");
        }
        ChannelStoreDto channelStoreDto = ChannelStoreConverter.model2Dto(channelStore);
        channelStoreDto.setAddress(channelStoreAddressDao.getOne(new QueryWrapper<ChannelStoreAddress>()
                .eq("marketing_id", uid)
                .orderByDesc("create_time")
                .last("limit 1")));
        return channelStoreDto;
    }


    @Override
    public Boolean add(ChannelStoreDto channelStoreDto) {
        ChannelStore model = channelStoreDao.getOne(new QueryWrapper<ChannelStore>().eq("marketing_id", channelStoreDto.getMarketingId()).isNull("delete_time"));
        if (model != null) {
            throw new HzsxBizException("-1", "渠道编码已使用");
        }
        ChannelStore channelStore = ChannelStoreConverter.dto2Model(channelStoreDto);
        channelStore.setCreateTime(new Date());
        if (channelStoreDao.save(channelStore)) {
            return Boolean.TRUE;
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Boolean delete(String id) {
        ChannelStore channelStore = channelStoreDao.getById(id);
        if (channelStore == null) {
            throw new HzsxBizException("-1", "没有找到该记录");
        }
        channelStore.setDeleteTime(new Date());
        channelStoreDao.updateById(channelStore);
        return Boolean.TRUE;
    }

    @Override
    public String getChannelSplitId(String marketingId) {
        ChannelStore channelStore = channelStoreDao.getOne(new QueryWrapper<ChannelStore>().eq("marketing_id", marketingId));
        //如果新的用户进来没有配置渠道默认渠道编号为1
        if (channelStore == null) {
            return "1";
        }
        return channelStore.getMarketingChannelId();
    }

}