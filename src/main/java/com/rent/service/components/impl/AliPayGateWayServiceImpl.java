package com.rent.service.components.impl;


import com.rent.dao.components.AlipayGateWayDao;
import com.rent.model.components.AlipayGateWay;
import com.rent.service.components.AliPayGateWayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author zhaowenchao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliPayGateWayServiceImpl implements AliPayGateWayService {

    private final AlipayGateWayDao alipayGateWayDao;

    @Override
    public void save(AlipayGateWay data) {
        data.setCreateTime(new Date());
        alipayGateWayDao.save(data);
    }
}
