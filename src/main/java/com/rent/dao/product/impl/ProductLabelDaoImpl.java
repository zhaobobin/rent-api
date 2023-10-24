    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductLabelDao;
import com.rent.mapper.product.ProductLabelMapper;
import com.rent.model.product.ProductLabel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProductLabelDao
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
@Repository
public class ProductLabelDaoImpl extends AbstractBaseDaoImpl<ProductLabel, ProductLabelMapper> implements ProductLabelDao {

    @Override
    public Map<String,List<String>> getProductLabelList(List<String> productIdList) {
        List<ProductLabel> productLabels = list(new QueryWrapper<ProductLabel>()
                .select("item_id,label")
                .in("item_id", productIdList)
                .isNull("delete_time"));

        Map<String,List<String>> map = new HashMap<>();
        for (ProductLabel productLabel : productLabels) {
            String itemId = productLabel.getItemId();
            String label = productLabel.getLabel();
            List<String> labelList = map.get(itemId);
            if(labelList==null){
                labelList =  new ArrayList<>();
                labelList.add(label);
                map.put(itemId,labelList);
            }else {
                labelList.add(label);
            }
        }
        return map;
    }
}
