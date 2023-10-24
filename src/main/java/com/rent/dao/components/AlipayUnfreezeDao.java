package com.rent.dao.components;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AlipayUnfreeze;

/**
 * AlipayUnfreezeDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
public interface AlipayUnfreezeDao extends IBaseDao<AlipayUnfreeze> {

    /**
     * 根据unfreezeRequestNo查询
     * @param unfreezeRequestNo
     * @return
     */
    AlipayUnfreeze getByUnfreezeRequestNo(String unfreezeRequestNo);
}
