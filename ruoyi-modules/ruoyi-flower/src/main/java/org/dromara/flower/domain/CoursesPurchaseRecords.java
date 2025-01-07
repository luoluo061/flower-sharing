package org.dromara.flower.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 课程管理-课程购买记录对象 courses_purchase_records
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("courses_purchase_records")
public class CoursesPurchaseRecords extends TenantEntity {

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
     * 订单号
     */
    private String code;

    /**
     * 课程名称
     */
    private String coursesName;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 小程序用户信息ID
     */
    private Long appletUserInformationId;

    /**
     * 会员名称
     */
    private String name;

    /**
     * 价格
     */
    private Long price;

    /**
     * 订单状态 0 待支付 1 已支付
     */
    private Long status;

    /**
     * 课程ID
     */
    private Long coursesManagerId;
}
