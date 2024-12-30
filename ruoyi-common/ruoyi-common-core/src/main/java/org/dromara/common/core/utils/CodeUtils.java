package org.dromara.common.core.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author Peter
 */
public class CodeUtils {

    public static String codeGeneration(String head, int digit, int num, String date) {
        return head + date +
            String.format("%0" + digit + "d", ++num);
    }

}
