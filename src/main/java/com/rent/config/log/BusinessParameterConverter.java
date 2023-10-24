package com.rent.config.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import com.google.common.collect.Maps;
import com.rent.common.constant.SymbolConstants;
import com.rent.util.AppParamUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author xiaoyao
 * @version v1.0
 * @Date 2020-06-28 23:55:46
 */
public class BusinessParameterConverter extends CompositeConverter<ILoggingEvent> {

    @Override
    protected String transform(ILoggingEvent event, String in) {
        return print(getParameter());
    }

    /**
     * 通过SPI机制获取要打印的业务参数
     * @return key-value形式的业务参数
     */
    private Map<String, String> getParameter() {
        Map<String, String> parameters = Maps.newHashMap();
        parameters.put("流水号", AppParamUtil.getSerialNo());
        return parameters;
    }

    /**
     * 把value值不为空的参数进行组装成字符串
     *
     * @param parameters key-value形式的业务参数
     * @return 要打印的字符串
     */
    private String print(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        parameters.forEach((key, value) -> {
            if (StringUtils.isNotBlank(value)) {
                sb.append(key)
                    .append(SymbolConstants.COLON)
                    .append(StringUtils.SPACE)
                    .append(value)
                    .append(SymbolConstants.COMMA)
                    .append(StringUtils.SPACE);
            }
        });
        // 由于后面有长度 - 2的操作，所以这里要先判断长度是否已经大于2
        return sb.length() > 2 ? SymbolConstants.OPEN_BRACKET + sb.substring(0, sb.length() - 2)
            + SymbolConstants.CLOSE_BRACKET : StringUtils.EMPTY;
    }
}
