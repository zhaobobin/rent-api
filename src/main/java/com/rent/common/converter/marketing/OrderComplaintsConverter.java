        
package com.rent.common.converter.marketing;

import com.rent.common.dto.api.request.SubmitOrderComplaintReqVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsDetailRespVo;
import com.rent.common.enums.marketing.OrderComplaintStatusEnum;
import com.rent.model.marketing.OrderComplaints;
import com.rent.util.AppParamUtil;

import java.util.Date;



public class OrderComplaintsConverter {

    public static OrderComplaints submitVo2Model(SubmitOrderComplaintReqVo vo) {
        if (vo == null) {
            return null;
        }
        Date now = new Date();
        OrderComplaints model = new OrderComplaints();
        model.setUid(vo.getUid());
        model.setTelphone(vo.getTelphone());
        model.setName(vo.getName());
        model.setContent(vo.getContent());
        model.setTypeId(vo.getTypeId());
        model.setOrderId(vo.getOrderId());
        model.setShopId(vo.getShopId());
        model.setCreateTime(now);
        model.setUpdateTime(now);
        model.setChannel(AppParamUtil.getChannelId());
        model.setStatus(OrderComplaintStatusEnum.INIT);
        return model;
    }

    public static OrderComplaintsDetailRespVo model2DetailRespVo(OrderComplaints model) {
        if (model == null) {
            return null;
        }
        OrderComplaintsDetailRespVo vo = new OrderComplaintsDetailRespVo();
        vo.setId(model.getId());
        vo.setName(model.getName());
        vo.setOrderId(model.getOrderId());
        vo.setTelphone(model.getTelphone());
        vo.setCreateTime(model.getCreateTime());
        vo.setContent(model.getContent());
        vo.setResult(model.getResult());
        return vo;
    }
}