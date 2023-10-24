    
package com.rent.dao.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.OrderComplaintsImageDao;
import com.rent.mapper.marketing.OrderComplaintsImageMapper;
import com.rent.model.marketing.OrderComplaintsImage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderComplaintsImageDao
 *
 * @author youruo
 * @Date 2020-09-27 15:38
 */
@Repository
public class OrderComplaintsImageDaoImpl extends AbstractBaseDaoImpl<OrderComplaintsImage, OrderComplaintsImageMapper> implements OrderComplaintsImageDao {


    @Override
    public void batchSaveOrderComplaintsImage(List<String> images, Long complaintId) {
        Date now = new Date();
        List<OrderComplaintsImage> list = new ArrayList<>(images.size());
        for (String image : images) {
            OrderComplaintsImage model = new OrderComplaintsImage();
            model.setComplaintId(complaintId);
            model.setImageUrl(image);
            model.setCreateTime(now);
        }
        saveBatch(list);
    }

    @Override
    public List<String> getOrdOrderImages(Long complaintId) {
        List<String> result = new ArrayList<>();
        List<OrderComplaintsImage> images = list(new QueryWrapper<OrderComplaintsImage>().eq("complaint_id", complaintId));
        if (CollectionUtils.isNotEmpty(images)) {
            result = images.stream().map(t -> t.getImageUrl()).collect(Collectors.toList());
        }
        return result;
    }
}
