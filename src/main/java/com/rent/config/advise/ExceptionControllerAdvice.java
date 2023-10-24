package com.rent.config.advise;

import com.rent.common.dto.CommonResponse;
import com.rent.common.enums.EnumResponseType;
import com.rent.common.enums.common.EnumRpcError;
import com.rent.exception.HzsxBizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Description:
 * <p>
 * Created on 2020年6月6日
 *
 * @author xiaoyao
 * @version 1.0
 * @since v1.0
 */
@ControllerAdvice(basePackages = "com.rent")
@Slf4j
public class ExceptionControllerAdvice {


    /**
     * 针对{@link HzsxBizException}类型的异常，该异常一般为业务抛出的异常，主要用于向前台传输异常信息
     *
     * @param ex 抛出的异常
     * @author jinyanan
     */
    @ExceptionHandler(HzsxBizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResponse handlerError(HzsxBizException ex) {
        log.error(ex.getErrorMessage(), ex);
        return CommonResponse.builder()
            .responseType(EnumResponseType.FLOW_ERR)
            .errorCode(ex.getErrorCode())
            .errorMessage(ex.getErrorMessage())
            .build();

    }

    /**
     * 针对{@link MethodArgumentNotValidException}类型的异常，参数校验异常
     *
     * @param ex 抛出的异常
     * @author jinyanan
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResponse handlerError(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder sb =  new StringBuilder("[");
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getDefaultMessage()).append(",");
        }
        sb.deleteCharAt(sb.length()-1).append("]");
        return CommonResponse.builder()
                .responseType(EnumResponseType.FLOW_ERR)
                .errorCode(EnumRpcError.VALIDATOR_ERROR.getCode())
                .errorMessage(sb.toString())
                .build();

    }


    /**
     * 除了基础类型之外的异常，该异常一般为内部实现出错时会抛出的异常，例如NullPointerException等等，非业务逻辑异常
     *
     * @param ex 抛出的异常
     * @author
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResponse handlerError(Exception ex) {
        if (StringUtils.isBlank(ex.getMessage())) {
            log.error("系统抛出非业务异常，message未设置", ex);
        } else {
            log.error("系统抛出非业务异常，message: " + ex.getMessage(), ex);
        }
        return CommonResponse.builder()
            .responseType(EnumResponseType.SYS_ERR)
            .errorCode("999999")
            // 如果Exception#getMessage为空，则使用自己定义的message
            .errorMessage(
                StringUtils.isBlank(ex.getMessage()) ? "系统异常" : ex.getMessage())
            .build();
    }

}
