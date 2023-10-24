package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayUnfreezeDao;
import com.rent.mapper.components.AlipayUnfreezeMapper;
import com.rent.model.components.AlipayUnfreeze;
import org.springframework.stereotype.Repository;

/**
 * AlipayUnfreezeDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
@Repository
public class AlipayUnfreezeDaoImpl extends AbstractBaseDaoImpl<AlipayUnfreeze, AlipayUnfreezeMapper> implements AlipayUnfreezeDao {

    @Override
    public AlipayUnfreeze getByUnfreezeRequestNo(String unfreezeRequestNo) {
        AlipayUnfreeze alipayUnfreeze = getOne(new QueryWrapper<AlipayUnfreeze>().eq("unfreeze_request_no",unfreezeRequestNo));
        return alipayUnfreeze;
    }
}
