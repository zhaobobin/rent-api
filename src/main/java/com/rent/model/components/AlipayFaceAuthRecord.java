package com.rent.model.components;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.components.EnumFaceAuthStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 人脸识别记录
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:22
 */
@Data
@Accessors(chain = true)
@TableName("ct_alipay_face_auth_record")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlipayFaceAuthRecord {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "certify_id")
    private String certifyId;

    @TableField(value = "uid")
    private String uid;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "id_card")
    private String idCard;

    @TableField(value = "status")
    private EnumFaceAuthStatus status;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
