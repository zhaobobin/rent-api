package com.rent.model.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 用户主体表
 *
 * @author zhao
 * @Date 2020-06-28 14:20
 */
@Data
@Schema(description = "用户埋点信息记录表")
public class UserPointDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 渠道位置
     *
     */
    private String position;
    /**
     * 用户ID
     *
     */
    private String uid;
    /**
     * 活动名称
     *
     */
    private String action;
    /**
     * 渠道
     *
     */
    private String channelId;
    /**
     * 创建时间
     *
     */
    private Date createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPointDto that = (UserPointDto) o;
        return Objects.equals(position, that.position) &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(action, that.action) &&
                Objects.equals(channelId, that.channelId);
    }

    @Override
    public int hashCode() {
        return position.hashCode()+uid.hashCode()+action.hashCode()+channelId.hashCode();
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
