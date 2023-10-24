
package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.marketing.OpeNoticeItemConverter;
import com.rent.common.dto.marketing.OpeNoticeItemDto;
import com.rent.common.dto.marketing.OpeNoticeItemReqDto;
import com.rent.dao.marketing.OpeNoticeItemDao;
import com.rent.model.marketing.OpeNoticeItem;
import com.rent.service.marketing.OpeNoticeItemService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 公告常见问题tab内容Service
 *
 * @author youruo
 * @Date 2021-08-16 15:55
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpeNoticeItemServiceImpl implements OpeNoticeItemService {

    private final OpeNoticeItemDao opeNoticeItemDao;

    @Override
    public Boolean addOpeNoticeItem(OpeNoticeItemDto request) {
        OpeNoticeItem model = OpeNoticeItemConverter.dto2Model(request);
        Date now = DateUtil.getNowDate();
        model.setCreateTime(now);
        model.setUpdateTime(now);
        if (opeNoticeItemDao.save(model)) {
            return Boolean.TRUE;
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Boolean modifyOpeNoticeItem(OpeNoticeItemDto request) {
        OpeNoticeItem model = OpeNoticeItemConverter.dto2Model(request);
        Date now = DateUtil.getNowDate();
        model.setUpdateTime(now);
        return opeNoticeItemDao.updateById(model);
    }

    @Override
    public Boolean deleteOpeNoticeItem(Long id) {
        return opeNoticeItemDao.updateById(OpeNoticeItemConverter.dto2ModelV1(id));
    }

    @Override
    public OpeNoticeItemDto queryOpeNoticeItemDetail(OpeNoticeItemReqDto request) {
        OpeNoticeItem opeNoticeItem = opeNoticeItemDao.getOne(new QueryWrapper<>(OpeNoticeItemConverter.reqDto2Model(request)).last("limit 1"));
        return OpeNoticeItemConverter.model2Dto(opeNoticeItem);
    }

    @Override
    public List<OpeNoticeItemDto> queryOpeNoticeItemList(OpeNoticeItemReqDto request) {
        List<OpeNoticeItem> list = opeNoticeItemDao.list(new QueryWrapper<>(OpeNoticeItemConverter.reqDto2Model(request))
                .isNull("delete_time")
                .orderByDesc("index_sort", "update_time"));
        return OpeNoticeItemConverter.modelList2DtoList(list);
    }

    @Override
    public Page<OpeNoticeItemDto> queryOpeNoticeItemPage(OpeNoticeItemReqDto request) {
        Page<OpeNoticeItem> page = opeNoticeItemDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<>(OpeNoticeItemConverter.reqDto2Model(request))
                        .isNull("delete_time")
                        .orderByDesc("index_sort", "update_time")
        );
        return new Page<OpeNoticeItemDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                OpeNoticeItemConverter.modelList2DtoList(page.getRecords()));
    }


}