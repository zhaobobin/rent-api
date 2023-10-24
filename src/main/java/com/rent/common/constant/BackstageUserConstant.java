package com.rent.common.constant;


import java.io.Serializable;

/**
 *
 * @author zhaowenchao
 */
public class BackstageUserConstant implements Serializable {


    /**
     * 商家店铺通过审核时所属部门权限ID
     */
    public static final Long BACKSTAGE_USER_SHOP_MASTER_DEPARTMENT_ID=2L;
    /**
     * 商家用户注册时所属的部门权限ID
     */
    public static final Long BACKSTAGE_USER_SHOP_REGISTER_DEPARTMENT_ID=3L;
    public static final Long CHANNEL_USER_DEPARTMENT_ID = 4L;


    public static final String BACKSTAGE_USER_CREATE_REGISTER="REGISTER";


}
