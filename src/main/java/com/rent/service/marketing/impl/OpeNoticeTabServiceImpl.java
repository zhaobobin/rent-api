
package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.marketing.OpeNoticeTabConverter;
import com.rent.common.dto.marketing.OpeNoticeItemReqDto;
import com.rent.common.dto.marketing.OpeNoticeTabDto;
import com.rent.common.dto.marketing.OpeNoticeTabReqDto;
import com.rent.dao.marketing.OpeNoticeTabDao;
import com.rent.model.marketing.OpeNoticeTab;
import com.rent.service.marketing.OpeNoticeItemService;
import com.rent.service.marketing.OpeNoticeTabService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 公告常见问题tabService
 *
 * @author youruo
 * @Date 2021-08-16 15:55
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpeNoticeTabServiceImpl implements OpeNoticeTabService {

    private final OpeNoticeTabDao opeNoticeTabDao;
    private final OpeNoticeItemService opeNoticeItemService;

    @Override
    public Boolean addOpeNoticeTab(OpeNoticeTabDto request) {
        OpeNoticeTab model = OpeNoticeTabConverter.dto2Model(request);
        Date now = DateUtil.getNowDate();
        model.setCreateTime(now);
        model.setUpdateTime(now);
        if (opeNoticeTabDao.save(model)) {
            return Boolean.TRUE;
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Boolean modifyOpeNoticeTab(OpeNoticeTabDto request) {
        OpeNoticeTab model = OpeNoticeTabConverter.dto2Model(request);
        Date now = DateUtil.getNowDate();
        model.setUpdateTime(now);
        return opeNoticeTabDao.updateById(model);
    }

    @Override
    public Boolean deleteOpeNoticeTab(Long id) {
        return opeNoticeTabDao.updateById(OpeNoticeTabConverter.dto2ModelV1(id));
    }
    @Override
    public List<OpeNoticeTabDto> queryOpeNoticeTabDetailList(OpeNoticeTabReqDto request) {
        List<OpeNoticeTab> list = opeNoticeTabDao.list(new QueryWrapper<>(OpeNoticeTabConverter.reqDto2Model(request))
                .isNull("delete_time")
                .orderByDesc("index_sort", "update_time")
        );
        return OpeNoticeTabConverter.modelList2DtoList(list);
    }

    @Override
    public List<OpeNoticeTabDto> queryOpeNoticeTabList() {
        List<OpeNoticeTabDto> dtos = this.queryOpeNoticeTabDetailList(OpeNoticeTabReqDto.builder().build());
        if(CollectionUtils.isNotEmpty(dtos)){
            dtos.forEach(item ->{
                item.setItemDtos(this.opeNoticeItemService.queryOpeNoticeItemList(OpeNoticeItemReqDto.builder().tabId(item.getId()).build()));
            });
        }
        return dtos;
    }
}