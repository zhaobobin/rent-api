package com.rent.common.util;

import com.rent.common.enums.order.EnumSerialModalName;
import com.rent.util.SnowflakeIdGenUtil;

/**
 * 主键生成工具
 *
 * @author zhangqing@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020-6-15 10:46
 */
public class SequenceUtil extends SequenceTool {

    /**
     * 获取主键流水号
     *
     * @param serialModelName 流水名
     * @return 生成流水号
     */
    public static String getTypeSerialNo(EnumSerialModalName serialModelName) {
        return serialModelName.getCode()+SnowflakeIdGenUtil.snowflakeId();
    }

}
