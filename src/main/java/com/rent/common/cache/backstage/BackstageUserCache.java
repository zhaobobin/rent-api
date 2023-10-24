package com.rent.common.cache.backstage;

import com.alibaba.fastjson.JSON;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.user.BackstageUserDto;
import com.rent.util.RedisUtil;

import java.util.List;

/**
 * @author zhaowenchao
 */
public class BackstageUserCache {

    private static final String TOKEN_PREFIX = "BACKSTAGE_TOKEN::";
    private static final String USERID_TOKEN_PREFIX = "BACKSTAGE_USER_TOKEN::";
    private static final String BACKSTAGE_LOGIN_USER = "BACKSTAGE_LOGIN_USER::";
    private static final String AUTH_CACHE_PREFIX = "auth::";
    private static final Integer TOKEN_EXPIRE = 60 * 30;

    public static void refreshBackstageUserAuth(Long backstageUserId, List<String> functionCodes) {
        //保存用户权限信息
        RedisUtil.del(AUTH_CACHE_PREFIX + backstageUserId);
        RedisUtil.sSet(AUTH_CACHE_PREFIX + backstageUserId, functionCodes.toArray());
    }

    /**
     * 保存用户登录信息到缓存
     * 1）token和backstageUserId映射关系
     * 2）backstageUserId 和 LoginUserBo 映射
     * 2）backstageUserId 和 功能列表 映射
     *
     * @param token
     * @param backstageUserDto
     * @param isAuditUser
     * @param functionCodes
     */
    public static void setToken(String token, BackstageUserDto backstageUserDto, Boolean isAuditUser, List<String> functionCodes) {
        LoginUserBo loginUserBo = new LoginUserBo();
        loginUserBo.setId(backstageUserDto.getId());
        loginUserBo.setName(backstageUserDto.getName());
        loginUserBo.setType(backstageUserDto.getType());
        loginUserBo.setMobile(backstageUserDto.getMobile());
        loginUserBo.setShopId(backstageUserDto.getShopId());
        loginUserBo.setIsAuditUser(isAuditUser);
        // 记录当前用户已登陆,用于单点登陆
        RedisUtil.set(USERID_TOKEN_PREFIX + backstageUserDto.getId().toString(), token, TOKEN_EXPIRE);

        //保存token信息
        RedisUtil.set(TOKEN_PREFIX + token, backstageUserDto.getId().toString(), TOKEN_EXPIRE);
        //保存登录用户信息
        RedisUtil.hset(BACKSTAGE_LOGIN_USER, backstageUserDto.getId().toString(), JSON.toJSONString(loginUserBo));
        //保存用户权限信息
        refreshBackstageUserAuth(backstageUserDto.getId(), functionCodes);
    }

    public static String loginedUserToken(Long backstageUserId) {
        String key = USERID_TOKEN_PREFIX + backstageUserId.toString();
        if (!RedisUtil.hasKey(key)) {
            return null;
        }
        String token = (String) RedisUtil.get(key);
        if (!RedisUtil.hasKey(TOKEN_PREFIX + token)) {
            return null;
        }
        return (String) RedisUtil.get(key);
    }


    /**
     * 删除用户登录缓存信息，删除之后用户需要重新登录
     *
     * @param backstageUserId
     */
    public static void removeLoginUserInfo(Long backstageUserId) {
        RedisUtil.hdel(BACKSTAGE_LOGIN_USER, backstageUserId.toString());
    }

    /**
     * 根据token获取登录信息
     *
     * @param token
     * @return
     */
    public static LoginUserBo getLoginUserBo(String token) {
        //根据token获取登录用户ID
        Object backstageUserIdCache = RedisUtil.get(TOKEN_PREFIX + token);
        if (backstageUserIdCache == null) {
            return null;
        }
        //根据登录用户ID获取登录用户信息
        Object userCache = RedisUtil.hget(BACKSTAGE_LOGIN_USER, (String) backstageUserIdCache);
        if (userCache == null) {
            return null;
        }
        return JSON.parseObject((String) userCache, LoginUserBo.class);
    }

    /**
     * 校验用户是否有请求接口的权限
     *
     * @param userId
     * @param uri
     * @return
     */
    public static Boolean checkUserHasAuth(String userId, String uri) {
        return RedisUtil.sHasKey(AUTH_CACHE_PREFIX + userId, uri);
    }

    /**
     * 延长token过期时间
     *
     * @param token
     */
    public static void expireToken(String token) {
        RedisUtil.expire(TOKEN_PREFIX + token, TOKEN_EXPIRE);
    }

    public static void delToken(String token) {
        RedisUtil.del(TOKEN_PREFIX + token);
    }


}
