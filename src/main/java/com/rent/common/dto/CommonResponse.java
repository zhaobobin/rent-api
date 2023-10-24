package com.rent.common.dto;

import com.rent.common.enums.EnumResponseType;
import com.rent.common.enums.EnumRpcResult;
import com.rent.util.AppParamUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 公共响应报文
 *
 * @author xiaoyao
 */
@Data
@Builder
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = -3324425188674908323L;

    /** rpc通信结果 */
    private EnumRpcResult rpcResult;

    /** 通讯异常code */
    private Integer httpStatus;

    /** rpc通信成功后，如果业务抛出异常，则该枚举进行区分是何种业务异常 */
    private EnumResponseType responseType;

    /** 请求失败时的错误码 */
    private String errorCode;

    /** 请求失败时的错误信息 */
    private String errorMessage;

    /** 业务处理正常的返回 */
    private T data;

    /** 父幂等流水号 */
    private String parentIdemSerialId;

    private CommonResponse(final EnumRpcResult rpcResult, final Integer httpStatus, final EnumResponseType responseType,
                           final String errorCode, final String errorMessage, final T data, final String parentIdemSerialId) {
        this.rpcResult = rpcResult == null ? EnumRpcResult.SUCCESS : rpcResult;
        this.httpStatus = httpStatus == null ? 200 : httpStatus;
        this.responseType = responseType == null ? EnumResponseType.SUCCESS : responseType;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
        this.parentIdemSerialId = AppParamUtil.getSerialNo();
    }

    public CommonResponse() {
    }

    /**
     * rpc是否成功
     *
     * @return true-成功，false-失败
     */
    public boolean isRpcSuccess() {
        return this.rpcResult == EnumRpcResult.SUCCESS;
    }

    /**
     * rpc是否失败
     *
     * @return true-失败，false-没有失败
     */
    public boolean isRpcFail() {
        return !isRpcSuccess();
    }

    /**
     * rpc是否超时异常
     *
     * @return true-超时，false-不是超时异常（可能是其他异常）
     */
    public boolean isRpcTimeout() {
        return this.rpcResult == EnumRpcResult.TIMEOUT_ERR;
    }

    /**
     * 业务交易是否成功
     *
     * @return true-成功，false-失败
     */
    public boolean isBusinessSuccess() {
        return this.responseType == EnumResponseType.SUCCESS;
    }

    /**
     * 业务交易是否失败
     *
     * @return true-失败，false-没有失败
     */
    public boolean isBusinessFail() {
        return !isBusinessSuccess();
    }

    /**
     * rpc和业务交易是否都成功
     *
     * @return true-成功，false-失败
     */
    public boolean isFinalSuccess() {
        return isRpcSuccess() && isBusinessSuccess();
    }

    /**
     * rpc和业务交易是否有失败
     *
     * @return true-有失败，false-全都成功
     */
    public boolean isFinalFail() {
        return !isFinalSuccess();
    }



}
