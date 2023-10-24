package com.rent.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Comparator;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired(required = false)
    private List<HandlerInterceptor> handlerInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        handlerInterceptors.stream()
            .sorted(Comparator.comparingInt(this::getOrder))
            .forEach(registry::addInterceptor);
    }

    /**
     * 获取拦截器对应的优先级，如果没有带{@link Order}注解，则默认优先级最低
     * @param interceptor 具体拦截器
     * @return 优先级
     */
    private Integer getOrder(HandlerInterceptor interceptor) {
        Order annotation = interceptor.getClass()
            .getAnnotation(Order.class);
        if (annotation != null) {
            return annotation.value();
        }
        return Ordered.LOWEST_PRECEDENCE;
    }

}