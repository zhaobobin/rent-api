package com.rent.common.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户绑卡dto")
public class BankCardDto  implements Serializable {

    private String cardType;
    private String cardNo;
    private String checkValue;
    private String expMonth;
    private String expYear;
    private String mobileNo;
    private Date createTime;
}
