package com.rent.service.user.impl;

import com.google.common.collect.ImmutableSet;
import com.rent.common.cache.user.UserCertificationCache;
import com.rent.common.converter.user.UserCertificationConverter;
import com.rent.common.dto.components.response.IdCardOcrResponse;
import com.rent.common.dto.user.UidAndPhone;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.common.EnumRpcError;
import com.rent.common.util.CheckDataUtils;
import com.rent.common.util.DesensitizationUtil;
import com.rent.common.util.StringUtil;
import com.rent.dao.user.UserCertificationDao;
import com.rent.dao.user.UserDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.user.User;
import com.rent.model.user.UserCertification;
import com.rent.service.components.SxService;
import com.rent.service.user.UserCertificationService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户认证表Service
 *
 * @author zhao
 * @Date 2020-06-18 15:06
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserCertificationServiceImpl implements UserCertificationService {

    private final UserCertificationDao userCertificationDao;
    private final UserDao userDao;
    private final SxService sxService;

    private static final String LONG_ID_CARD = "长期";
    private static final Set<String> PHOTO_FILE_EXTENSION_SET = ImmutableSet.of("JPG", "JPEG", "PNG");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String userCertificationAuth(UserCertificationDto request) {
        String idCard = request.getIdCard();
        String uid = request.getUid();
        String userName = request.getUserName();
        String errorMsg = StringUtil.IDCardValidate(idCard);
        String limitDate = request.getLimitDate();
        String startDate = request.getStartDate();
        String address = request.getAddress();
        String sex = request.getSex();
        String nation = request.getNation();
        String issueOrg = request.getIssueOrg();
        //是否上传身份证照片
        Boolean idCardStatus = StringUtils.isNotEmpty(request.getIdCardFrontUrl()) && StringUtils.isNotEmpty(
            request.getIdCardBackUrl()) && StringUtils.isNotEmpty(limitDate);
        if (StringUtil.isNotEmpty(errorMsg)) {
            throw new HzsxBizException(EnumRpcError.DATA_ERROR.getCode(), errorMsg);
        }
        //允许身份验证后期补录身份证件图片信息
        if (idCardStatus) {
            //如果是长期，将到期日期设置为2099年
            if (LONG_ID_CARD.equals(limitDate)) {
                limitDate = "20991231";
            }
            if (DateUtil.getBetweenDays(DateUtil.getNowDate(), DateUtil.string2Date(limitDate)) <= 0) {
                throw new HzsxBizException(EnumRpcError.DATA_ERROR.getCode(), "身份证已过期");
            }
        }
        UserCertification userCertification = userCertificationDao.getByUid(uid);
        //实名认证&身份证图片已上传
        if (userCertification != null && StringUtil.isNotEmpty(userCertification.getIdCardFrontUrl())
            && StringUtil.isNotEmpty(userCertification.getIdCardBackUrl()) && null != userCertification.getLimitDate()
            && Long.parseLong(DateUtil.getDate(userCertification.getLimitDate(), DateUtil.DATE_FORMAT_2))
            >= Long.parseLong(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_2))) {
            UserCertificationCache.setUserCertificationCache(
                UserCertificationConverter.model2Dto(userCertificationDao.getByUid(uid)));
            throw new HzsxBizException(EnumRpcError.DATA_ERROR.getCode(), "已经实名认证");
        }

        if (!idCardStatus && null != userCertification && StringUtils.isNotEmpty(userCertification.getIdCard())
            && StringUtils.isNotEmpty(userCertification.getTelephone()) && StringUtils.isNotEmpty(
            userCertification.getUserName())) {
            throw new HzsxBizException(EnumRpcError.DATA_ERROR.getCode(), "您已录入身份证号码信息，请补录对应的身份证图片信息");
        }

        //判断身份证号码和姓名是否匹配
        if (!sxService.cert(userName, idCard)) {
            throw new HzsxBizException(EnumRpcError.DATA_ERROR.getCode(), "姓名和身份证号码不匹配");
        }
        //如果结果不为空，更新信息
        if (null != userCertification) {
            //不为空,判断数据库中填写的身份证号和后期补录的身份证号是否一致
            if (idCardStatus) {
                if (!idCard.equalsIgnoreCase(userCertification.getIdCard())) {
                    throw new HzsxBizException(EnumRpcError.DATA_ERROR.getCode(), "请上传本人身份证");
                }
                //更新用户信息
                userCertification.setIdCardFrontUrl(request.getIdCardFrontUrl());
                userCertification.setIdCardBackUrl(request.getIdCardBackUrl());
                userCertification.setLimitDate(DateUtil.string2Date(limitDate));
                userCertification.setStartDate(DateUtil.string2Date(startDate));
                userCertification.setAddress(address);
                userCertification.setSex(sex);
                userCertification.setNation(nation);
                userCertification.setIssueOrg(issueOrg);
            }
            userCertification.setUpdateTime(new Date());
            userCertificationDao.updateById(userCertification);
            UserCertificationCache.setUserCertificationCache(
                UserCertificationConverter.model2Dto(userCertificationDao.getByUid(uid)));
            //更新用户表状态
            userDao.updateUserStatusByUid(request.getUid(), idCardStatus);
            return uid;
        }
        User user = userDao.getUserByUid(uid);
        String telephone = user.getTelephone() == null ? request.getTelephone() : user.getTelephone();
        //保存用户认证信息
        userCertificationDao.initRecord(uid, idCard, userName, telephone, request.getIdCardFrontUrl(),
                request.getIdCardBackUrl(), DateUtil.string2Date(limitDate),DateUtil.string2Date(startDate),address,sex,issueOrg,nation);
        //更新用户信息
        user.setIdCardPhotoStatus(idCardStatus);
        user.setTelephone(telephone);
        user.setUpdateTime(new Date());
        user.setIsAuth(Boolean.TRUE);
        userDao.updateById(user);
        UserCertificationCache.setUserCertificationCache(UserCertificationConverter.model2Dto(userCertificationDao.getByUid(uid)));
        return uid;
    }

    @Override
    public UserCertificationDto getByUid(String uid) {
        UserCertification userCertification = userCertificationDao.getByUid(uid);
        UserCertificationDto dto = UserCertificationConverter.model2Dto(userCertification);
        UserCertificationCache.setUserCertificationCache(dto);
        return dto;
    }

    @Override
    public UserCertificationDto getDesensitizationByUid(String uid) {

        UserCertificationDto dto = UserCertificationCache.getUserCertificationCache(uid);
        if (dto == null) {
            UserCertification userCertification = userCertificationDao.getByUid(uid);
            dto = UserCertificationConverter.model2Dto(userCertification);
            UserCertificationCache.setUserCertificationCache(dto);
        }
        if (null != dto) {
            Date limitDate = null;
            if (StringUtil.isNotEmpty(dto.getLimitDate())) {
                limitDate = DateUtil.string2Date(dto.getLimitDate(), DateUtil.DATE_FORMAT_2);
            }
            dto.setIdCard(DesensitizationUtil.idEncrypt(dto.getIdCard()));
            dto.setTelephone(DesensitizationUtil.mobileEncrypt(dto.getTelephone()));
            if (null != limitDate && Long.parseLong(DateUtil.getDate(limitDate, DateUtil.DATE_FORMAT_2))
                < Long.parseLong(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_2))) {
                dto.setIdCardBackUrl(null);
                dto.setIdCardFrontUrl(null);
                dto.setLimitDate(null);
            }
        }
        return dto;
    }

    @Override
    public Map<String, UserCertificationDto> queryUserCertificationList(List<String> uidList) {
        return userCertificationDao.queryUserCertificationList(uidList);
    }

    @Override
    public List<UidAndPhone> getUidAndPhoneSet(List<String> phones) {
        return userCertificationDao.getUidAndPhoneSet(phones);
    }

    @Override
    public List<String> getUidByIdCard(String idCard) {
        return userCertificationDao.getUidByIdCard(idCard);
    }

    @Override
    public IdCardOcrResponse certificationOcr(String frontUrl, String backUrl, String uid) {
        checkFileAndUid(frontUrl, backUrl, uid);
        try {
            IdCardOcrResponse idCardOcrResponse = sxService.idCardOcr(frontUrl,backUrl);
            //查询用户id，是否实名认证，已认证校验上传证件身份证号是否为原身份证号
            UserCertification userCertification = userCertificationDao.getByUid(uid);
            if (null != userCertification && !userCertification.getIdCard().equalsIgnoreCase(idCardOcrResponse.getIdCardNo())) {
                throw new HzsxBizException("999999", "请使用本人身份证进行认证");
            }
            return idCardOcrResponse;
        }catch (Exception e){
            log.error("OCR识别异常",e);
            return null;
        }
    }

    private void checkFileAndUid(String frontFileUrl, String backFileUrl, String uid) {
        CheckDataUtils.judge(Objects.isNull(frontFileUrl) || Objects.isNull(backFileUrl), "照片不能为空");
        CheckDataUtils.judge(StringUtils.isBlank(uid), "uid不能为空");
        String[] front = frontFileUrl.split("\\.");
        String frontType = front[front.length - 1];
        CheckDataUtils.judge(!PHOTO_FILE_EXTENSION_SET.contains(frontType.toUpperCase()), "只支持 jpg, png jpeg格式的照片");
        String[] back = backFileUrl.split("\\.");
        String backType = back[back.length - 1];
        CheckDataUtils.judge(!PHOTO_FILE_EXTENSION_SET.contains(backType.toUpperCase()), "只支持 jpg, png jpeg格式的照片");
    }

}