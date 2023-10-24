package com.rent.model.components;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumPayType;
import com.rent.common.enums.components.EnumTradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付流水
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Data
@Accessors(chain = true)
@TableName("ct_yfb_trade_serial")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class YfbTradeSerial {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单编号
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 期数 账单支付时会有
     */
    @TableField(value = "period")
    private String period;
    /**
     * uid
     */
    @TableField(value = "uid")
    private String uid;
    /**
     * 支付宝user_id
     */
    @TableField(value = "user_id")
    private String userId;
    /**
     * 商户订单号
     */
    @TableField(value = "out_order_no")
    private String outOrderNo;
    /**
     * 交易号 每次请求唯一，对应支付宝trade_no,out_request_no等
     */
    @TableField(value = "serial_no")
    private String serialNo;
    /**
     * 交易金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;
    /**
     * 支付类型 01：预授权冻结 02：预授权转支付 03：订单创建 04：APP支付 05：退款 06：解冻 07：转账
     */
    @TableField(value = "pay_type")
    private EnumPayType payType;
    /**
     * 交易类型 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账
     */
    @TableField(value = "trade_type")
    private EnumTradeType tradeType;
    /**
     * 状态  00:初始化 01:支付中 02：成功 03：失败
     */
    @TableField(value = "status")
    private EnumAliPayStatus status;
    /**
     * 渠道编号
     */
    @TableField(value = "channel_id")
    private String channelId;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
