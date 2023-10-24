
package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.TransferOrderRecordDto;
import com.rent.model.order.TransferOrderRecord;

import java.util.ArrayList;
import java.util.List;


/**
 * 转单记录表Service
 *
 * @author youruo
 * @Date 2021-12-22 17:55
 */
public class TransferOrderRecordConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static TransferOrderRecordDto model2Dto(TransferOrderRecord model) {
        if (model == null) {
            return null;
        }
        TransferOrderRecordDto dto = new TransferOrderRecordDto();
        dto.setId(model.getId());
        dto.setOrderId(model.getOrderId());
        dto.setTransferredProductId(model.getTransferredProductId());
        dto.setTransferredSnapShotId(model.getTransferredSnapShotId());
        dto.setTransferredSkuId(model.getTransferredSkuId());
        dto.setTransferProductId(model.getTransferProductId());
        dto.setTransferSnapShotId(model.getTransferSnapShotId());
        dto.setTransferSkuId(model.getTransferSkuId());
        dto.setTransferredShopId(model.getTransferredShopId());
        dto.setTransferShopId(model.getTransferShopId());
        dto.setOperator(model.getOperator());
        dto.setRemark(model.getRemark());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<TransferOrderRecordDto> modelList2DtoList(List<TransferOrderRecord> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), TransferOrderRecordConverter::model2Dto));
    }
}