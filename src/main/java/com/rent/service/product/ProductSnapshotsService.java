        
package com.rent.service.product;

import com.rent.common.dto.product.ProductSnapshotsDto;

import java.util.List;

/**
 * 商品快照表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:30
 */
public interface ProductSnapshotsService {


        //商家后台新增商品快照
        void insertProductSnap(String  itemId);


        /**
         * 查询快照记录信息集合
         * @param ids
         * @return
         */
        List<ProductSnapshotsDto> queryProductSnapshotsList(List<Integer> ids);


        /**
         * 查询一条商品快照ID
         * @param productId
         * @return
         */
        Integer queryProductSnapshotsId(String productId);


}