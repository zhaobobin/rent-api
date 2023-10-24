
package com.rent.service.product.impl;

import com.rent.common.constant.ShopSplitRuleConstant;
import com.rent.common.dto.components.dto.ShopContractDto;
import com.rent.common.dto.components.response.ContractResultResponse;
import com.rent.common.dto.product.SpiltBillRuleConfigDto;
import com.rent.config.outside.OutsideConfig;
import com.rent.config.outside.PlatformChannelDto;
import com.rent.dao.product.ShopDao;
import com.rent.dao.product.ShopEnterpriseInfosDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.Shop;
import com.rent.model.product.ShopEnterpriseInfos;
import com.rent.service.components.ContractSignService;
import com.rent.service.product.PlatformChannelService;
import com.rent.service.product.ShopContractService;
import com.rent.service.product.SplitBillConfigService;
import com.rent.util.AppParamUtil;
import com.rent.util.DateUtil;
import com.rent.util.PDFGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ShopContractServiceImpl implements ShopContractService {

    private static final String savePath = OutsideConfig.TEMP_FILE_DIR+ File.separator;

    private final ShopDao shopDao;
    private final ShopEnterpriseInfosDao shopEnterpriseInfosDao;
    private final SplitBillConfigService splitBillConfigService;
    private final PlatformChannelService platformChannelService;
    private final ContractSignService byteSignService;

    @Override
    public ShopContractDto getShopContractData(String shopId) {
        Shop shop = shopDao.getByShopId(shopId);
        ShopContractDto shopContractDto = new ShopContractDto();
        if(shop==null){
            throw new HzsxBizException("-1","请先完善店铺信息");
        }
        if(StringUtils.isEmpty(shop.getZfbName()) || StringUtils.isEmpty(shop.getZfbCode())){
            throw new HzsxBizException("-1","请完善支付宝账号和姓名信息");
        }
        shopContractDto.setShopId(shopId);
        shopContractDto.setZfbName(shop.getZfbName());
        shopContractDto.setZfbCode(shop.getZfbCode());

        ShopEnterpriseInfos enterpriseInfo = shopEnterpriseInfosDao.getByShopId(shopId);
        if(enterpriseInfo==null){
            throw new HzsxBizException("-1","请完善企业信息");
        }
        if(StringUtils.isEmpty(enterpriseInfo.getName())
                || StringUtils.isEmpty(enterpriseInfo.getBusinessLicenseNo())
                || StringUtils.isEmpty(enterpriseInfo.getContactName())
                || StringUtils.isEmpty(enterpriseInfo.getContactTelephone())
                || StringUtils.isEmpty(enterpriseInfo.getRealname())
                || StringUtils.isEmpty(enterpriseInfo.getIdentity())){
            throw new HzsxBizException("-1","请完善企业信息");
        }

        SpiltBillRuleConfigDto spiltBillRuleConfigDto = splitBillConfigService.getUseAbleConfigByType(shopId,new Date(), ShopSplitRuleConstant.TYPE_RENT, AppParamUtil.getChannelId());
        if(spiltBillRuleConfigDto==null || spiltBillRuleConfigDto.getScale()==null){
            throw new HzsxBizException("-1","请联系运营人员，完善分账规则信息");
        }
        String platformRate = new BigDecimal("1").subtract(spiltBillRuleConfigDto.getScale()).multiply(new BigDecimal("100")).setScale(0).toPlainString()+"%";
        String shopRate = spiltBillRuleConfigDto.getScale().multiply(new BigDecimal("100")).setScale(0).toPlainString()+"%";
        shopContractDto.setPlatformRate(platformRate);
        shopContractDto.setShopRate(shopRate);
        shopContractDto.setName(enterpriseInfo.getName());
        shopContractDto.setBusinessLicenseNo(enterpriseInfo.getBusinessLicenseNo());
        shopContractDto.setContactName(enterpriseInfo.getContactName());
        shopContractDto.setContactTelephone(enterpriseInfo.getContactTelephone());
        shopContractDto.setIdNo(enterpriseInfo.getIdentity());
        shopContractDto.setUserName(enterpriseInfo.getRealname());

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.YEAR,1);
        Date nextYear = calendar.getTime();
        shopContractDto.setStart(DateUtil.date2String(now,DateUtil.DATE_FORMAT_3));
        shopContractDto.setEnd(DateUtil.date2String(nextYear,DateUtil.DATE_FORMAT_3));
        shopContractDto.setCreateTime(DateUtil.date2String(now,DateUtil.DATE_FORMAT_3));

        PlatformChannelDto dto = platformChannelService.getPlatFormChannel(AppParamUtil.getChannelId());
        shopContractDto.setPlatformName(dto.getPlatformName());
        shopContractDto.setPlatformEnterpriseName(dto.getEnterpriseName());
        shopContractDto.setPlatformLicenseNo(dto.getEnterpriseLicenseNo());
        shopContractDto.setPlatformContactName(dto.getEnterpriseLegalName());
        shopContractDto.setPlatformContactTelephone(dto.getEnterpriseLegalPhone());
        shopContractDto.setPlatformContactIdNo(dto.getEnterpriseLegalIdNo());
        shopContractDto.setPlatformAddress(dto.getEnterpriseAddress());
        shopContractDto.setPlatformBank(dto.getBank());
        shopContractDto.setPlatformBankCardNo(dto.getBankCardNo());
        shopContractDto.setPlatformAlipay(dto.getAlipay());
        shopContractDto.setSignAddress(dto.getContractSignCity());
        return shopContractDto;
    }

    @Override
    public String signShopContract(String shopId) {
        ShopContractDto dto = getShopContractData(shopId);

        Map<String, Object> map = new HashMap<>();
        map.put("userName", dto.getUserName());
        map.put("name", dto.getName());
        map.put("businessLicenseNo", dto.getBusinessLicenseNo());
        map.put("contactName", dto.getContactName());
        map.put("contactTelephone", dto.getContactTelephone());
        map.put("start", dto.getStart());
        map.put("end", dto.getEnd());
        map.put("zfbName", dto.getZfbName());
        map.put("zfbCode", dto.getZfbCode());
        map.put("createTime", dto.getCreateTime());
        map.put("shopRate",dto.getShopRate());
        map.put("platformRate",dto.getPlatformRate());


        map.put("platformEnterpriseName",dto.getPlatformEnterpriseName());
        map.put("platformLicenseNo",dto.getPlatformLicenseNo());
        map.put("platformContactName",dto.getPlatformContactName());
        map.put("platformContactTelephone",dto.getPlatformContactTelephone());
        map.put("platformAddress",dto.getPlatformAddress());
        map.put("platformBank",dto.getPlatformBank());
        map.put("platformBankCardNo",dto.getPlatformBankCardNo());
        map.put("platformAlipay",dto.getPlatformAlipay());
        map.put("signAddress",dto.getSignAddress());
        map.put("platformName",dto.getPlatformName());

        String fileName = "shopContract" + shopId;

        String pdfPath = null;
        try {
            pdfPath = PDFGenerator.generate(savePath,fileName + ".pdf","shopContract",map,fileName);
        }catch (Exception e){
            log.error("生成合同pdf失败",e);
            throw new HzsxBizException("-1", "生成合同pdf失败！请联系技术人员处理！");
        }
        ContractResultResponse contractResultResponse = byteSignService.signShopContract(new File(pdfPath),dto);
        String fileUrl = contractResultResponse.getFileUrl();
        Shop shop = shopDao.getByShopId(shopId);
        shop.setShopContractLink(fileUrl);
        shop.setContractCode(contractResultResponse.getContractCode());
        shop.setSignerCode(contractResultResponse.getSignerCodes());
        shopDao.updateById(shop);
        return fileUrl;
    }
}