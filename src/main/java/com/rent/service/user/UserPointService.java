
package com.rent.service.user;

import com.rent.model.order.UserPointDto;

import java.util.List;

/**
 * 用户活动埋点统计Service
 *
 * @author youruo
 * @Date 2020-09-25 14:38
 */
public interface UserPointService {



    Boolean insertUserPoint(String position, String uid, String action, String channelId);


    /**
     * 获取渠道的用户
     * @param position
     * @param action
     * @param channelId
     * @return
     */
    List<UserPointDto> getUserSource(String position, String action, String channelId);
}