package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 会员购买记录对象 member_purchase_record
 *
 * @author chzl
 * @date 2024-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("member_purchase_record")
public class MemberPurchaseRecord extends TenantEntity {

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
     * 订单编号
     */
    private String orderCode;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 会员电话
     */
    private String phone;

    /**
     * 会员等级ID
     */
    private Long memberLevelId;

    /**
     * 等级
     */
    private String grade;

    /**
     * 等级中文名称
     */
    private String gradeName;

    /**
     * 价格
     */
    private Long price;

    /**
     * 会员到期时间
     */
    private Date endTime;

    /**
     * 会员状态 0 关闭  1 正常
     */
    private Long status;

    /**
     * 支付状态 0 待支付  1 已支付
     */
    private Long payStatus;

    /**
     * 支付信息，支付接口返回信息JSON字符串
     */
    private String payInfo;
}
