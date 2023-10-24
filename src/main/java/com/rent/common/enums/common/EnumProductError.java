package com.rent.common.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @since 1.0 2020-6-15 15:05
 */
@Getter
@AllArgsConstructor
public enum EnumProductError {
    /**
     * ------------------------业务类异常-----------------------
     */
    BIZ_SUCCESS("000000", "success"),
    BIZ_FAILED("999999", "failed"),
    BIZ_TIMEOUT("020440", "timeout"),
    DB_ERROR("100000", "db_error"),

    /**
     * 000500-000600 商品
     */
    PRODUCT_NOT_EXISTS("9130500", "商品不存在或已下架"),
    PARAMS_NOT_EXISTS("9130501", "缺少参数"),
    RETURN_NOT_EXISTS("9130501", "缺少归还规则参数"),
    USER_NOT_EXISTS("9130511", "登录用户信息为空，请重新登录"),
    SKU_NOT_EXISTS("9130502", "缺少商品SKU信息"),
    CYCS_ERROR("9130503", "商品周期价格参数不正确"),
    PRICE_ERROR("9130504", "商品sku官方售价不正确"),
    CYCS_PRICE_ERROR("9130507", "商品周期销售价格参数不正确 -租期>=360天时，销售价需<=官方售价*1.35；当租期<=180天时，销售价需<=官方售价*1.18"),
    INVENTORY_ERROR("9130508", "库存参数不正确"),
    SPE_ERROR("9130509", "specAll商品规格参数不正确"),
    FRE_ERROR("9130512", "商品物流方式必传！！！"),
    FRE_ZDY_ERROR("9130513", "自定义物流传参时，参数必须是数字！！！"),
    FRE_ZDY_CMO_ERROR("9130514", "自定义物流传参时，参数必须大于0！！！"),
    SPE_OPE_ERROR("9130510", "颜色规格Id必传"),
    CATE_OPE_ERROR("9130511", "父类parentId必传"),
    IMAGE_NOT_EXISTS("9130512", "请上传商品图片！！！"),
    IMAGE_ERROR_EXISTS("9130512", "商品图片不合规范！！！"),
    CATEID_ERROR("9130709", "categoryId是必传参数！"),
    BUY_ERROR("9130709", "缺少买断参数！"),
    STAGE_ERROR("9130710", "缺少分期参数！"),
    ISHANDLINGFEE_ERROR("9130710", "是否包含分期手续费未传！"),
    CATE_ZFB_CODE_ERROR("9130710", "一级类目必传支付宝对应类目编码！如：手机-RENT_PHONE，参考：https://opendocs.alipay.com/open/10719"),

    /**
     * 000900-001000 部落
     */
    PARENT_ID_ERROR("9130900", "客服只能对一级评论进行回复"),
    TRIBE_COMMENT_ERROR("9130901", "文件未上传成功！"),
    TRIBE_ERROR("9130901", "文件传输有误！"),
    TRIBE_TITLE_ERROR("9130902", "标题不为空！"),
    TRIBE_SUMMARY_ERROR("9130903", "副标题不为空！"),
    TRIBE_CONTENT_ERROR("9130904", "部落内容不为空！"),
    TRIBE_IMAGE_ERROR("9130905", "部落图片不为空！"),
    TRIBE_CHANEEL_ERROR("9130906", "来源渠道不为空！"),
    /**
     * 投诉
     */
    COMPLAINT_TYPE_ERROR("9130906", "投诉渠道类型不可为空！！！"),
    COMPLAINT_ID_ERROR("9130906", "投诉ID不可为空！！！"),
    COMPLAINT_REASULT_ERROR("9130906", "投诉处理结果不可为空！！！"),
    COMPLAINT_NAME_ERROR("9130906", "投诉用户名不可为空！！！"),
    COMPLAINT_UID_ERROR("9130906", "投诉用户UID不可为空！！！"),
    COMPLAINT_TEL_ERROR("9130906", "投诉用户手机号不可为空！！！"),
    COMPLAINT_CONTENT_ERROR("9130906", "投诉内容不可为空！！！"),
    COMPLAINT_IMAGE_ERROR("9130906", "最多上传4张图片！！！"),
    COMPLAINT_ORDER_ERROR("9130906", "投诉订单号不可为空！！！"),
    COMPLAINT_SHOP_ERROR("9130906", "投诉商户不可为空！！！"),
    COMPLAINT_STAT_ERROR("9130906", "投诉星级不可为空！！！"),
    COMPLAINT_ORDERS_ERROR("9130906", "当前用户无下单记录，请先下单！！！"),

    /**
     * 0001000-001500 部落
     */
    DATA_ERROR("9131000", "数据不存在"),
    INDEX_DATA_ERROR("9131001", "专场配置IndexId不存在"),
    LBALE_DATA_ERROR("9131002", "标签商品为空！"),
    TOPIC_DATA_ERROR("9131003", "专场商品为空！"),
    TOPIC_BOTTOM_ERROR("9131004", "Bottom为空！"),
    SPIKE_BOTTOM_ERROR("9131005", "秒杀活动时间不能为空！"),
    SPIKE_PRODUCT_ERROR("9131006", "秒杀活动商品不能为空！"),
    SPIKE_PRODUCTV1_ERROR("9131007", "秒杀活动商品不能重复！"),
    SPIKE_PRODUCTV2_ERROR("9131008", "秒杀活动价格不能为空！"),
    SPIKE_PRODUCTV3_ERROR("9131009", "秒杀活动价格不能小于等于0！"),
    MESSAGE_ERROR("9131010", "同一渠道下相同用户行为只允许一条生效！！！"),

    /**
     * 000900-001000 拼团
     */
    GROUP_CODE_ERROR("9130900", "队员助力时拼团编号不能为空"),
    GROUP_UID_ERROR("9130900", "获取我的拼团分页uid不能为空"),
    GROUP_TYPE_ERROR("9130900", "团长开团时拼团类型不能为空"),

    /**
     * 000700-000800 商家
     */
    SHOP_NOT_EXISTS("9130700", "商家不存在"),
    SHOPID_NOT_EXISTS("9130705", "缺少商家ID"),
    SHOP_ERROR("9130701", "请填写店铺信息"),
    SHOP_ENTERPRISE("9130702", "请填写企业资质信息"),
    SHOP_ENTERPRISE_PICS("9130703", "请上传企业资质信息相关图片文件"),
    SHOP_IDCARD_ERROR("9130705", "缺少法人姓名与身份证号参数"),
    SHOP_EXAMINE_ERROR("9130706", "审核拒绝请填写拒绝理由！"),
    SHOP_AUDIT_ERROR("9130707", "审核状态不正确！"),
    SHOP_ZFB_ERROR("9130708", "支付宝信息不能为空！"),
    SHOP_AUDIT_STATUS_ERROR("9130708", "商家审核通过才可以添加商品，请联系运营管理员！！！"),
    SHOP_LOCK_STATUS_ERROR("9130709", "店铺信息被冻结，请联系运营管理员！！！"),

    SHOP_CHECK_IDCARD_NAME("9130704", "法人姓名与身份证号不匹配,请仔细检查!");

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String msg;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link EnumProductError } 实例
     **/
    public static EnumProductError find(String code) {
        for (EnumProductError instance : EnumProductError.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
