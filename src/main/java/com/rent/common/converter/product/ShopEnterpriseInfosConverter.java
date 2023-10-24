        
package com.rent.common.converter.product;

import com.rent.common.dto.product.ShopEnterpriseInfosDto;
import com.rent.model.product.ShopEnterpriseInfos;


/**
 * 店铺资质表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:46
 */
public class ShopEnterpriseInfosConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ShopEnterpriseInfosDto model2Dto(ShopEnterpriseInfos model) {
        if (model == null) {
            return null;
        }
        ShopEnterpriseInfosDto dto = new ShopEnterpriseInfosDto();
        dto.setId(model.getId());
        dto.setShopId(model.getShopId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setName(model.getName());
        dto.setRegistrationCapital(model.getRegistrationCapital());
        dto.setBusinessLicenseNo(model.getBusinessLicenseNo());
        dto.setLicenseStart(model.getLicenseStart());
        dto.setLicenseEnd(model.getLicenseEnd());
        dto.setLicenseProvince(model.getLicenseProvince());
        dto.setLicenseCity(model.getLicenseCity());
        dto.setBusinessScope(model.getBusinessScope());
        dto.setRealname(model.getRealname());
        dto.setTelephone(model.getTelephone());
        dto.setContactName(model.getContactName());
        dto.setContactTelephone(model.getContactTelephone());
        dto.setContactQq(model.getContactQq());
        dto.setContactEmail(model.getContactEmail());
        dto.setIsMerged(model.getIsMerged());
        dto.setOclStart(model.getOclStart());
        dto.setOclEnd(model.getOclEnd());
        dto.setStatus(model.getStatus());
        dto.setReason(model.getReason());
        dto.setIdentity(model.getIdentity());
        dto.setLicenseStreet(model.getLicenseStreet());
        dto.setSealNo(model.getSealNo());
        dto.setContractSealNo(model.getContractSealNo());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ShopEnterpriseInfos dto2Model(ShopEnterpriseInfosDto dto) {
        if (dto == null) {
            return null;
        }
        ShopEnterpriseInfos model = new ShopEnterpriseInfos();
        model.setId(dto.getId());
        model.setShopId(dto.getShopId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setName(dto.getName());
        model.setRegistrationCapital(dto.getRegistrationCapital());
        model.setBusinessLicenseNo(dto.getBusinessLicenseNo());
        model.setLicenseStart(dto.getLicenseStart());
        model.setLicenseEnd(dto.getLicenseEnd());
        model.setLicenseProvince(dto.getLicenseProvince());
        model.setLicenseCity(dto.getLicenseCity());
        model.setBusinessScope(dto.getBusinessScope());
        model.setRealname(dto.getRealname());
        model.setTelephone(dto.getTelephone());
        model.setContactName(dto.getContactName());
        model.setContactTelephone(dto.getContactTelephone());
        model.setContactQq(dto.getContactQq());
        model.setContactEmail(dto.getContactEmail());
        model.setIsMerged(dto.getIsMerged());
        model.setOclStart(dto.getOclStart());
        model.setOclEnd(dto.getOclEnd());
        model.setStatus(dto.getStatus());
        model.setReason(dto.getReason());
        model.setIdentity(dto.getIdentity());
        model.setLicenseStreet(dto.getLicenseStreet());
        model.setSealNo(dto.getSealNo());
        model.setContractSealNo(dto.getContractSealNo());
        return model;
    }
}