package com.rent.dao.components.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.components.AntiReqType;
import com.rent.common.enums.components.EnumAntChainLogStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AntChainLogDao;
import com.rent.mapper.components.AntChainLogMapper;
import com.rent.model.components.AntChainLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaowenchao
 */
@Repository
public class AntChainLogDaoImpl extends AbstractBaseDaoImpl<AntChainLog, AntChainLogMapper> implements AntChainLogDao {

    @Override
    public AntChainLog getSyncSuccessLog(String orderId, AntiReqType type) {

        List<AntChainLog> list = list(new QueryWrapper<AntChainLog>()
                .eq("order_id", orderId)
                .eq("type", type)
                .eq("status", EnumAntChainLogStatus.SUCCESS));
        if(CollectionUtil.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}
