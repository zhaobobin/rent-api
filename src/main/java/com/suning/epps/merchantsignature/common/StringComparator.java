/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: StringComparator.java
 * Author:   Andy_Wang01
 * Date:     Aug 1, 2014 11:02:01 AM
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.epps.merchantsignature.common;

import java.util.Comparator;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author Andy_Wang01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StringComparator implements Comparator<String> {

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
    

}
