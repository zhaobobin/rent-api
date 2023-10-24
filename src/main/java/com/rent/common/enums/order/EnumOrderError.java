package com.rent.common.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @since 1.0 2020-6-15 15:05
 */
@Getter
@AllArgsConstructor
public enum EnumOrderError {
    /**
     * ------------------------业务类异常-----------------------
     */
    BIZ_SUCCESS("000000", "success"),
    BIZ_FAILED("999999", "failed"),
    BIZ_TIMEOUT("020440", "timeout"),
    DB_ERROR("100000", "db_error"),

    /**
     * 000200-000399 订单
     */
    ORDER_IDEMPOTENT_EXCEPTION("9130200", "请到商品详情页重新下单"),
    ORDER_EXISTS("9130201", "该订单已存在"),
    ORDER_AMOUNT_ERROR("9130202", "订单金额错误"),
    ORDER_CASHES_SAVE_ERROR("9130203", "订单金额保存失败"),
    ORDER_FREEZE_PAYING("9130204", "已经有一笔正在授权中请耐心等待结果"),
    ORDER_NOT_EXISTS("9130205", "订单不存在"),
    ORDER_STATUS_NOT_ALLOW_APPLY("9130206", "该订单不允许取消"),
    ORDER_STATUS_ERROR("9130207", "订单状态错误"),
    ORDER_SETTLEMENT_STATUS_ERROR("9130208", "结算单状态错误"),
    SETTLEMENT_TYPE_NOT_SUPPORTED("9130209", "结算类型不支持"),
    SETTLEMENT_NOT_EXISTS_ERROR("9130210", "结算单不存在"),
    SETTLEMENT_AMOUNT_ERROR("9130211", "结算金额错误"),
    SKU_NOT_EXISTS("9130212", "sku不存在"),
    DURATION_NOT_EXISTS("9130213", "租期不存在"),
    AUTHING_ORDER_FACE("9130214", "该订单已做人脸认证"),
    NOT_REAL_NAME_AUTH("9130215", "请先做实名认证"),
    ORDER_BY_STAGES_NOT_OPERATOR("9130216", "账单更新中，请重新进入页面"),
    ORDER_BY_STAGES_RENT_ERROR("9130217", "还款金额错误"),
    ACTIVITY_NO_EMPTY_ERROR("9130218", "活动编号不能为空"),
    ACTIVITY_NOT_ENABLE_ERROR("9130219", "当前时间不在活动有效期内"),
    ACTIVITY_NOT_INVENTORY_ERROR("9130219", "当前活动商品库存不足"),
    PRICE_NOT_ALLOW_ERROR("9130220", "租金与原始租金错误"),
    ORDER_BY_STAGES_NOT_EXISTS("9130221", "所选期次不存在"),
    USER_NOT_EXISTS("9130222", "用户不存在"),
    ORDER_STATUS_NOT_ALLOW_NULL("9130223", "订单状态不能为空"),
    ORDER_NOT_SETTLE("9130224", "原订单未结清"),
    RELET_ORDER_EXISTS("9130225", "原订单存在处理中的续租订单"),
    ORDER_REFUND_TIME_OUT("9130226", "取消已超时，请联系客服"),
    SETTLEMENT_NOT_INTACT_AMOUNT_ERROR("9130227", "非完好状态结算金额不能为零"),
    ORDER_APPOINTMENT_UID_EXISTS("9130228", "该用户已预定"),
    ORDER_APPOINTMENT_EXISTS("9130229", "预定订单正在支付中"),
    ORDER_RETURN_RULE_ERROR("9130230", "您还未租满时间，而且当前订单仅支持到期归还"),
    ORDER_RETURN_ERROR("9130231", "此订单状态下不能操作归还按钮"),
    ORDER_RATAIN_ERROR("9130232", "首期订单减免不能和优惠券同时使用，请选择其中一种呀"),
    ORDER_RATAIN_DIFFER_ERROR("9130233", "首期订单减免不能与系统计算减免不一致"),

    /**
     * 000400-000599 买断
     */
    SALE_PRICE_NOT_ALLOW_NULL("9130400", "销售价不能为空"),
    ORDER_BUY_OUT_STATUS_ERROR("9130401", "买断订单状态错误"),
    BUY_OUT_MATURE_ERROR("9130402", "当前下单的商品仅支持到期买断"),
    BUY_OUT_UNRENT_ERROR("9130403", "当前订单的租用结束时间有误，请联系客服！！！"),

    /**
     * 000600-000799 查询
     */
    ORDER_CREATE_START_END_ALL("9130600", "创建时间必须同时为空或非空"),
    RENT_TIME_START_END_ALL("9130601", "租用时间必须同时为空或非空"),
    ORDER_CREATE_START_ERROR("9130602", "时间不能为空且时间间隔不能大于14天"),

    /**
     * 000800-000899 common
     */
    WORD_IO_ERR("000800", "替换word模板异常"),

    ;

    /** 状态码 */
    private String code;

    /** 状态描述 */
    private String msg;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link com.hzsx.rent.order.center.common.enums.EnumOrderError } 实例
     **/
    public static EnumOrderError find(String code) {
        for (EnumOrderError instance : EnumOrderError.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
