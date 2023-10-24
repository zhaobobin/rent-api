package com.rent.common.cache.user;

import com.alibaba.fastjson.JSON;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.util.StringUtil;
import com.rent.util.RedisUtil;

/**
 * @author zhaowenchao
 */
public class UserCertificationCache {

    private static final String USER_CERTIFICATION = "USER_CERTIFICATION";

    public static  String hasCertification(String uid){
        UserCertificationDto dto = getUserCertificationCache(uid);
        if(dto==null) {
            return "UN_CERT";
        }else {
            if(StringUtil.isEmpty(dto.getIdCardFrontUrl())){
                return "UN_UPLOAD";
            }else {
                return "FINISH";
            }
        }
    }

    public static  Boolean setUserCertificationCache(UserCertificationDto dto){
        if(dto==null){
            return Boolean.FALSE;
        }
        return RedisUtil.hset(USER_CERTIFICATION,dto.getUid(), JSON.toJSONString(dto));
    }

    public static  UserCertificationDto getUserCertificationCache(String uid){
        Object cache = RedisUtil.hget(USER_CERTIFICATION,uid);
        if(cache!=null){
            return JSON.parseObject((String)cache, UserCertificationDto.class);
        }
        return null;
    }





}
