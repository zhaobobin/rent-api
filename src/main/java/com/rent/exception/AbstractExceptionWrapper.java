package com.rent.exception;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;

/**
 *
 * @Author xiaoyao
 * @Date 10:58 2020-4-20
 * @Param
 * @return
 **/
@Getter
public abstract class AbstractExceptionWrapper extends RuntimeException {

    private static final long serialVersionUID = 8924966337248068445L;

    /**
     * 异常code
     */
    protected String errorCode;

    /**
     * 异常描述
     */
    protected String errorMessage;

    /**
     * 抛异常的类
     */
    protected Class<?> clazz;

    public AbstractExceptionWrapper() {
    }

    public AbstractExceptionWrapper(String message) {
        super(message);
        this.errorMessage = message;
    }

    public AbstractExceptionWrapper(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public AbstractExceptionWrapper(Throwable cause) {
        super(cause);
    }

    public AbstractExceptionWrapper(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMessage = message;
    }

    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values 参数值
     * @return 格式化后的文本
     */
    protected static String format(String template, Object... values) {
        if (values == null || 0 == Array.getLength(values)) {
            return template;
        }
        // 由于String.format针对于%有特殊处理，所以需要转义下
        String replacedTemplate = template.replace("%", "%%");
        return StringUtils.isBlank(template) ? StringUtils.EMPTY : String.format(replacedTemplate.replace("{}", "%s"),
            values);
    }

}
