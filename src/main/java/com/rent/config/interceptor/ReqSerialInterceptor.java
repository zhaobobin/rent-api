package com.rent.config.interceptor;

import com.rent.util.AppParamUtil;
import com.rent.util.DateUtil;
import com.rent.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zhaowenchao
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class ReqSerialInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        AppParamUtil.setSerialNo(DateUtil.date2String(new Date(),DateUtil.DATETIME_FORMAT_8)+ RandomUtil.randomString(6));
        return true;

    }
}
