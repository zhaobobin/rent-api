
package com.rent.service.user.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.RedisKey;
import com.rent.dao.product.ChannelStoreDao;
import com.rent.dao.user.UserPointDao;
import com.rent.model.order.UserPointDto;
import com.rent.model.product.ChannelStore;
import com.rent.model.user.UserPoint;
import com.rent.service.user.UserPointService;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户活动埋点统计Service
 *
 * @author youruo
 * @Date 2020-09-25 14:38
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserPointServiceImpl implements UserPointService {

    private final UserPointDao userPointDao;
    private final ChannelStoreDao channelStoreDao;

    @Override
    public Boolean insertUserPoint(String position, String uid, String action, String channelId) {

        ChannelStore channelStore = channelStoreDao.getOne(new QueryWrapper<ChannelStore>().eq("marketing_id", channelId));
        if (Objects.isNull(channelStore)) {
            return Boolean.FALSE;
        }

        // 失效保护开关为1，无论用户是第几次扫这个码扫进来都算这个链接的保护订单
        if (channelStore.getExpireSwitch() == 1) {
            String catchKey = RedisKey.CHANNEL_USER_IN_7_DAYS + uid;
            // 若未设置失效保护天则默认30天
            int expireDay = channelStore.getExpireDay() != null ? channelStore.getExpireDay() : 30;
            RedisUtil.set(catchKey, JSON.toJSONString(channelStore), 60L * 60 * 24 * expireDay);
        } else {
            // 若expireSwitch为0 则用户只有第一次扫码进来计算为这个链接的保护订单
            List<UserPoint> list = userPointDao.list(new QueryWrapper<UserPoint>().select("id").eq("uid", uid).last("limit 1"));
            if (list.size() == 0) {
                String catchKey = RedisKey.CHANNEL_USER_IN_7_DAYS + uid;
                // 若未设置失效保护天则默认30天
                int expireDay = channelStore.getExpireDay() != null ? channelStore.getExpireDay() : 30;
                RedisUtil.set(catchKey, JSON.toJSONString(channelStore), 60L * 60 * 24 * expireDay);
            }
        }

        if (!"channelId".equals(channelId)) {
            UserPoint point = new UserPoint();
            point.setPosition(position);
            point.setUid(uid);
            point.setChannelId(channelId);
            point.setAction(action);
            point.setCreateTime(new Date());
            this.userPointDao.save(point);
        }
        return Boolean.TRUE;
    }


    @Override
    public List<UserPointDto> getUserSource(String position, String action, String channelId) {
        List<UserPointDto> list = userPointDao.getAllData();
        Set<UserPointDto> set = new HashSet<>();
        for (UserPointDto userPointDto : list) {
            set.add(userPointDto);
        }
        List<UserPointDto> result = new ArrayList<>();
        for (UserPointDto userPointDto : set) {
            if (userPointDto.getAction().equals(action)
                    && userPointDto.getPosition().equals(position)
                    && userPointDto.getChannelId().equals(channelId)
            ) {
                userPointDto.setAction(null);
                userPointDto.setPosition(null);
                userPointDto.setChannelId(null);
                result.add(userPointDto);
            }
        }
        log.info("【获取渠道的用户】的列表长度={}", new Object[]{result.size()});
        return result;
    }


}