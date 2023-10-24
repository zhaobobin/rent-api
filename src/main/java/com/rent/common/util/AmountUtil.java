package com.rent.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @desc 金额计算工具
 * @author: xiaoyao
 * @since 1.0
 */
public class AmountUtil {

    public static final String REPAY_PLAN_TERM_JOINT_SYMBOL = ",";

    /**
     * 将期次拼接字符串切割还原
     *
     * @param termJoints
     * @return java.util.List<java.lang.Integer>
     */
    public static LinkedList<Integer> splitRepayPlanTermList(String termJoints) {
        return StringUtils.isNotBlank(termJoints) ? Arrays.stream(
            StringUtils.split(termJoints, REPAY_PLAN_TERM_JOINT_SYMBOL))
            .map(Integer::valueOf)
            .sorted()
            .collect(Collectors.toCollection(Lists::newLinkedList)) : Lists.newLinkedList();
    }

    /**
     * 将期次拼接字符串
     *
     * @param repayPlanTerms
     * @return String
     */
    public static String jointRepayPlanTermList(List<Integer> repayPlanTerms) {
        if (CollectionUtil.isEmpty(repayPlanTerms)) {
            return null;
        }
        return repayPlanTerms.stream()
            .sorted()
            .map(String::valueOf)
            .collect(Collectors.joining(AmountUtil.REPAY_PLAN_TERM_JOINT_SYMBOL));
    }

    /**
     * 计算免赔额
     *
     * @param amt
     * @param excessRate
     * @return java.math.BigDecimal
     */
    public static BigDecimal calDeductibleAmount(BigDecimal amt, BigDecimal excessRate) {
        return amt.multiply(BigDecimal.ONE.subtract(excessRate))
            .setScale(4, BigDecimal.ROUND_HALF_DOWN);
    }

    /**
     * 金额比较
     *
     * @param b1s
     * @param b2s
     * @return boolean
     */
    public static boolean isEqual(Supplier<BigDecimal> b1s, Supplier<BigDecimal> b2s) {
        BigDecimal b1, b2;
        return (b1 = b1s.get()) != null && (b2 = b2s.get()) != null && b1.compareTo(b2) == 0;
    }

    /**
     * 金额计算乘法
     *
     * @param decimals
     * @return
     */
    public static BigDecimal safeMultiply(BigDecimal... decimals) {
        return decimals == null ? BigDecimal.ZERO : Arrays.stream(decimals)
            .map(b -> ObjectUtils.defaultIfNull(b, BigDecimal.ZERO))
            .reduce(BigDecimal::multiply).orElse(BigDecimal.ZERO);
    }

    /**
     * 金额计算加法
     *
     * @param decimals
     * @return
     */
    public static BigDecimal safeAdd(BigDecimal... decimals) {
        return decimals == null ? BigDecimal.ZERO : Arrays.stream(decimals)
            .map(b -> ObjectUtils.defaultIfNull(b, BigDecimal.ZERO))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 金额计算减法
     *
     * @param negativeToZero 为true则：计算结果是负数时返回BigDecimal.ZERO
     * @param decimals
     * @return java.math.BigDecimal
     */
    public static BigDecimal safeSubtract(boolean negativeToZero, BigDecimal... decimals) {
        if (decimals == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = Arrays.stream(decimals)
            .map(b -> ObjectUtils.defaultIfNull(b, BigDecimal.ZERO))
            .reduce(BigDecimal::subtract)
            .get();
        if (negativeToZero && result.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return result;
    }

    /**
     * 填充追回各项金额，并返回剩余金额
     *
     * @param totalAmt
     * @param decreasingItems
     * @return java.math.BigDecimal[]
     */
    public static BigDecimal[] computeGradualDecrease(BigDecimal totalAmt, BigDecimal... decreasingItems) {
        if (decreasingItems == null || totalAmt == null) {
            return null;
        }
        BigDecimal current = checkNonNegative(totalAmt);
        BigDecimal[] result = new BigDecimal[decreasingItems.length + 1];
        for (int idx = 0; idx < decreasingItems.length; idx++) {
            int i = idx + 1;
            BigDecimal decreasingItem = checkNonNegative(decreasingItems[idx]);
            if (decreasingItem.compareTo(BigDecimal.ZERO) == 0) {
                result[i] = BigDecimal.ZERO;
            } else if (current.compareTo(decreasingItem) >= 0) {
                result[i] = decreasingItem;
                current = current.subtract(decreasingItem);
            } else {
                result[i] = current;
                current = BigDecimal.ZERO;
            }
        }
        result[0] = current;
        return result;
    }

    /**
     * 校验非空非负数
     *
     * @param num
     * @return java.math.BigDecimal
     */
    public static BigDecimal checkNonNegative(BigDecimal num) {
        if (num == null) {
            return BigDecimal.ZERO;
        }
        if (BigDecimal.ZERO.compareTo(num) > 0) {
            throw new IllegalArgumentException("The amount must not be negative.");
        }
        return num;
    }

    /**
     * 设置金额舍入小数位
     *
     * @param num
     * @return java.math.BigDecimal
     */
    public static BigDecimal setAmountScale(BigDecimal num) {
        return num == null ? null : num.setScale(2, RoundingMode.DOWN);
    }

}
