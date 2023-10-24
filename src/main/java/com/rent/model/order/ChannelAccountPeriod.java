package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumAccountPeriodStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 渠道结算表
 *
 * @author xiaoyao
 * @Date 2020-06-10 17:02
 */
@Data
@Accessors(chain = true)
@TableName("ct_channel_account_period")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelAccountPeriod {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 渠道id
     */
    @TableField(value = "marketing_channel_id")
    private String marketingChannelId;
    /**
     * 渠道名称
     */
    @TableField(value = "marketing_channel_name")
    private String marketingChannelName;
    /**
     * 状态：待结算；待审核；待支付；已支付
     */
    @TableField(value = "status")
    private EnumAccountPeriodStatus status;
    /**
     * 结算总金额
     */
    @TableField(value = "total_amount")
    private BigDecimal totalAmount;
    /**
     * 佣金
     */
    @TableField(value = "total_brokerage")
    private BigDecimal totalBrokerage;
    /**
     * 结算日期
     */
    @TableField(value = "settle_date")
    private Date settleDate;
    /**
     * 实际结算金额
     */
    @TableField(value = "settle_amount")
    private BigDecimal settleAmount;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public static ChannelAccountPeriod init (){
        ChannelAccountPeriod channelAccountPeriod = new ChannelAccountPeriod();
        channelAccountPeriod.setSettleDate(new Date());
        channelAccountPeriod.setStatus(EnumAccountPeriodStatus.WAITING_SETTLEMENT);
        channelAccountPeriod.setTotalAmount(BigDecimal.ZERO);
        channelAccountPeriod.setSettleAmount(BigDecimal.ZERO);
        channelAccountPeriod.setTotalBrokerage(BigDecimal.ZERO);
        return channelAccountPeriod;
    }

    public void addTotalAmount(BigDecimal addend){
        this.totalAmount = this.totalAmount.add(addend);
    }
    public void addTotalSettleAmount(BigDecimal addend){
        this.settleAmount = this.settleAmount.add(addend);
    }

    public void addTotalBrokerage(BigDecimal addend){
        this.totalBrokerage = this.totalBrokerage.add(addend);
    }
}
