package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户地址表
 *
 * @author zhao
 * @Date 2020-06-18 13:54
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户订单地址修改请求类")
public class UserOrderAddressModifyRequest implements Serializable {

    private static final long serialVersionUID = 7006882764603389429L;


    /**
     * 省
     * 
     */
    @Schema(description = "省")
    @NotNull
    private Integer province;

    /**
     * 市
     * 
     */
    @Schema(description = "市")
    @NotNull
    private Integer city;

    /**
     * 所属用户id
     * 
     */
    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;

    /**
     * 区
     * 
     */
    @Schema(description = "区")
    @NotNull
    private Integer area;

    /**
     * 详细地址
     * 
     */
    @Schema(description = "详细地址")
    @NotBlank
    private String street;

    /**
     * 邮编
     * 
     */
    @Schema(description = "邮编")
    private String zcode;

    /**
     * 手机号码
     * 
     */
    @Schema(description = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String telephone;

    /**
     * 收货人姓名
     * 
     */
    @Schema(description = "收货人姓名")
    @NotBlank(message = "收货人姓名不能为空")
    private String realName;

}
