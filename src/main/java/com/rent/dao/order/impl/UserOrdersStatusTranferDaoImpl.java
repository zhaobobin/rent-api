package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.UserOrdersStatusTranferDao;
import com.rent.mapper.order.UserOrdersStatusTranferMapper;
import com.rent.model.order.UserOrdersStatusTranfer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserOrdersStatusTranferDao
 *
 * @author xiaoyao
 * @Date 2020-07-23 16:06
 */
@Repository
public class UserOrdersStatusTranferDaoImpl
    extends AbstractBaseDaoImpl<UserOrdersStatusTranfer, UserOrdersStatusTranferMapper>
    implements UserOrdersStatusTranferDao {

    @Override
    public List<UserOrdersStatusTranfer> queryByOrderId(String orderId) {
        return this.getBaseMapper()
            .selectList(new QueryWrapper<>(UserOrdersStatusTranfer.builder()
                .orderId(orderId)
                .build()).orderByDesc("id"));
    }
}
