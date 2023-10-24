        
package com.rent.dao.marketing;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.marketing.OrderComplaintsImage;

import java.util.List;


public interface OrderComplaintsImageDao extends IBaseDao<OrderComplaintsImage> {

    /**
     * 批量录入投诉图片
     * @param images
     */
    void batchSaveOrderComplaintsImage(List<String> images, Long complaintId);

    /**
     * 根据投诉ID获取图片列表
     * @param complaintId
     * @return
     */
    List<String> getOrdOrderImages(Long complaintId);
}
