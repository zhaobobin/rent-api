package com.rent.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.marketing.MaterialCenterCategoryDto;
import com.rent.common.dto.marketing.MaterialCenterCategoryReqDto;
import com.rent.common.dto.marketing.MaterialCenterItemDto;
import com.rent.common.dto.marketing.MaterialCenterItemReqDto;


/**
 * 素材中心
 * @author zhaowenchao
 */
public interface MaterialCenterService {

    /**
     * 添加分类
     * @param request
     * @return
     */
    Long addCategory(MaterialCenterCategoryDto request);

    /**
     * 删除分类。与此同时删除分类对应的素材
     * @param id
     */
    void deleteCategory(Long id);

    /**
     * 查看素材详情
     * @param id
     * @return
     */
    MaterialCenterCategoryDto detailCategory(Long id);

    /**
     * 分页查询分类
     * @param request
     * @return
     */
    Page<MaterialCenterCategoryDto> pageCategory(MaterialCenterCategoryReqDto request);

    /**
     * 分页查询某个分类下的素材信息
     * @param dto
     * @return
     */
    Page<MaterialCenterItemDto> pageItem(MaterialCenterItemReqDto dto);

    /**
     * 新增素材
     * @param dto
     * @return
     */
    Long addItem(MaterialCenterItemDto dto);

    /**
     * 查询单个素材
     * @param id
     * @return
     */
    MaterialCenterItemDto detailItem(Long id);

    /**
     * 修改素材信息
     * @param dto
     */
    void updateItem(MaterialCenterItemDto dto);

    /**
     * 删除一个素材信息
     * @param id
     */
    void deleteItem(Long id);


}