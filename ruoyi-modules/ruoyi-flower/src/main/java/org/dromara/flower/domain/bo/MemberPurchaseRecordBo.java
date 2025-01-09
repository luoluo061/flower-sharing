package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 会员购买记录业务对象 member_purchase_record
 *
 * @author chzl
 * @date 2024-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MemberPurchaseRecord.class, reverseConvertGenerate = false)
public class MemberPurchaseRecordBo extends BaseEntity {

    /**
     * 主键
     */
//    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 部门id
     */
//    @NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 订单编号
     */
//    @NotBlank(message = "订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderCode;

    /**
     * 会员ID
     */
//    @NotBlank(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberId;

    /**
     * 会员名称
     */
//    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberName;

    /**
     * 会员电话
     */
//    @NotBlank(message = "会员电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;

    /**
     * 会员等级ID
     */
//    @NotNull(message = "会员等级ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberLevelId;

    /**
     * 等级
     */
//    @NotBlank(message = "等级不能为空", groups = { AddGroup.class, EditGroup.class })
    private String grade;

    /**
     * 等级中文名称
     */
//    @NotBlank(message = "等级中文名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gradeName;


    /**
     * 价格
     */
//    @NotNull(message = "价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long price;

    /**
     * 支付状态 0 待支付  1 已支付
     */
    private Long payStatus;

    /**
     * 支付信息，支付接口返回信息JSON字符串
     */
    private String payInfo;

}
