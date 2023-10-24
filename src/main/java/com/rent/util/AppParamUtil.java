package com.rent.util;


import com.rent.common.enums.EnumContext;

/**
 * @Author xiaoyao
 * @Date 21:49 2020-4-19
 * @Param
 * @return
 **/
public final class AppParamUtil {


    /**
     * 获得流水号
     * @return 流水号
     */
    public static String getSerialNo() {
        return (String) ThreadContextStoreUtil.getInstance().get(EnumContext.SERIAL_NO.getCode());
    }

    /**
     * 获得流水号
     * @return 流水号
     */
    public static void setSerialNo(String serialNo) {
        ThreadContextStoreUtil.getInstance().set(EnumContext.SERIAL_NO.getCode(),serialNo);
    }

    public static String getChannelId() {
        return "000";
    }
}
