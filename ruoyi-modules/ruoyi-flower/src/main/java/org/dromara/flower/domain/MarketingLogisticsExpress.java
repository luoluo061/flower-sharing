package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 营销推广-物流快递对象 marketing_logistics_express
 *
 * @author chy
 * @date 2025-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("marketing_logistics_express")
public class MarketingLogisticsExpress extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type=IdType.ASSIGN_ID)
    private Long id;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;

    /**
     * 快递名称
     */
    private String name;

    /**
     * 快递编码
     */
    private String expressCode;

    /**
     * 排序
     */
    private Long sort;


}
