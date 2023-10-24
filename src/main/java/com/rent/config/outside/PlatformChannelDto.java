package com.rent.config.outside;

import com.rent.util.AppParamUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "平台来源渠道")
public class PlatformChannelDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id")
    private Integer id=1;

    @Schema(description = "渠道ID")
    private String channelId= AppParamUtil.getChannelId();

    @Schema(description = "渠道名字")
    private String channelName = OutsideConfig.APP_NAME;

    @Schema(description = "AppId")
    private String appId = OutsideConfig.APPID;

    @Schema(description = "编码")
    private String code = OutsideConfig.APP_CODE;

    @Schema(description = "平台邮箱")
    private String email = OutsideConfig.E_MAIL;

    @Schema(description = "企业名称")
    private String enterpriseName = OutsideConfig.COMPANY;

    @Schema(description = "企业地址")
    private String enterpriseAddress = OutsideConfig.ADDRESS;

    @Schema(description = "企业营业执照编号")
    private String enterpriseLicenseNo = OutsideConfig.LICENSE_NO;

    @Schema(description = "企业法人姓名")
    private String enterpriseLegalName = OutsideConfig.LEGAL_NAME;

    @Schema(description = "企业法人身份证号")
    private String enterpriseLegalIdNo = OutsideConfig.LEGAL_ID_NO;

    @Schema(description = "企业法人联系电话")
    private String enterpriseLegalPhone = OutsideConfig.LEGAL_PHONE;

    @Schema(description = "平台名称")
    private String platformName = OutsideConfig.APP_NAME;

    @Schema(description = "开户行")
    private String bank = OutsideConfig.BANK;

    @Schema(description = "银行账号")
    private String bankCardNo = OutsideConfig.BANK_CARD_NO;

    @Schema(description = "平台支付宝账号")
    private String alipay = OutsideConfig.ALIPAY_ACCOUNT;

    @Schema(description = "合同签署城市")
    private String contractSignCity = OutsideConfig.SIGN_CITY;

    @Schema(description = "客服电话")
    private String contractPhone = OutsideConfig.CONTRACT_PHONE;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
