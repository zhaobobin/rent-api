
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.product.AntChainProductClassEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 前台类目控制器
 *
 * @author youruo
 * @Date 2020-06-15 11:07
 */
@Data
@Accessors(chain = true)
@TableName("ct_ope_category")
public class OpeCategory {

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
     * 名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 分类图标
     * 
     */
    @TableField(value="icon")
    private String icon;
    /**
     * 宣传图片
     *
     */
    @TableField(value="banner_icon")
    private String bannerIcon;
    /**
     * 父类Id
     * 
     */
    @TableField(value="parent_id")
    private Integer parentId;
    /**
     * 排序规则
     * 
     */
    @TableField(value="sort_rule")
    private Integer sortRule;
    /**
     * 生效 0 失效 1 有效
     *
     */
    @TableField(value="status")
    private Integer status;

    /**
     * 支付宝类目
     *
     */
    @TableField(value="zfb_code")
    private String zfbCode;

    /**
     * 支付宝类目
     * 类目分类 1:一级类目 2：二级类目 3：三级类目
     */
    @TableField(value="type")
    private String type;


    @TableField(value="ant_chain_code")
    private AntChainProductClassEnum antChainCode;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
