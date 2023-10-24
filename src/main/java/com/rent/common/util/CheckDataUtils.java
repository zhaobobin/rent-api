package com.rent.common.util;

import com.rent.common.enums.common.EnumRpcError;
import com.rent.exception.HzsxBizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * 校验业务数据
 * 并返回相应的异常类 供全局异常处理
 *
 * @author boan
 */
@Slf4j
public class CheckDataUtils {

    /**
     * 判断参数是否为空，为空则抛出参数异常
     *
     * @param args
     */
    @SafeVarargs
    public static <T> void judgeNull(T... args){
        if (args == null || args.length == 0) {
            throw new HzsxBizException(EnumRpcError.VALIDATOR_ERROR.getCode(),
                    EnumRpcError.VALIDATOR_ERROR.getDescription());
        }

        for (T item : args) {
            if (item == null) {
                throw new HzsxBizException(EnumRpcError.VALIDATOR_ERROR.getCode(),
                        EnumRpcError.VALIDATOR_ERROR.getDescription());
            }

            if (item instanceof String) {
                if (StringUtils.isBlank((String) item)) {
                    throw new HzsxBizException(EnumRpcError.VALIDATOR_ERROR.getCode(),
                            EnumRpcError.VALIDATOR_ERROR.getDescription());
                }
            }
        }
    }


    /**
     * 请求值重复异常
     *
     * @param
     */
    public static <T> void dataError(String args){
        if (args != null ) {
            throw new HzsxBizException(EnumRpcError.DATA_ERROR.getCode(),
                    args);
        }
    }


    /**
     * 如果条件成立,抛出一个Service异常
     * @param check
     * @param errorMsg 异常信息
     */
    public static void judge(boolean check, String errorMsg) {
        if (check) {
            throw new HzsxBizException(EnumRpcError.DATA_ERROR.getCode(),errorMsg);
        }
    }






}
