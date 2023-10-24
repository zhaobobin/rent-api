package com.rent.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("ct_yfb_bank_card")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserBankCard {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户平台唯一标识
     */
    @TableField(value = "uid")
    private String uid;

    @TableField(value = "card_holder_name")
    private String cardHolderName;

    @TableField(value = "id_card")
    private String icCard;


    @TableField(value = "card_type")
    private String cardType;

    @TableField(value = "card_no")
    private String cardNo;

    @TableField(value = "check_value")
    private String checkValue;

    @TableField(value = "exp_month")
    private String expMonth;

    @TableField(value = "exp_year")
    private String expYear;

    @TableField(value = "mobile_no")
    private String mobileNo;

    @TableField(value = "contract_no")
    private String contractNo;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "delete_time")
    private Date deleteTime;
}
