package com.rent.service.order.impl;


import com.rent.common.converter.order.UserOrdersStatusTransferConverter;
import com.rent.common.dto.order.UserOrdersStatusTransferDto;
import com.rent.dao.order.UserOrdersStatusTranferDao;
import com.rent.model.order.UserOrdersStatusTranfer;
import com.rent.service.order.UserOrdersStatusTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-10-26 下午 4:31:18
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UserOrdersStatusTransferServiceImpl implements UserOrdersStatusTransferService {

    private final UserOrdersStatusTranferDao userOrdersStatusTranferDao;

    @Override
    public List<UserOrdersStatusTransferDto> queryRecordByOrderId(String orderId) {
        List<UserOrdersStatusTranfer> userOrdersStatusTranfers = userOrdersStatusTranferDao.queryByOrderId(orderId);
        return UserOrdersStatusTransferConverter.modelList2DtoList(userOrdersStatusTranfers);
    }
}
