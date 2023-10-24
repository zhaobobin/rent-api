package com.rent.common.util;

import com.rent.util.SnowflakeIdGenUtil;
import org.springframework.stereotype.Component;



/**
 * 字符串工具类
 */
@Component
public class SequenceTool {


    public static String nextId(){
        return SnowflakeIdGenUtil.snowflakeId()+"";
    }


}
