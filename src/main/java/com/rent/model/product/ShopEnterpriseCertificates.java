
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 店铺资质证书表
 *
 * @author youruo
 * @Date 2020-06-17 10:45
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_enterprise_certificates")
public class ShopEnterpriseCertificates {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 关联shop_enterprice_info表中的id
     * 
     */
    @TableField(value="se_info_id")
    private Integer seInfoId;
    /**
     * 证书图片链接
     * 
     */
    @TableField(value="image")
    private String image;
    /**
     * 类型 0为营业执照号 1为组织机构代码证 2为税务登记证 3为法人身份证正面 4为法人身份证背面
     * 
     */
    @TableField(value="type")
    private Integer type;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
