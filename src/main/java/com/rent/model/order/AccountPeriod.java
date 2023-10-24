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
 * 账期表
 * @author xiaotong
 */
@Data
@Accessors(chain = true)
@TableName("ct_account_period")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPeriod {

    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商家id
     */
    @TableField(value = "shop_id")
    private String shopId;
    /**
     * 商家名称
     */
    @TableField(value = "shop_name")
    private String shopName;
    /**
     * 结算账号
     */
    @TableField(value = "account_identity")
    private String accountIdentity;
    /**
     * 结算账号名称
     */
    @TableField(value = "account_name")
    private String accountName;
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
     * 结算金额
     */
    @TableField(value = "total_settle_amount")
    private BigDecimal totalSettleAmount;
    /**
     * 佣金
     */
    @TableField(value = "total_brokerage")
    private BigDecimal totalBrokerage;
    /**
     * 常规订单结算总金额
     */
    @TableField(value = "rent_amount")
    private BigDecimal rentAmount;
    /**
     * 常规订单结算金额
     */
    @TableField(value = "rent_settle_amount")
    private BigDecimal rentSettleAmount;
    /**
     * 常规订单佣金
     */
    @TableField(value = "rent_brokerage")
    private BigDecimal rentBrokerage;
    /**
     * 购买订单结算总金额
     */
    @TableField(value = "purchase_amount")
    private BigDecimal purchaseAmount;
    /**
     * 购买订单结算金额
     */
    @TableField(value = "purchase_settle_amount")
    private BigDecimal purchaseSettleAmount;
    /**
     * 购买订单佣金
     */
    @TableField(value = "purchase_brokerage")
    private BigDecimal purchaseBrokerage;
    /**
     * 买断订单结算总金额
     */
    @TableField(value = "buyout_amount")
    private BigDecimal buyoutAmount;
    /**
     * 买断订单结算金额
     */
    @TableField(value = "buyout_settle_amount")
    private BigDecimal buyoutSettleAmount;
    /**
     * 买断订单佣金
     */
    @TableField(value = "buyout_brokerage")
    private BigDecimal buyoutBrokerage;

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
     * 转账业务标题
     */
    @TableField(value = "settle_title")
    private String settleTitle;

    /**
     * 业务备注
     */
    @TableField(value = "settle_remark")
    private String settleRemark;

    /**
     * 业务备注
     */
    @TableField(value = "create_time")
    private Date createTime;


    public static  AccountPeriod init (){
         AccountPeriod
            accountPeriod = new  AccountPeriod();
        accountPeriod.setSettleDate(new Date());
        accountPeriod.setStatus(EnumAccountPeriodStatus.WAITING_SETTLEMENT);
        accountPeriod.setTotalAmount(BigDecimal.ZERO);
        accountPeriod.setTotalSettleAmount(BigDecimal.ZERO);
        accountPeriod.setTotalBrokerage(BigDecimal.ZERO);
        accountPeriod.setRentAmount(BigDecimal.ZERO);
        accountPeriod.setRentSettleAmount(BigDecimal.ZERO);
        accountPeriod.setRentBrokerage(BigDecimal.ZERO);
        accountPeriod.setPurchaseAmount(BigDecimal.ZERO);
        accountPeriod.setPurchaseSettleAmount(BigDecimal.ZERO);
        accountPeriod.setPurchaseBrokerage(BigDecimal.ZERO);
        accountPeriod.setBuyoutAmount(BigDecimal.ZERO);
        accountPeriod.setBuyoutSettleAmount(BigDecimal.ZERO);
        accountPeriod.setBuyoutBrokerage(BigDecimal.ZERO);
        return accountPeriod;
    }


    public void addTotalAmount(BigDecimal addend){
        this.totalAmount = this.totalAmount.add(addend);
    }

    public void addTotalSettleAmount(BigDecimal addend){
        this.totalSettleAmount = this.totalSettleAmount.add(addend);
    }

    public void addTotalBrokerage(BigDecimal addend){
        this.totalBrokerage = this.totalBrokerage.add(addend);
    }

    public void addBuyoutAmount(BigDecimal addend){
        this.buyoutAmount = this.buyoutAmount.add(addend);
    }

    public void addBuyoutSettleAmount(BigDecimal addend){
        this.buyoutSettleAmount = this.buyoutSettleAmount.add(addend);
    }

    public void addBuyoutBrokerage(BigDecimal addend){
        this.buyoutBrokerage = this.buyoutBrokerage.add(addend);
    }


    public void addPurchaseAmount(BigDecimal addend){
        this.purchaseAmount = this.purchaseAmount.add(addend);
    }

    public void addPurchaseSettleAmount(BigDecimal addend){
        this.purchaseSettleAmount = this.purchaseSettleAmount.add(addend);
    }

    public void addPurchaseBrokerage(BigDecimal addend){
        this.purchaseBrokerage = this.purchaseBrokerage.add(addend);
    }

    public void addRentAmount(BigDecimal addend){
        this.rentAmount = this.rentAmount.add(addend);
    }

    public void addRentSettleAmount(BigDecimal addend){
        this.rentSettleAmount = this.rentSettleAmount.add(addend);
    }

    public void addRentBrokerage(BigDecimal addend){
        this.rentBrokerage = this.rentBrokerage.add(addend);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}