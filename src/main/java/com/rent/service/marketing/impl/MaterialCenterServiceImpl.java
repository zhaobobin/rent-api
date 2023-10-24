package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.marketing.MaterialCenterConverter;
import com.rent.common.dto.marketing.MaterialCenterCategoryDto;
import com.rent.common.dto.marketing.MaterialCenterCategoryReqDto;
import com.rent.common.dto.marketing.MaterialCenterItemDto;
import com.rent.common.dto.marketing.MaterialCenterItemReqDto;
import com.rent.dao.marketing.MaterialCenterCategoryDao;
import com.rent.dao.marketing.MaterialCenterItemDao;
import com.rent.model.marketing.MaterialCenterCategory;
import com.rent.model.marketing.MaterialCenterItem;
import com.rent.service.marketing.MaterialCenterService;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 素材中心
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MaterialCenterServiceImpl implements MaterialCenterService {

    private final MaterialCenterCategoryDao materialCenterCategoryDao;
    private final MaterialCenterItemDao materialCenterItemDao;

    @Override
    public Page<MaterialCenterCategoryDto> pageCategory(MaterialCenterCategoryReqDto dto) {
        Page<MaterialCenterCategory> page = materialCenterCategoryDao.page(new Page<>(dto.getPageNumber(), dto.getPageSize()),
                new QueryWrapper<MaterialCenterCategory>()
                        .like(StringUtil.isNotEmpty(dto.getName()),"name",dto.getName())
                        .isNull("delete_time")
                        .orderByDesc("id"));

        return new Page<MaterialCenterCategoryDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                MaterialCenterConverter.categoryModelsToDtoList(page.getRecords()));
    }

    @Override
    public Long addCategory(MaterialCenterCategoryDto request) {
        MaterialCenterCategory model = MaterialCenterConverter.categoryDtoToModel(request);
        model.setCreateTime(new Date());
        materialCenterCategoryDao.save(model);
        return model.getId();
    }

    @Override
    public void deleteCategory(Long id) {
        MaterialCenterCategory model = materialCenterCategoryDao.getById(id);
        model.setDeleteTime(new Date());
        materialCenterCategoryDao.updateById(model);
        materialCenterItemDao.deleteCategoryById(id);
    }

    @Override
    public MaterialCenterCategoryDto detailCategory(Long id) {
        MaterialCenterCategory materialCenterCategory = materialCenterCategoryDao.getById(id);
        if(materialCenterCategory.getDeleteTime()!=null){
            return null;
        }
        return MaterialCenterConverter.categoryModelToDto(materialCenterCategory);
    }


    @Override
    public Page<MaterialCenterItemDto> pageItem(MaterialCenterItemReqDto dto) {
        Page<MaterialCenterItem> page = materialCenterItemDao.page(new Page<>(dto.getPageNumber(), dto.getPageSize()),
                new QueryWrapper<MaterialCenterItem>()
                        .eq(dto.getCategoryId()!=null,"category_id",dto.getCategoryId())
                        .eq(dto.getId()!=null,"id",dto.getId())
                        .like(StringUtil.isNotEmpty(dto.getName()),"name",dto.getName())
                        .isNull("delete_time")
                        .orderByDesc("id"));
        return new Page<MaterialCenterItemDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                MaterialCenterConverter.itemModelsToDtoList(page.getRecords()));
    }

    @Override
    public Long addItem(MaterialCenterItemDto dto) {
        MaterialCenterItem model = MaterialCenterConverter.itemDtoToModel(dto);
        model.setCreateTime(new Date());
        materialCenterItemDao.save(model);
        return model.getId();
    }

    @Override
    public MaterialCenterItemDto detailItem(Long id) {
        return MaterialCenterConverter.itemModelToDto(materialCenterItemDao.getById(id));
    }

    @Override
    public void updateItem(MaterialCenterItemDto dto) {
        MaterialCenterItem model = MaterialCenterConverter.itemDtoToModel(dto);
        materialCenterItemDao.updateById(model);
    }

    @Override
    public void deleteItem(Long id) {
        MaterialCenterItem model = materialCenterItemDao.getById(id);
        model.setDeleteTime(new Date());
        materialCenterItemDao.updateById(model);
    }
}