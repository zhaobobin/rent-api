package com.rent.service.components.impl;

import com.rent.common.dto.components.dto.OrderContractDto;
import com.rent.common.dto.components.dto.ShopContractDto;
import com.rent.common.dto.components.dto.SxSignCompanyDto;
import com.rent.common.dto.components.dto.SxSignPersonDto;
import com.rent.common.dto.components.response.ContractResponse;
import com.rent.common.dto.components.response.ContractResultResponse;
import com.rent.common.enums.EnumByteSignEntQualificationType;
import com.rent.common.util.OSSFileUtils;
import com.rent.config.outside.OutsideConfig;
import com.rent.service.components.ContractSignService;
import com.rent.service.components.SxService;
import com.rent.util.OkHttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 电子合同
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ContractSignServiceImpl implements ContractSignService {

    private final SxService sxService;
    private final OSSFileUtils ossFileUtils;

    @Override
    public ContractResultResponse signOrderContract(File localFile, OrderContractDto orderContractDto) {
        ContractResultResponse contractResultResponse = new ContractResultResponse();
        String contractCode = sxService.uploadContract(localFile);
        List<SxSignPersonDto> sxSignPersonList = new ArrayList<>(1);
        SxSignPersonDto user = new SxSignPersonDto();
        user.setKeyWord("乙方：")
                .setPostSignCallback("")
                .setPersonIdCard(orderContractDto.getIdNo())
                .setPersonMobile(orderContractDto.getTelephone())
                .setPersonName(orderContractDto.getUserName());
        sxSignPersonList.add(user);

        List<SxSignCompanyDto> sxSignCompanyList = new ArrayList<>(2);
        SxSignCompanyDto platform = new SxSignCompanyDto();
        platform.setKeyWord("丙方：")
                .setPostSignCallback("")
                .setEntQualificationType(EnumByteSignEntQualificationType.TYPE_7.getCode())
                .setEntName(orderContractDto.getCompany())
                .setEntQualificationNum(orderContractDto.getPlatformLicenseNo())
                .setPersonName(orderContractDto.getPlatformLegalName())
                .setCorporateName(orderContractDto.getPlatformLegalName())
                .setPersonIdCard(orderContractDto.getPlatformLegalIdNo())
                .setPersonMobile(orderContractDto.getPlatformLegalPhone());
        sxSignCompanyList.add(platform);

        SxSignCompanyDto shop = new SxSignCompanyDto();
        shop.setKeyWord("甲方：")
                .setPostSignCallback("")
                .setEntQualificationType(EnumByteSignEntQualificationType.TYPE_7.getCode())
                .setEntName(orderContractDto.getEnterpriseName())
                .setEntQualificationNum(orderContractDto.getBusinessLicenseNo())
                .setPersonName(orderContractDto.getShopLegalName())
                .setCorporateName(orderContractDto.getShopLegalName())
                .setPersonIdCard(orderContractDto.getShopLegalIdNo())
                .setPersonMobile(orderContractDto.getShopLegalPhone());
        sxSignCompanyList.add(shop);
        ContractResponse contractResponse = sxService.signContract(contractCode,sxSignPersonList,sxSignCompanyList);
        String downloadUrl  = contractResponse.getDownloadUrl();
        String downloadPath = OutsideConfig.TEMP_FILE_DIR+ File.separator + orderContractDto.getOrderId()+".pdf";
        OkHttpUtil.downloadFile(downloadUrl,downloadPath);
        String fileUrl =  ossFileUtils.uploadFile("contract",downloadPath,orderContractDto.getOrderId()+".pdf");
        contractResultResponse.setContractCode(contractCode);
        contractResultResponse.setSignerCodes(contractResponse.getSignerCodes());
        contractResultResponse.setFileUrl(fileUrl);
        return contractResultResponse;
    }




    @Override
    public ContractResultResponse signShopContract(File localFile, ShopContractDto dto) {
        String shopId = dto.getShopId();
        String contractCode = sxService.uploadContract(localFile);
        List<SxSignCompanyDto> sxSignCompanyList = new ArrayList<>(2);
        SxSignCompanyDto platform = new SxSignCompanyDto();
        platform.setKeyWord("乙方（盖章）")
                .setPostSignCallback("")
                .setEntQualificationType(EnumByteSignEntQualificationType.TYPE_7.getCode())
                .setEntName(dto.getPlatformEnterpriseName())
                .setEntQualificationNum(dto.getPlatformLicenseNo())
                .setPersonName(dto.getPlatformContactName())
                .setCorporateName(dto.getPlatformContactName())
                .setPersonIdCard(dto.getPlatformContactIdNo())
                .setPersonMobile(dto.getPlatformContactTelephone());
        sxSignCompanyList.add(platform);

        SxSignCompanyDto shop = new SxSignCompanyDto();
        shop.setKeyWord("甲方（盖章）")
                .setPostSignCallback("")
                .setEntQualificationType(EnumByteSignEntQualificationType.TYPE_7.getCode())
                .setEntName(dto.getName())
                .setEntQualificationNum(dto.getBusinessLicenseNo())
                .setPersonName(dto.getUserName())
                .setCorporateName(dto.getUserName())
                .setPersonIdCard(dto.getIdNo())
                .setPersonMobile(dto.getContactTelephone());
        sxSignCompanyList.add(shop);
        ContractResponse contractResponse =sxService.signContract(contractCode,new ArrayList<>(),sxSignCompanyList);
        String downloadUrl  = contractResponse.getDownloadUrl();
        String downloadPath = OutsideConfig.TEMP_FILE_DIR+ File.separator+ shopId+".pdf";
        OkHttpUtil.downloadFile(downloadUrl,downloadPath);
        String fileUrl =  ossFileUtils.uploadFile("shopContract",downloadPath,shopId+".pdf");
        ContractResultResponse contractResultResponse = new ContractResultResponse();
        contractResultResponse.setFileUrl(fileUrl);
        contractResultResponse.setContractCode(contractCode);
        contractResultResponse.setSignerCodes(contractResponse.getSignerCodes());
        return contractResultResponse;
    }
}
