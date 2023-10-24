package com.rent.common.dto.components.response;

import com.alipay.api.AlipayResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-3 下午 1:55:11
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "获取会员详细信息")
public class AlipayUserInfoResponse extends AlipayResponse {

    /**
     * 详细地址。
     */
    @Schema(description = "address")
    private String address;

    /**
     * 区县名称。
     */
    @Schema(description = "area")
    private String area;

    /**
     * 用户头像地址
     */
    @Schema(description = "avatar")
    private String avatar;

    /**
     * 经营/业务范围（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "business_scope")
    private String businessScope;

    /**
     * 【证件号码】结合证件类型使用.
     * 【注意】只有is_certified为T的时候才有意义，否则不保证准确性.
     */
    @Schema(description = "cert_no")
    private String certNo;

    /**
     * 【证件类型】
     * 0:身份证
     * 1:护照
     * 2:军官证
     * 3:士兵证
     * 4:回乡证
     * 5:临时身份证
     * 6:户口簿
     * 7:警官证
     * 8:台胞证
     * 9:营业执照
     * 10:其它证件
     * 11:港澳居民来往内地通行证
     * 12:台湾居民来往大陆通行证
     * 【注意】只有is_certified为T的时候才有意义，否则不保证准确性.
     */
    @Schema(description = "cert_type")
    private String certType;

    /**
     * 市名称。
     */
    @Schema(description = "city")
    private String city;

    /**
     * 学信网返回的学校名称，有可能为空。
     */
    @Schema(description = "college_name")
    private String collegeName;

    /**
     * 国家码
     */
    @Schema(description = "country_code")
    private String countryCode;

    /**
     * 学信网返回的学历/学位信息，数据质量一般，请谨慎使用，取值包括：博士研究生、硕士研究生、高升本、专科、博士、硕士、本科、夜大电大函大普通班、专科(高职)、第二学士学位。
     */
    @Schema(description = "degree")
    private String degree;

    /**
     * 优先获取email登录名，如果不存在，则返回mobile登录名
     */
    @Schema(description = "email")
    private String email;

    /**
     * 入学时间，yyyy-mm-dd格式
     */
    @Schema(description = "enrollment_time")
    private String enrollmentTime;

    /**
     * 企业代理人证件有效期（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "firm_agent_person_cert_expiry_date")
    private String firmAgentPersonCertExpiryDate;

    /**
     * 企业代理人证件号码（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "firm_agent_person_cert_no")
    private String firmAgentPersonCertNo;

    /**
     * 企业代理人证件类型, 返回值参考cert_type字段（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "firm_agent_person_cert_type")
    private String firmAgentPersonCertType;

    /**
     * 企业代理人姓名（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "firm_agent_person_name")
    private String firmAgentPersonName;

    /**
     * 企业法人证件有效期（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "firm_legal_person_cert_expiry_date")
    private String firmLegalPersonCertExpiryDate;

    /**
     * 法人证件号码（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "firm_legal_person_cert_no")
    private String firmLegalPersonCertNo;

    /**
     * 企业法人证件类型, 返回值参考cert_type字段（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "firm_legal_person_cert_type")
    private String firmLegalPersonCertType;

    /**
     * 企业法人名称（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "firm_legal_person_name")
    private String firmLegalPersonName;

    /**
     * 公司类型，包括（用户类型是公司类型时才有此字段）：
     * CO(公司)
     * INST(事业单位),
     * COMM(社会团体),
     * NGO(民办非企业组织),
     * STATEORGAN(党政国家机关)
     */
    @Schema(description = "firm_type")
    private String firmType;

    /**
     * 【注意】只有is_certified为T的时候才有意义，否则不保证准确性.
     * 性别（F：女性；M：男性）。
     */
    @Schema(description = "gender")
    private String gender;

    /**
     * 预期毕业时间，不保证准确性，yyyy-mm-dd格式。
     */
    @Schema(description = "graduation_time")
    private String graduationTime;

    /**
     * 余额账户是否被冻结。
     * T--被冻结；F--未冻结
     */
    @Schema(description = "is_balance_frozen")
    private String isBalanceFrozen;

    /**
     * 是否通过实名认证。T是通过 F是没有实名认证。
     */
    @Schema(description = "is_certified")
    private String isCertified;

    /**
     * 是否是学生
     */
    @Schema(description = "is_student_certified")
    private String isStudentCertified;

    /**
     * 营业执照有效期，yyyyMMdd或长期，（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "license_expiry_date")
    private String licenseExpiryDate;

    /**
     * 企业执照号码（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "license_no")
    private String licenseNo;

    /**
     * 支付宝会员等级
     */
    @Schema(description = "member_grade")
    private String memberGrade;

    /**
     * 手机号码。
     */
    @Schema(description = "mobile")
    private String mobile;

    /**
     * 用户昵称
     */
    @Schema(description = "nick_name")
    private String nickName;

    /**
     * 组织机构代码（用户类型是公司类型时才有此字段）。
     */
    @Schema(description = "organization_code")
    private String organizationCode;

    /**
     * 个人用户生日。
     */
    @Schema(description = "person_birthday")
    private String personBirthday;

    /**
     * 生日。不包含具体年份，格式MMdd
     */
    @Schema(description = "person_birthday_without_year")
    private String personBirthdayWithoutYear;

    /**
     * 证件有效期（用户类型是个人的时候才有此字段）。
     */
    @Schema(description = "person_cert_expiry_date")
    private String personCertExpiryDate;

    /**
     * 电话号码。
     */
    @Schema(description = "phone")
    private String phone;

    /**
     * 职业
     */
    @Schema(description = "profession")
    private String profession;

    /**
     * 省份名称
     */
    @Schema(description = "province")
    private String province;

    /**
     * 淘宝id
     */
    @Schema(description = "taobao_id")
    private String taobaoId;

    /**
     * 支付宝用户的userId
     */
    @Schema(description = "user_id")
    private String userId;

    /**
     * 若用户是个人用户，则是用户的真实姓名；若是企业用户，则是企业名称。
     * 【注意】只有is_certified为T的时候才有意义，否则不保证准确性.
     */
    @Schema(description = "user_name")
    private String userName;

    /**
     * 用户状态（Q/T/B/W）。
     * Q代表快速注册用户
     * T代表正常用户
     * B代表被冻结账户
     * W代表已注册，未激活的账户
     */
    @Schema(description = "user_status")
    private String userStatus;

    /**
     * 用户类型（1/2）
     * 1代表公司账户2代表个人账户
     */
    @Schema(description = "user_type")
    private String userType;

    /**
     * 邮政编码。
     */
    @Schema(description = "zip")
    private String zip;
}
