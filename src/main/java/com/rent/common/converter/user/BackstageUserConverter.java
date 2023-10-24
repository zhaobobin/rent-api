        
package com.rent.common.converter.user;

import com.rent.common.dto.backstage.resp.PageBackstageUserResp;
import com.rent.common.dto.user.BackstageUserDto;
import com.rent.common.dto.user.BackstageUserReqDto;
import com.rent.model.user.BackstageUser;


/**
 * 后台用户Service
 *
 * @author zhao
 * @Date 2020-06-24 11:46
 */
public class BackstageUserConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static BackstageUserDto model2Dto(BackstageUser model) {
        if (model == null) {
            return null;
        }
        BackstageUserDto dto = new BackstageUserDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setName(model.getName());
        dto.setEmail(model.getEmail());
        dto.setPassword(model.getPassword());
        dto.setType(model.getType());
        dto.setStatus(model.getStatus());
        dto.setMobile(model.getMobile());
        dto.setShopId(model.getShopId());
        dto.setSalt(model.getSalt());
        dto.setCreateUserName(model.getCreateUserName());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static BackstageUser dto2Model(BackstageUserDto dto) {
        if (dto == null) {
            return null;
        }
        BackstageUser model = new BackstageUser();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());
        model.setType(dto.getType());
        model.setStatus(dto.getStatus());
        model.setMobile(dto.getMobile());
        model.setShopId(dto.getShopId());
        model.setSalt(dto.getSalt());
        model.setRemark(dto.getRemark());
        model.setCreateUserName(dto.getCreateUserName());
        return model;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static BackstageUser reqDto2Model(BackstageUserReqDto dto) {
        if (dto == null) {
            return null;
        }
        BackstageUser model = new BackstageUser();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setType(dto.getType());
        model.setMobile(dto.getMobile());
        model.setShopId(dto.getShopId());
        return model;
    }

    /**
     * 模型转分页查询数据dto
     * @param model
     * @return
     */
    public static PageBackstageUserResp model2PageDto(BackstageUser model) {
        PageBackstageUserResp dto = new PageBackstageUserResp();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setName(model.getName());
        dto.setEmail(model.getEmail());
        dto.setStatus(model.getStatus());
        dto.setMobile(model.getMobile());
        return dto;
    }
}