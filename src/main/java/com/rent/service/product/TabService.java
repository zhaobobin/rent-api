        
package com.rent.service.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.*;
import com.rent.common.dto.backstage.resp.BackstageTabProductResp;
import com.rent.common.dto.backstage.resp.TabDetailResp;
import com.rent.common.dto.backstage.resp.TabListResp;
import com.rent.common.dto.product.AvailableTabProductResp;
import com.rent.common.dto.product.TabProductResp;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface TabService {

        /**
         * 新增tab
         * @param request
         * @return
         */
        Boolean add(AddTabReq request);

        /**
         * 修改Tab
         * @param request
         * @return
         */
        Boolean update(UpdateTabReq request);

        /**
         * 删除Tab
         * @param id
         * @return
         */
        Boolean delete(Long id);

        /**
         * 根据渠道ID获取tab列表
         * @param channelId
         * @return
         */
        List<TabListResp> list(String channelId);

        /**
         * 根据ID查询一个
         * @param id
         * @return
         */
        TabDetailResp getById(Long id);

        /**
         * 查询tab可以添加的商品
         * @param request
         * @return
         */
        Page<AvailableTabProductResp> getAvailableProduct(QueryAvailableTabProductReq request);

        /**
         * tab下添加商品
         * @param request
         * @return
         */
        Boolean addProduct(AddTabProductReq request);

        /**
         * 小程序查询tab下的商品
         * @param tabId
         * @param pageNum
         * @param pageSize
         * @return
         */
        Page<TabProductResp> listProductForApi(Long tabId, Integer pageNum, Integer pageSize);

        /**
         * 运营后台查询tab下的商品
         * @param tabId
         * @param pageNum
         * @param pageSize
         * @return
         */
        Page<BackstageTabProductResp> listProduct(Long tabId, Integer pageNum, Integer pageSize);

        /**
         * 更新tab下商品的顺序
         * @param request
         * @return
         */
        Boolean updateProductSort(UpdateTabProductSortReq request);

        /**
         * 删除tab下某个商品
         * @param id
         * @return
         */
        Boolean deleteProduct(Long id);


}