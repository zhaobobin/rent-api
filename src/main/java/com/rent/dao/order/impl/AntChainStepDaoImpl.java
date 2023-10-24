package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.AntChainStepDao;
import com.rent.mapper.order.AntChainStepMapper;
import com.rent.model.order.AntChainStep;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * AccountPeriodDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
@Repository
public class AntChainStepDaoImpl extends AbstractBaseDaoImpl<AntChainStep, AntChainStepMapper> implements AntChainStepDao {

    @Override
    public AntChainStep getByOrderId(String orderId) {
        return getOne(new QueryWrapper<AntChainStep>().eq("order_id",orderId));
    }

    @Override
    public Map<String, AntChainStep> getByOrderIds(List<String> orderId) {
        List<AntChainStep> list = list(new QueryWrapper<AntChainStep>().in("order_id",orderId));
        return list.stream().collect(Collectors.toMap(AntChainStep::getOrderId, Function.identity()));
    }

    @Override
    public void updateShieldScore(String orderId, String shieldScore) {
        AntChainStep step = getByOrderId(orderId);
        if(step==null){
            step = new AntChainStep();
            step.setOrderId(orderId);
            step.setShieldScore(shieldScore);
            step.setCreateTime(new Date());
            save(step);
        }else {
            step.setShieldScore(shieldScore);
            updateById(step);
        }
    }

    @Override
    public void updateSyncToChain(String orderId) {
        AntChainStep step = getByOrderId(orderId);
        if(step==null){
            step = new AntChainStep();
            step.setOrderId(orderId);
            step.setSyncToChain(Boolean.TRUE);
            step.setCreateTime(new Date());
            save(step);
        }else {
            step.setSyncToChain(Boolean.TRUE);
            updateById(step);
        }
    }

    @Override
    public void updateInsure(String orderId) {
        AntChainStep step = getByOrderId(orderId);
        if(step==null){
            step = new AntChainStep();
            step.setOrderId(orderId);
            step.setInsure(Boolean.TRUE);
            step.setCreateTime(new Date());
            save(step);
        }else {
            step.setInsure(Boolean.TRUE);
            updateById(step);
        }
    }

    @Override
    public void updateUnInsure(String orderId) {
        AntChainStep step = getByOrderId(orderId);
        if(step==null){
            step = new AntChainStep();
            step.setOrderId(orderId);
            step.setInsure(Boolean.FALSE);
            step.setCreateTime(new Date());
            save(step);
        }else {
            step.setInsure(Boolean.FALSE);
            updateById(step);
        }
    }
}
