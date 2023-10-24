package com.rent.config.interceptor;

import com.rent.common.cache.backstage.BackstageUserCache;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.exception.HzsxBizException;
import com.rent.util.LoginUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhaowenchao
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class AuthInterceptor implements HandlerInterceptor {

    public static final String LOGIN_USER_KEY = "login:token:";

    private static final String AUTH_FAILED_STR = "{\"errorCode\":\"-1\",\"errorMsg\":\"AUTH_FAILED\"}";
    private static final String LOGIN_INVALID_STR = "{\"errorCode\":\"LOGIN_INVALID\",\"errorMsg\":\"登录失效，请重新登陆\"}";

    // 需要登陆但不需要鉴权的接口
    private static final Set<String> loginNoAuthUriSet = new HashSet<String>() {{
        add("user/restPasswordOnLogined");
        add("user/logout");
        add("business/order/checkOrderIsAuth");
        add("notice/selectNotice");
        add("buyoutCalculator/cal");
        add("noticeCenter/queryOpeNoticeDetailList");
        add("category/getAntChainCategory");
    }};

    // 不需要登陆的接口
    private static final Set<String> noNeedLoginUriSet = new HashSet<String>() {{
        add("user/validateCode");
        add("user/login");
        add("user/sendValidateCode");
        add("user/restPassword");
        add("user/register");
        add("swagger");
        add("error");
        add("weixin/saveWeixinUserInfo");
        add("common/appinfo");
    }};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String uri = request.getRequestURI();

            // 非后台接口不鉴权
            if (!uri.contains("/zyj-backstage-web")) {
                return true;
            }

            uri = uri.substring(24);
            // 校验不需要登陆的接口
            if (noNeedLoginUriSet.contains(uri)) {
                return true;
            }
            String token = request.getHeader("token");
            LoginUserBo loginUserBo = BackstageUserCache.getLoginUserBo(token);
            if (loginUserBo == null) {
                log.error("登录失效，请重新登陆");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(LOGIN_INVALID_STR);
                return false;
            }
            //token失效时间延长
            BackstageUserCache.expireToken(token);
            //保存登录用户信息到线程
            LoginUserUtil.setLoginUser(loginUserBo);

            // 校验需要登陆但不对接口鉴权的
            if (loginNoAuthUriSet.contains(uri)) {
                return true;
            }

            String userId = loginUserBo.getId().toString();
            //校验权限
            if (!BackstageUserCache.checkUserHasAuth(userId, uri)) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(AUTH_FAILED_STR);
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("【鉴权失败】AuthInterceptor出现异常", e);
            throw new HzsxBizException("001", "鉴权出现异常");
        }
    }

}
