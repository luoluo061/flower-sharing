package org.dromara.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态
 *
 * @author ruoyi
 */
@Getter
@AllArgsConstructor
public enum Status {
    /**
     * 正常
     */
    ENABLE("Y", "启用"),
    /**
     * 停用
     */
    DISABLE("N", "停用");


    private final String code;
    private final String info;

}
