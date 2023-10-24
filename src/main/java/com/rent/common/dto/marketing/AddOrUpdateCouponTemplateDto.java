package com.rent.common.dto.marketing;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.user.UidAndPhone;
import com.rent.common.enums.marketing.CouponTemplateTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 新增或者编辑优惠券模板请求dto
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "优惠券模版")
public class AddOrUpdateCouponTemplateDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id,新增时不用填")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "使用场景 RENT=租赁 BUY_OUT=买断")
    private String scene;

    @Schema(description = "展示说明")
    private String displayNote;

    @Schema(description = "发放数量")
    private Integer num;

    @Schema(description = "剩余数量")
    private Integer leftNum;

    @Schema(description = "最小使用金额")
    private BigDecimal minAmount;

    @Schema(description = "减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "限制用户最多拥有数量")
    private Integer userLimitNum;

    @Schema(description = "有效期开始时间")
    private Date startTime;

    @Schema(description = "有效期结束时间")
    private Date endTime;

    @Schema(description = "有效期，以用户领取时间+delay_day_num")
    private Integer delayDayNum;

    @Schema(description = "添加优惠券的店铺ID ，OPE表示是运营端添加的---前端不用传")
    private String sourceShopId;

    @Schema(description = "添加优惠券的店铺名称---前端不用传")
    private String sourceShopName;

    @Schema(description = "是否针对新人 T：是 F：不是---前端不用传")
    private String forNew;

    @Schema(description = "是否是礼包优惠券 T：是 F：不是")
    private String forPackage;

    @Schema(description = "优惠券用法 SINGLE-独立优惠券|PACKAGE-大礼包|ALIPAY-支付宝券码券|ACTIVITY—营销活动")
    private CouponTemplateTypeEnum type;

    @Schema(description = "PackageId")
    private Long packageId;

    @Schema(description = "VALID：有效 INVALID：失效 RUN_OUT:已经领取完")
    private String status;

    @Schema(description = "渠道编号")
    private String channelId;

    @Schema(description = "添加优惠券指定使用人群   ALL：所有用户    PART：部分用户    NEW：新用户")
    private String userRange;

    @Schema(description = "指定用户时，指定用户的手机号码")
    private List<String> phones;

    private List<UidAndPhone> UidAndPhoneList;

    @Schema(description = "优惠券使用范围的类型 CATEGORY=类目 PRODUCT：商品 ALL:所有商品可用")
    private String rangeType;

    @Schema(description = "优惠券使用范围的值")
    private List<CouponRangeReqDto> rangeList;


    @Schema(description = "优惠券使用范围的类型 CATEGORY=类目 PRODUCT：商品 ALL:所有商品可用")
    private String exRangeType;

    @Schema(description = "优惠券使用范围的值")
    private List<CouponRangeReqDto> exRangeList;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
