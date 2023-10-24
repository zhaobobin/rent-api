
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.CategoryType;
import com.rent.common.converter.product.OpeCategoryConverter;
import com.rent.common.dto.marketing.OpeCategoryDto;
import com.rent.common.dto.product.CategoryProductResp;
import com.rent.common.dto.product.LiteCategoryDto;
import com.rent.common.dto.product.OpeCategoryReqDto;
import com.rent.common.dto.product.TabProductResp;
import com.rent.common.enums.product.*;
import com.rent.common.util.CheckDataUtils;
import com.rent.dao.product.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.OpeCategory;
import com.rent.model.product.OpeCategoryProduct;
import com.rent.model.product.OpeConfig;
import com.rent.model.product.Product;
import com.rent.service.product.OpeCategoryService;
import com.rent.service.product.OpeConfigService;
import com.rent.service.product.PlatformChannelService;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 前台类目控制器Service
 *
 * @author youruo
 * @Date 2020-06-15 11:07
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpeCategoryServiceImpl implements OpeCategoryService {

    private static final String PRODUCT_CHANNEL_INDEX_CATE = "product:index:cate:";

    private final OpeCategoryDao opeCategoryDao;
    private final OpeCategoryProductDao opeCategoryProductDao;
    private final OpeConfigDao opeConfigDao;
    private final ProductLabelDao productLabelDao;
    private final OpeConfigService opeConfigService;
    private final PlatformChannelService platformChannelService;
    private final ProductDao productDao;


    @Override
    public List<OpeCategoryDto> selectParentCategoryList() {
        List<OpeCategoryDto> result = new ArrayList<>();
        QueryWrapper<OpeCategory> wh = new QueryWrapper<>();
        wh.eq("parent_id", 0).eq("status", ProductStatus.STATUS.getCode()).isNull("delete_time").orderByAsc("sort_rule");
        result = OpeCategoryConverter.modelList2DtoList(this.opeCategoryDao.list(wh));
        if (CollectionUtils.isNotEmpty(result)) {
            result = result.stream().map(item -> {
                List<String> channelIds = new ArrayList<String>();
                List<String> channelNames = new ArrayList<String>();
                List<OpeConfig> opeConfigList = this.opeConfigService.getOpeConfigList(OpeConfigType.CATE, item.getId(), null);
                if (CollectionUtils.isNotEmpty(opeConfigList)) {
                    channelIds = opeConfigList.stream().map(OpeConfig::getChannelId).collect(toList());
                    channelNames = this.platformChannelService.getPlatformChannelList(channelIds);
                }
                item.setChannerIds(channelIds);
                item.setChannerNames(channelNames);
                return item;
            }).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public Integer addOpeCategory(OpeCategoryDto request) {
        OpeCategory model = OpeCategoryConverter.dto2Model(request);
        model.setStatus(null != request.getStatus() ? request.getStatus() : ProductStatus.STATUS.getCode());
        String name = request.getName();
        String type = request.getType();
        if (StringUtils.isNotEmpty(request.getZfbCode())) {
            request.setZfbCode(request.getZfbCode().replaceAll(" ", ""));
        }
        log.info("OpeCategoryController: addOperateCategory :" + request.toString());
        Date now = new Date();
        model.setUpdateTime(now);
        OpeCategory bean = new OpeCategory();
        bean.setType(StringUtils.isNotEmpty(type) ? type : CategoryType.FIRST_CATEGORY);
        if (type.equals(CategoryType.FIRST_CATEGORY)) {
            Integer parentId = 0;
            //一级类目
            OpeCategory opeCategoryCheck = opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                    .eq("name", name)
                    .eq("parent_id", parentId)
                    .isNull("delete_time")
                    .last("limit 1")
            );
            if (opeCategoryCheck != null) {
                throw new HzsxBizException("-1","类目已存在");
            }
            model.setCreateTime(now);
            opeCategoryDao.save(model);
            if (!CollectionUtils.isEmpty(request.getChannerIds()) && null != model.getId()) {
                opeConfigService.updateOpeConfig(OpeConfigType.CATE, model.getId(), request.getChannerIds());
            }
            RedisUtil.expirebatch(PRODUCT_CHANNEL_INDEX_CATE, 1);
            return model.getId();
        }

        if (type.equals(CategoryType.TWO_CATEGORY) || type.equals(CategoryType.THREE_CATEGORY)) {
            //二级类目&&三级类目
            OpeCategory opeCategoryCheck = opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                    .eq("`name`", name)
                    .eq("parent_id", request.getParentId())
                    .isNull("delete_time")
                    .last("limit 1")
            );
            if (opeCategoryCheck != null) {
                throw new HzsxBizException("-1","类目已存在");
            }
            model.setCreateTime(now);
            opeCategoryDao.save(model);
            if (!CollectionUtils.isEmpty(request.getChannerIds()) && null != model.getId()) {
                opeConfigService.updateOpeConfig(OpeConfigType.CATE, model.getId(), request.getChannerIds());
            }
            RedisUtil.expirebatch(PRODUCT_CHANNEL_INDEX_CATE, 1);
            return model.getId();
        }
        log.error("addOperateCategory: type error");
        throw new MybatisPlusException("保存数据失败");
    }

    @Override
    public Page<OpeCategoryDto> queryOpeCategoryPage(OpeCategoryReqDto request) {
        Page<OpeCategory> page = opeCategoryDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<OpeCategory>()
                        .eq(null != request.getParentId(), "parent_id", request.getParentId())
                        .eq(null != request.getId(),"id",request.getId())
                        .like(StringUtils.isNotEmpty(request.getName()),"name",request.getName())
                        .isNull("delete_time")
                        .orderByAsc("sort_rule")
        );
        List<OpeCategoryDto> records = OpeCategoryConverter.modelList2DtoList(page.getRecords());
        if (CollectionUtils.isNotEmpty(records)) {
            for (OpeCategoryDto dto : records) {
                List<String> channelIds = new ArrayList<String>();
                List<String> channelNames = new ArrayList<String>();
                List<OpeConfig> opeConfigList = this.opeConfigService.getOpeConfigList(OpeConfigType.CATE, dto.getId(), null);
                if (CollectionUtils.isNotEmpty(opeConfigList)) {
                    channelIds = opeConfigList.stream().map(OpeConfig::getChannelId).collect(toList());
                    channelNames = this.platformChannelService.getPlatformChannelList(channelIds);
                }
                dto.setChannerIds(channelIds);
                dto.setChannerNames(channelNames);
            }
        }
        return new Page<OpeCategoryDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(records);
    }

    @Override
    public Boolean modifyOpeCategory(OpeCategoryDto request) {
        if (StringUtils.isNotEmpty(request.getZfbCode())) {
            request.setZfbCode(request.getZfbCode().replaceAll(" ", ""));
        }
        request.setUpdateTime(new Date());
        if (!CollectionUtils.isEmpty(request.getChannerIds()) && null != request.getId()) {
            opeConfigService.updateOpeConfig(OpeConfigType.CATE, request.getId(), request.getChannerIds());
        }
        opeCategoryDao.updateById(OpeCategoryConverter.dto2Model(request));
        RedisUtil.expirebatch(PRODUCT_CHANNEL_INDEX_CATE, 1);
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeOperateCategory(Integer id) {
        CheckDataUtils.judgeNull(id);
        //如果删除的是1级类目，将2级类目删除
        OpeCategory opeCategory = opeCategoryDao.getOne(new QueryWrapper<OpeCategory>()
                .eq("id", id)
                .isNull("delete_time")
        );
        if (null == opeCategory) {
            CheckDataUtils.dataError("类目数据不存在");
        }
        Date now = new Date();
        Integer parentId = opeCategory.getParentId();
        if (parentId <= 0) {
            //一级
            OpeCategory bean = new OpeCategory();
            bean.setId(id);
            bean.setDeleteTime(now);
            opeCategoryDao.updateById(bean);
            opeConfigService.deleteOpeConfig(OpeConfigType.CATE, bean.getId());
            //删除二级
            List<OpeCategory> opeCategories = opeCategoryDao.list(new QueryWrapper<OpeCategory>().eq("parent_id", id).isNull("delete_time")
            );
            for (OpeCategory category : opeCategories) {
                bean.setId(category.getId());
                bean.setDeleteTime(new Date());
                opeCategoryDao.updateById(bean);
                //删除来源渠道
                opeConfigService.deleteOpeConfig(OpeConfigType.CATE, category.getId());
                //删除类目下商品
                List<OpeCategoryProduct> opeCategoryProducts = opeCategoryProductDao.list(new QueryWrapper<OpeCategoryProduct>()
                        .eq("operate_category_id", category.getId())
                        .isNull("delete_time")
                );

                for (OpeCategoryProduct categoryProduct : opeCategoryProducts) {
                    categoryProduct.setUpdateTime(now);
                    categoryProduct.setDeleteTime(now);
                    this.opeCategoryProductDao.updateById(categoryProduct);
                }
            }
        } else {
            OpeCategory bean = new OpeCategory();
            bean.setId(id);
            bean.setDeleteTime(now);
            this.opeCategoryDao.updateById(bean);
            //删除类目下商品
            List<OpeCategoryProduct> opeCategoryProducts = opeCategoryProductDao.list(new QueryWrapper<OpeCategoryProduct>()
                    .eq("operate_category_id", id)
                    .isNull("delete_time")
            );

            for (OpeCategoryProduct categoryProduct : opeCategoryProducts) {
                categoryProduct.setUpdateTime(now);
                categoryProduct.setDeleteTime(now);
                this.opeCategoryProductDao.updateById(categoryProduct);
            }
        }
        RedisUtil.expirebatch(PRODUCT_CHANNEL_INDEX_CATE, 1);
        return Boolean.TRUE;
    }

    @Override
    public List<LiteCategoryDto> liteCategory(Integer parentId, String channelId) {
        List<OpeCategory> list = opeCategoryDao.list(new QueryWrapper<OpeCategory>()
                .eq("parent_id", parentId)
                .eq("status", ProductStatus.STATUS.getCode())
                .isNull("delete_time").orderByAsc("sort_rule")
        );

        Set<Integer> configIdSet = opeConfigDao.getConfigId(OpeConfigType.CATE,channelId);
        List<LiteCategoryDto> liteCategoryDtoList = new ArrayList<>(list.size());
        for (OpeCategory opeCategory : list) {
            if(configIdSet.contains(opeCategory.getId())){
                LiteCategoryDto dto = new LiteCategoryDto();
                dto.setId(opeCategory.getId());
                dto.setName(opeCategory.getName());
                liteCategoryDtoList.add(dto);
            }
        }
        return liteCategoryDtoList;
    }

    @Override
    public Page<CategoryProductResp> listProduct(Integer categoryId, Integer pageNum, Integer pageSize) {

        List<Product> validProduct = productDao.list(new QueryWrapper<Product>().select("product_id")
                .eq("audit_state", EnumProductAuditState.PASS)
                .eq("type", EnumProductType.ON_SHELF)
                .eq("status", EnumProductStatus.VALID)
                .isNull("delete_time"));
        List<String> queryProductIdList = validProduct.stream().map(Product::getProductId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(queryProductIdList)){
            return new Page<TabProductResp>(pageNum, pageSize, 0).setRecords(Collections.EMPTY_LIST);
        }

        Page<OpeCategoryProduct> page = opeCategoryProductDao.page(new Page<>(pageNum, pageSize),
                new QueryWrapper<OpeCategoryProduct>()
                        .isNull("delete_time")
                        .in("item_id",queryProductIdList)
                        .eq("status", ProductStatus.STATUS.getCode())
                        .and(wrapper -> wrapper
                                .eq("parent_category_id",categoryId)
                                .or()
                                .eq("operate_category_id",categoryId))
                        .orderByDesc("update_time"));
        if(CollectionUtils.isEmpty(page.getRecords())){
            return new Page<TabProductResp>(pageNum, pageSize, page.getTotal()).setRecords(Collections.EMPTY_LIST);
        }
        List<String> productIdList  = page.getRecords().stream().map(OpeCategoryProduct::getItemId).collect(toList());
        Map<String,List<String>> labelsMap = productLabelDao.getProductLabelList(productIdList);
        return new Page<CategoryProductResp>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                OpeCategoryConverter.opeProductModelToResp(page.getRecords(),labelsMap));
    }

    @Override
    public List<Integer> getCateIds(Integer categoryId, Integer status) {
        List<Integer> reseult = new ArrayList<>();
        if(null == categoryId){
            return reseult;
        }
        QueryWrapper<OpeCategory> wh = new QueryWrapper<>();
        wh.eq("id", categoryId).isNull("delete_time").last("limit 1");
        OpeCategory ope = this.opeCategoryDao.getOne(wh);
        if (null != ope) {
            switch (ope.getType()) {
                case CategoryType.TWO_CATEGORY:
                    List<OpeCategory> opes = opeCategoryDao.list(new QueryWrapper<OpeCategory>()
                            .select("id")
                            .eq("parent_id", categoryId)
                            .eq(null != status, "status", status)
                            .isNull("delete_time")
                    );
                    if (CollectionUtils.isNotEmpty(opes)) {
                        reseult = opes.stream().map(OpeCategory::getId).collect(Collectors.toList());
                    }
                    reseult.add(categoryId);
                    break;
                case CategoryType.THREE_CATEGORY:
                    reseult.add(categoryId);
                    break;
                case CategoryType.FIRST_CATEGORY:
                    List<OpeCategory> twoOpes = opeCategoryDao.list(new QueryWrapper<OpeCategory>()
                            .select("id")
                            .eq("parent_id", categoryId)
                            .eq(null != status, "status", status)
                            .isNull("delete_time")
                    );
                    if(CollectionUtils.isNotEmpty(twoOpes)){
                        reseult = twoOpes.stream().map(OpeCategory::getId).collect(Collectors.toList());
                        List<OpeCategory> threeOpes = opeCategoryDao.list(new QueryWrapper<OpeCategory>()
                                .select("id")
                                .in("parent_id", reseult)
                                .eq(null != status, "status", status)
                                .isNull("delete_time")
                        );
                        if(CollectionUtils.isNotEmpty(threeOpes)){
                            reseult.addAll(threeOpes.stream().map(OpeCategory::getId).collect(Collectors.toList()));
                        }
                    }
                    reseult.add(categoryId);
                    break;
                default:
                    break;
            }
        }
        return reseult;
    }


}