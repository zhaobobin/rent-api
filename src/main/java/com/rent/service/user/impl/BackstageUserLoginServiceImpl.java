package com.rent.service.user.impl;

import com.rent.common.cache.backstage.BackstageUserCache;
import com.rent.common.constant.BackstageUserConstant;
import com.rent.common.dto.backstage.RegisterReqDto;
import com.rent.common.dto.backstage.SendValidateCodeRespDto;
import com.rent.common.dto.backstage.request.LoginReqDto;
import com.rent.common.dto.backstage.resp.LoginRespDto;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.components.dto.SendMsgDto;
import com.rent.common.dto.product.ChannelSplitBillDto;
import com.rent.common.dto.user.BackstageFunctionDto;
import com.rent.common.dto.user.BackstageUserDto;
import com.rent.common.dto.user.BackstageUserReqDto;
import com.rent.common.dto.user.ChannelRegisterReqDto;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.common.enums.user.EnumBackstageUserStatus;
import com.rent.exception.HzsxBizException;
import com.rent.service.components.SendSmsService;
import com.rent.service.product.ChannelSplitBillService;
import com.rent.service.user.AuditUserService;
import com.rent.service.user.BackstageUserFunctionService;
import com.rent.service.user.BackstageUserLoginService;
import com.rent.service.user.BackstageUserService;
import com.rent.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author udo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BackstageUserLoginServiceImpl implements BackstageUserLoginService {

    private final BackstageUserService backstageUserService;
    private final BackstageUserFunctionService backstageUserFunctionService;
    private final SendSmsService sendSmsService;
    private final ChannelSplitBillService channelSplitBillService;
    private final AuditUserService auditUserService;

    @Override
    public void logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        BackstageUserCache.delToken(token);
    }

    @Override
    public LoginRespDto login(LoginReqDto loginDto) {
        BackstageUserReqDto reqDto = new BackstageUserReqDto();
        reqDto.setMobile(loginDto.getMobile());
        reqDto.setType(loginDto.getUserType());
        BackstageUserDto backstageUserDto = backstageUserService.queryBackstageUserDetail(reqDto);
        if (backstageUserDto == null) {
            throw new HzsxBizException("-1", "用户不存在");
        }
        if (EnumBackstageUserStatus.INVALID.equals(backstageUserDto.getStatus())) {
            throw new HzsxBizException("-1", "用户已被冻结");
        }
        String encryptPassword = MD5.getMD5(loginDto.getPassword() + backstageUserDto.getSalt());
        if (!backstageUserDto.getPassword().equalsIgnoreCase(encryptPassword)) {
            throw new HzsxBizException("-1", "密码错误");
        }
        // 单点登陆
        String oldToken = BackstageUserCache.loginedUserToken(backstageUserDto.getId());
        if (StringUtils.isNotEmpty(oldToken)) {
            BackstageUserCache.delToken(oldToken);
        }

        Boolean isAuditUser = auditUserService.isAuditUser(backstageUserDto.getId());
        //1.生成token
        LoginRespDto loginRespDto = new LoginRespDto();
        String token = RandomUtil.randomString(32);
        loginRespDto.setToken(token);
        //2 获取用户所有的权限列表
        List<BackstageFunctionDto> backstageUserFunctionDtoList = backstageUserFunctionService.getBackstageUserFunction(backstageUserDto.getId());
        //2.1 筛选用户拥有的功能code，并放到缓存当中去
        List<String> functionCodes = backstageUserFunctionDtoList.stream()
                .filter(backstageFunctionDto -> backstageFunctionDto.getType().equals("FUNCTION"))
                .map(BackstageFunctionDto::getCode).collect(Collectors.toList());
        //2.2 筛选用户拥有的菜单栏列表，并返回给前端
        List<String> menus = backstageUserFunctionDtoList.stream()
                .filter(backstageFunctionDto -> backstageFunctionDto.getType().equals("MENU"))
                .map(BackstageFunctionDto::getName).collect(Collectors.toList());
        loginRespDto.setMenus(menus);
        loginRespDto.setShopId(backstageUserDto.getShopId());
        BackstageUserCache.setToken(token, backstageUserDto, isAuditUser, functionCodes);
        return loginRespDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long register(RegisterReqDto registerReqDto) {
        BackstageUserReqDto reqDto = new BackstageUserReqDto();
        reqDto.setMobile(registerReqDto.getMobile());
        reqDto.setType(EnumBackstageUserPlatform.SHOP);
        BackstageUserDto backstageUserDto = backstageUserService.queryBackstageUserDetail(reqDto);
        if (backstageUserDto != null) {
            throw new HzsxBizException("-1", "该手机号码已经注册");
        }
        BackstageUserDto request = new BackstageUserDto();
        request.setMobile(registerReqDto.getMobile());
        request.setPassword(registerReqDto.getPassword());
        request.setName(registerReqDto.getMobile());
        request.setType(EnumBackstageUserPlatform.SHOP);
        request.setShopId(StringUtil.generateUid());
        request.setCreateUserName(BackstageUserConstant.BACKSTAGE_USER_CREATE_REGISTER);
        request.setDepartmentId(BackstageUserConstant.BACKSTAGE_USER_SHOP_REGISTER_DEPARTMENT_ID);
        request.setSalt(RandomUtil.randomString(4));
        request.setPassword(MD5.getMD5(request.getPassword() + request.getSalt()));
        Date now = new Date();
        request.setCreateTime(now);
        request.setUpdateTime(now);
        request.setStatus(EnumBackstageUserStatus.VALID);
        return backstageUserService.addBackstageUser(request);
    }

    @Override
    public SendValidateCodeRespDto sendValidateCode(String mobile) {
        BackstageUserReqDto reqDto = new BackstageUserReqDto();
        reqDto.setMobile(mobile);
        reqDto.setType(EnumBackstageUserPlatform.SHOP);
        BackstageUserDto backstageUserDto = backstageUserService.queryBackstageUserDetail(reqDto);
        if (backstageUserDto == null) {
            throw new HzsxBizException("-1", "用户不存在");
        }
        Long dateTime = System.currentTimeMillis() / 1000;
        String randomCode = RandomUtil.randomNumbers(4);
        String encryptSmsCode = ValidateCodeUtil.encryptSmsCode(randomCode + mobile + dateTime);

        Map<String, Object> data = new HashMap<>();
        data.put("#code#", randomCode);
        SendMsgDto sendMsgDto = new SendMsgDto();
        sendMsgDto.setTelephone(mobile);
        sendMsgDto.setCode(randomCode);
        sendSmsService.businessUpdatePassword(sendMsgDto);

        SendValidateCodeRespDto resp = new SendValidateCodeRespDto();
        resp.setCodeKey(encryptSmsCode);
        resp.setCodeTime(dateTime);
        return resp;
    }

    @Override
    public Boolean restPassword(String mobile, String codeKey, String code, Long codeTime, String newPassword, EnumBackstageUserPlatform userType) {
        String msg = ValidateCodeUtil.verifySmsCode(mobile, codeKey, code, codeTime, true);
        if (StringUtils.isNotEmpty(msg)) {
            throw new HzsxBizException("-1", msg);
        }

        BackstageUserReqDto reqDto = new BackstageUserReqDto();
        reqDto.setMobile(mobile);
        reqDto.setType(userType);
        BackstageUserDto backstageUserDto = backstageUserService.queryBackstageUserDetail(reqDto);
        if (backstageUserDto == null) {
            throw new HzsxBizException("-1", "该用户不存在");
        }

        String salt = RandomUtil.randomString(4);
        backstageUserDto.setSalt(salt);
        backstageUserDto.setPassword(MD5.getMD5(newPassword + salt));
        return backstageUserService.modifyBackstageUser(backstageUserDto);
    }

    @Override
    public Boolean restPasswordOnLogin(String mobile, String oldPassword, String newPassword, EnumBackstageUserPlatform userType) {
        BackstageUserReqDto reqDto = new BackstageUserReqDto();
        reqDto.setMobile(mobile);
        reqDto.setType(userType);
        LoginUserBo loginUserBo = LoginUserUtil.getLoginUser();

        if (!loginUserBo.getMobile().equals(mobile)) {
            throw new HzsxBizException("-1", "不允许修改他人密码");
        }

        BackstageUserDto backstageUserDto = backstageUserService.queryBackstageUserDetail(reqDto);
        if (backstageUserDto == null) {
            throw new HzsxBizException("-1", "该用户不存在");
        }

//        String encryptPassword = MD5.getMD5(oldPassword + backstageUserDto.getSalt());
//        if (!backstageUserDto.getPassword().equalsIgnoreCase(encryptPassword)) {
//            throw new HzsxBizException("-1", "原密码错误");
//        }

        String salt = RandomUtil.randomString(4);
        backstageUserDto.setSalt(salt);
        backstageUserDto.setPassword(MD5.getMD5(newPassword + salt));
        return backstageUserService.modifyBackstageUser(backstageUserDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long channelRegister(ChannelRegisterReqDto registerReqDto) {
        BackstageUserReqDto reqDto = new BackstageUserReqDto();
        reqDto.setMobile(registerReqDto.getMobile());
        reqDto.setType(EnumBackstageUserPlatform.CHANNEL);
        BackstageUserDto backstageUserDto = backstageUserService.queryBackstageUserDetail(reqDto);
        if (backstageUserDto != null) {
            throw new HzsxBizException("-1", "该手机号码已经注册");
        }
        BackstageUserDto request = new BackstageUserDto();
        request.setMobile(registerReqDto.getMobile());
        request.setPassword("888888");
        request.setName(registerReqDto.getName());
        request.setType(EnumBackstageUserPlatform.CHANNEL);
        request.setCreateUserName(BackstageUserConstant.BACKSTAGE_USER_CREATE_REGISTER);
        //需要改为营销部门的id
        request.setDepartmentId(BackstageUserConstant.CHANNEL_USER_DEPARTMENT_ID);
        request.setSalt(RandomUtil.randomString(4));
        request.setPassword(MD5.getMD5(request.getPassword() + request.getSalt()));
        Date now = new Date();
        request.setCreateTime(now);
        request.setUpdateTime(now);
        request.setStatus(EnumBackstageUserStatus.VALID);

        ChannelSplitBillDto channelSplitBillDto = new ChannelSplitBillDto();
        channelSplitBillDto.setName(registerReqDto.getName());
        channelSplitBillDto.setAccount(registerReqDto.getMobile());
        channelSplitBillDto.setScale(registerReqDto.getScale());
        channelSplitBillDto.setIdentity(registerReqDto.getIdentity());
        channelSplitBillDto.setAliName(registerReqDto.getAliName());
        //加ch渠道
        String uid = "CH" + StringUtil.generateUid();
        channelSplitBillDto.setUid(uid);
        channelSplitBillDto.setAddUser(LoginUserUtil.getLoginUser().getName());
        request.setShopId(uid);
        channelSplitBillService.add(channelSplitBillDto);
        return backstageUserService.addBackstageUser(request);
    }
}
