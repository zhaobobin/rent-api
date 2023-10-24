package com.rent.config.aspect;

import com.alibaba.fastjson.JSON;
import com.rent.config.annotation.ExcludeWebLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Component
@Aspect
@Slf4j
public class WebLogAspect {



    @Pointcut("execution(public * com.rent.controller.*.*.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().toShortString();


        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        ExcludeWebLog excludeWebLog = method.getAnnotation(ExcludeWebLog.class);
        if(excludeWebLog!=null){
            return point.proceed();
        }

        List printArgs = new ArrayList();
        for (Object arg : point.getArgs()) {
            if(arg instanceof MultipartFile || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse){
                continue;
            }
            printArgs.add(arg);
        }
        log.info("请求开始：{},参数： request={}", methodName, JSON.toJSONString(printArgs));
        Object result;
        try {
            result = point.proceed();
        } catch (Exception e) {
            log.error("请求异常：{},异常：{}", methodName, e);
            throw e;
        }
        log.info("请求结束：{},结果： resp={}", methodName, JSON.toJSONString(result));
        return result;

    }
}
