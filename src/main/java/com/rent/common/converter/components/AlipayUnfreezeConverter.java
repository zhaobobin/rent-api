package com.rent.common.converter.components;

import com.rent.common.dto.components.request.AlipayUnfreezeRequest;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.model.components.AlipayUnfreeze;

import java.util.Date;

/**
 * 支付宝资金授权解冻Service
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
public class AlipayUnfreezeConverter {

    public static AlipayUnfreeze assemblyAlipayUnfreeze(AlipayUnfreezeRequest unfreezeRequest, String outRequestNo,
        Date now) {
        if (null == unfreezeRequest) {
            return null;
        }
        AlipayUnfreeze unfreeze = new AlipayUnfreeze();
        unfreeze.setCreateTime(now);
        unfreeze.setUpdateTime(now);
        unfreeze.setAuthNo(unfreezeRequest.getAuthNo());
        unfreeze.setOrderId(unfreezeRequest.getOrderId());
        unfreeze.setUid(unfreezeRequest.getUid());
        unfreeze.setUnfreezeRequestNo(outRequestNo);
        unfreeze.setAmount(unfreezeRequest.getAmount());
        unfreeze.setRemark(unfreezeRequest.getRemark());
        unfreeze.setStatus(EnumAliPayStatus.PAYING);
        return unfreeze;
    }

}