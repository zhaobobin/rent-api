package com.rent.service.components;


import com.rent.common.dto.components.dto.SxSignCompanyDto;
import com.rent.common.dto.components.dto.SxSignPersonDto;
import com.rent.common.dto.components.response.ContractResponse;
import com.rent.common.dto.components.response.ExpressInfoResponse;
import com.rent.common.dto.components.response.ExpressSignInfoResponse;
import com.rent.common.dto.components.response.IdCardOcrResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SxService {

    /**
     * 首新二要素实名认证
     * https://buershujv.yuque.com/pz57ep/qw3xe2/gva1rgahvuu5sbgi
     *
     * @param realName
     * @param idCardNo
     * @return
     */
    boolean cert(String realName, String idCardNo);

    /**
     * 首新运营商实名认证
     * https://buershujv.yuque.com/pz57ep/qw3xe2/kfo9lsn99z8g5meh?singleDoc#
     * @param realName
     * @param mobile
     * @return
     */
    boolean mobileCheck(String realName, String mobile);

    /**
     * 旗舰版天狼星报告
     * https://buershujv.yuque.com/pz57ep/qw3xe2/sckwee97r6vx83fs?singleDoc#
     *
     * @param name
     * @param idCardNo
     * @param phone
     * @return
     */
    String riskReport(String name, String idCardNo, String phone);

    /**
     * 查询物流信
     * https://buershujv.yuque.com/pz57ep/qw3xe2/mf1vx14u0tvusgtq
     *
     * @param com
     * @param no
     * @param receiverPhone
     * @return
     */
    ExpressInfoResponse getExpressList(String com, String no, String receiverPhone);

    /**
     * 查询签收信息
     * https://buershujv.yuque.com/pz57ep/qw3xe2/mf1vx14u0tvusgtq
     *
     * @param com
     * @param no
     * @param receiverPhone
     * @return
     */
    ExpressSignInfoResponse querySignInfo(String com, String no, String receiverPhone);


    /**
     * 签署合同第一步 上传合同原始文件
     * https://buershujv.yuque.com/pz57ep/qw3xe2/klq3d0gysobp6qqm?singleDoc#pJFaV
     * 上传合同文件
     *
     * @param file
     * @return
     */
    String uploadContract(File file);

    /**
     * 签署合同第二步 盖章并获取下载签署后的合同链接
     * https://buershujv.yuque.com/pz57ep/qw3xe2/klq3d0gysobp6qqm?singleDoc#pJFaV
     *
     * @param contractCode
     * @param signPerson
     * @param signCompany
     * @return
     */
    ContractResponse signContract(String contractCode, List<SxSignPersonDto> signPerson, List<SxSignCompanyDto> signCompany);

    /**
     * OCR身份证识别
     * https://buershujv.yuque.com/pz57ep/qw3xe2/poi9eczgux8qhm33
     *
     * @param frontUrl
     * @param backUrl
     * @return
     * @throws IOException
     */
    IdCardOcrResponse idCardOcr(String frontUrl, String backUrl);

}
