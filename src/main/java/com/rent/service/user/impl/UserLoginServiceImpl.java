package com.rent.service.user.impl;

import com.rent.common.converter.user.UserThirdInfoConverter;
import com.rent.common.dto.components.response.AlipayOauthTokenResponse;
import com.rent.common.dto.user.AlipayExemptLoginReq;
import com.rent.common.dto.user.UserThirdInfoDto;
import com.rent.dao.user.UserDao;
import com.rent.dao.user.UserThirdInfoDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.user.User;
import com.rent.model.user.UserThirdInfo;
import com.rent.service.components.AliPayService;
import com.rent.service.user.UserLoginService;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 用户认证表Service
 *
 * @author zhao
 * @Date 2020-06-18 15:06
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserThirdInfoDao userThirdInfoDao;
    private final UserDao userDao;
    private final AliPayService aliPayService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserThirdInfoDto alipayExemptLogin(AlipayExemptLoginReq req) {
        String authCode = req.getAuthCode();
        AlipayOauthTokenResponse response = aliPayService.getAliPaySystemOauthToken(authCode);
        String userId = StringUtils.isNotEmpty(response.getUserId()) ? response.getUserId() : response.getOpenId();
        String lockKey = "alipayExemptLogin::" + userId;
        try {
            if (!RedisUtil.tryLock(lockKey, 10)) {
                TimeUnit.MILLISECONDS.sleep(500);
            }
            UserThirdInfo userThirdInfo = exemptLogin(userId, req);
            UserThirdInfoDto dto = UserThirdInfoConverter.model2Dto(userThirdInfo);
            return dto;
        } catch (Exception e) {
            log.error("【用户登录异常】", e);
            throw new HzsxBizException("-1", "登录异常");
        } finally {
            RedisUtil.unLock(lockKey);
        }
    }


    /**
     * 小程序免密登录的时候一些公共的操作
     *
     * @param thirdId
     * @param req
     * @return
     */
    private UserThirdInfo exemptLogin(String thirdId, AlipayExemptLoginReq req) {
        String telephone = req.getTelephone();
        /**根据支付宝用户id查询用户，查询不到记录说明之前未登录过，校验手机号不能为空*/
        UserThirdInfo userThirdInfo = userThirdInfoDao.getByThirdId(thirdId);
        if (userThirdInfo == null && StringUtil.isNotEmpty(telephone)) {
            userThirdInfo = userThirdInfoDao.getByTelephoneAndChannelType(telephone, "ALIPAY");
        }
        /**未查询到构造用户*/
        if (userThirdInfo == null || !userThirdInfo.getThirdId().equals(thirdId)) {
            userThirdInfo = new UserThirdInfo();
            userThirdInfo.setCreateTime(new Date());
            userThirdInfo.setUserType("3");
            userThirdInfo.setThirdId(thirdId);
            userThirdInfo.setIsCertified("F");
            userThirdInfo.setTelephone(StringUtil.isNotEmpty(telephone) ? telephone : null);
            userThirdInfo.setIsStudentCetified("3");
        } else if (StringUtils.isEmpty(userThirdInfo.getThirdId())) {
            userThirdInfo.setThirdId(thirdId);
        }

        if (Objects.isNull(userThirdInfo.getId())) {
            String uid = userDao.addUser(telephone);
            userThirdInfo.setUid(uid);
        }
        // 处理用户未设置头像的昵称的情况
        if (!Objects.isNull(req.getNoSet())) {
            Map<String, Boolean> noSet = req.getNoSet();
            if (!Objects.isNull(noSet.get("avatar"))) {
                req.setAvatar("https://zos.alipayobjects.com/rmsportal/KvrblYonokCmLIK.png");
            }
            if (!Objects.isNull(noSet.get("nickName"))) {
                req.setNickName("用户" + userThirdInfo.getUid().substring(0, 8));
            }
        }

        BeanUtils.copyProperties(req, userThirdInfo);
        userThirdInfo.setUpdateTime(new Date());
        if (StringUtils.isEmpty(userThirdInfo.getTelephone()) && StringUtils.isNotEmpty(telephone)) {
            userThirdInfo.setTelephone(telephone);
        }
        userThirdInfo.setChannel("ALIPAY");
        userThirdInfoDao.saveOrUpdate(userThirdInfo);
        User user = userDao.getUserByUid(userThirdInfo.getUid());
        userThirdInfo.setIsCertified(user.getIsAuth() ? "T" : "F");
        if (StringUtils.isEmpty(user.getTelephone()) && StringUtils.isNotEmpty(telephone)) {
            userDao.updateById(User.builder().id(user.getId()).updateTime(new Date())
                    .telephone(telephone).build());
        }

        UserThirdInfo updatedInfo = userThirdInfoDao.getById(userThirdInfo.getId());
        return updatedInfo;
    }


}