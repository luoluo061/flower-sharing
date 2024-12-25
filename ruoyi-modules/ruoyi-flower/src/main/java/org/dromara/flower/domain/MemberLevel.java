package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 会员等级对象 member_level
 *
 * @author chzl
 * @date 2024-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("member_level")
public class MemberLevel extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
     * 等级
     */
    private String grade;

    /**
     * 等级中文名称
     */
    private String gradeName;

    /**
     * 会员图标
     */
    private String gradeIcon;

    /**
     * 折扣比率数值(无百分号)
     */
    private Long discountRatio;

    /**
     * 价格
     */
    private Long price;

    /**
     * 可以花券/张
     */
    private Long coupon;

    /**
     * 是否显示 0 否 1 是
     */
    private Long display;

    /**
     * 会员权益标签
     */
    private String memberTag;

    /**
     * 初始会员等级 0 否 1 是
     */
    private int initial;

}
