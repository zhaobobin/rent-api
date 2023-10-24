package com.rent.common.cache.compoments;

import com.rent.util.RedisUtil;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhaowenchao
 */
public class InsuranceBalanceCache {

    private static final String INSURE_BALANCE_KEY = "INSURE_BALANCE";

    public static void updateBalance(String balance){
        if(StringUtils.isNotEmpty(balance)){
            RedisUtil.set(INSURE_BALANCE_KEY,balance);
        }
    }


    public static void addBalance(String add){
        String old  = getBalance();
        Integer balance = Integer.parseInt(add)+Integer.parseInt(old);
        updateBalance(balance.toString());
    }

    public static String getBalance(){
        Object balance =  RedisUtil.get(INSURE_BALANCE_KEY);
        if(balance!=null){
            return (String) balance;
        }else {
            return "0";
        }
    }

}
