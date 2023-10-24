package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @program: hzsx-rent-parent
 * @description: 用户主动申请支付需要短信验证码
 * @author: yr
 * @create: 2021-09-19 09:13
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户主动申请支付需要短信验证码")
public class UserPaySmsRequest implements Serializable {


    @Schema(description = "用户uid", required = true)
    @NotNull(message = "用户uid不能为空")
    private String uid;

    @Schema(description = "银行卡尾号", required = true)
    @NotNull(message = "银行卡尾号不能为空")
    private String cardNo;

    @Schema(description = "银行名称", required = true)
    @NotNull(message = "银行名称不能为空")
    private String bankName;


    @Schema(description = "已绑定卡Id")
    private String bindCardId;

}
