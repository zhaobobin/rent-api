
package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.marketing.OpeNoticeConverter;
import com.rent.common.dto.marketing.OpeNoticeDto;
import com.rent.common.dto.marketing.OpeNoticeReqDto;
import com.rent.dao.marketing.MaterialCenterItemDao;
import com.rent.dao.marketing.OpeNoticeDao;
import com.rent.model.marketing.OpeNotice;
import com.rent.service.marketing.OpeNoticeService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商家中心通知Service
 *
 * @author youruo
 * @Date 2021-08-16 16:10
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpeNoticeServiceImpl implements OpeNoticeService {

    private final OpeNoticeDao opeNoticeDao;
    private final MaterialCenterItemDao materialCenterItemDao;

    @Override
    public Boolean addOpeNotice(OpeNoticeDto request) {
        OpeNotice model = OpeNoticeConverter.dto2Model(request);
        Date now = DateUtil.getNowDate();
        model.setCreateTime(now);
        model.setUpdateTime(now);
        if (opeNoticeDao.save(model)) {
            return Boolean.TRUE;
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Boolean modifyOpeNotice(OpeNoticeDto request) {
        OpeNotice model = OpeNoticeConverter.dto2Model(request);
        Date now = DateUtil.getNowDate();
        model.setUpdateTime(now);
        return opeNoticeDao.updateById(model);
    }

    @Override
    public Boolean deleteOpeNotice(Long id) {
        return opeNoticeDao.updateById(OpeNoticeConverter.dto2ModelV1(id));
    }

    @Override
    public List<OpeNoticeDto> queryOpeNoticeDetailList(OpeNoticeReqDto request) {
        List<OpeNotice> list = opeNoticeDao.list(new QueryWrapper<>(OpeNoticeConverter.reqDto2Model(request))
                .isNull("delete_time")
                .orderByDesc("index_sort", "update_time"));
        List<OpeNoticeDto> result = OpeNoticeConverter.modelList2DtoList(list);
        if(CollectionUtils.isNotEmpty(result)){
            List<Long> ids = result.stream().map(OpeNoticeDto::getMaterialItemId).collect(Collectors.toList());
            Map<Long,String> maps = materialCenterItemDao.getMaterialCenterFileUrl(ids);
            result.forEach(item->{
                item.setMaterialItemFileUrl(maps.get(item.getMaterialItemId()));
            });
        }

        return result;
    }

    @Override
    public Page<OpeNoticeDto> queryOpeNoticePage(OpeNoticeReqDto request) {
        Page<OpeNotice> page = opeNoticeDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<>(OpeNoticeConverter.reqDto2Model(request))
                        .isNull("delete_time")
                        .orderByDesc("index_sort", "update_time"));
        return new Page<OpeNoticeDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                OpeNoticeConverter.modelList2DtoList(page.getRecords()));
    }


}