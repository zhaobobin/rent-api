package com.rent.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaowenchao
 */
public class SplitBillRecordConstant {

    private static Map<String,String> statusStrMap = new HashMap<>();
    static {
        statusStrMap.put(SplitBillRecordConstant.STATUS_UNPAID,"待支付");
        statusStrMap.put(SplitBillRecordConstant.STATUS_DEALING,"支付中");
        statusStrMap.put(SplitBillRecordConstant.STATUS_PAID,"已支付");
        statusStrMap.put(SplitBillRecordConstant.STATUS_FAIL,"支付失败");
        statusStrMap.put(SplitBillRecordConstant.STATUS_SETTLED,"已结算");
        statusStrMap.put(SplitBillRecordConstant.STATUS_WAITING_SETTLEMENT,"未结算");
    }

    /**
     * 店铺分账记录-状态-已确认待支付
     */
    public static final String STATUS_UNPAID="UNPAID";

    /**
     * 店铺分账记录-状态-支付中
     */
    public static final String STATUS_DEALING="DEALING";


    /**
     * 店铺分账记录-状态-已支付
     */
    public static final String STATUS_PAID="PAID";

    /**
     * 店铺分账记录-状态-支付失败
     */
    public static final String STATUS_FAIL="FAIL";

    /**
     * 店铺分账记录-状态-已结算
     */
    public static final String STATUS_SETTLED="SETTLED";

    /**
     * 店铺分账记录-状态-待结算
     */
    public static final String STATUS_WAITING_SETTLEMENT="WAITING_SETTLEMENT";


    public static String getStatusStr(String status){
        return statusStrMap.get(status);
    }

}
