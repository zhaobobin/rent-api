package com.rent.common.dto.components.request;

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

/**
 * 步云不同类型账号
 *
 * @author youruo
 * @Date 2021-03-30 16:02
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "步云不同类型账号")
public class BuyunConfigReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     * 
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 短信行业类型
     * 
     */
    @Schema(description = "短信行业类型")
    private String smsType;

    /**
     * 账号
     * 
     */
    @Schema(description = "账号")
    private String account;

    /**
     * 接口密码
     * 
     */
    @Schema(description = "接口密码")
    private String password;

    /**
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     * 
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 删除时间
     * 
     */
    @Schema(description = "删除时间")
    private Date deleteTime;

    /**
     * 备注
     * 
     */
    @Schema(description = "备注")
    private String remark;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
