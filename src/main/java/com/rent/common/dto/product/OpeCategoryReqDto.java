package com.rent.common.dto.product;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 前台类目控制器
 *
 * @author youruo
 * @Date 2020-06-15 11:07
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "前台类目控制器")
public class OpeCategoryReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @Schema(description = "Id")
    private Integer id;

    /**
     * 类目Id
     */
    @Schema(description = "类目ID")
    private Integer categoryId;

    /**
     * 商品Id
     */
    @Schema(description = "商品ID")
    private String itemId;


    /**
     * CreateTime
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 分类图标
     */
    @Schema(description = "分类图标")
    private String icon;

    /**
     * 宣传图片
     */
    @Schema(description = "宣传图片")
    private String bannerIcon;

    /**
     * 父类Id
     */
    @Schema(description = "父类Id -必传")
    private Integer parentId;

    /**
     * 排序规则
     */
    @Schema(description = "排序规则")
    private Integer sortRule;

    @Schema(description = "渠道来源")
    private List<String> channerIds;

    @Schema(description = "渠道来源名称")
    private List<String> channerNames;

    @Schema(description = "生效 0 失效 1 有效")
    private Integer status;


    @Schema(description = "支付宝类目")
    private String zfbCode;


    @Schema(description = "类目分类 1:一级类目 2：二级类目 3：三级类目")
    private String type;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
