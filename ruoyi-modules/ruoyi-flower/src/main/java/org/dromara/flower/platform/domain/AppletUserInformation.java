package org.dromara.flower.platform.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;

import java.io.Serial;

/**
 * 小程序用户信息对象 applet_user_information
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("applet_user_information")
public class AppletUserInformation extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "user_id")
    private Long userId;

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
     * 会员ID 最大11位
     */
    private String memberId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private Long avatarUrl;

    /**
     * 用户类型 xcx 表示小程序
     */
    private String userType;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 小程序openid
     */
    private String openid;

    /**
     * 状态 0 否 1 是
     */
    private Long status;

    /**
     * 微信号
     */
    private String wechatNumber;

    /**
     * 用户分组id
     */
    private Long groupId;

    /**
     * 会员等级id
     */
    private Long memberLevelId;

    /**
     * 性别 0 女 1 男 2 未知 (默认 0 )
     */
    private Long gender;

    /**
     * 积分
     */
    private Long points;

    /**
     * 累计消费金额
     */
    private Long amount;

    /**
     * 累计消费次数
     */
    private Long total;

    /**
     * 推广次数
     */
    private Long promotion;

    /**
     * 累计金币
     */
    private Long gold;

    /**
     * 介绍人ID 0 表示没有介绍人
     */
    private Long parentId;

    /**
     * 生日例子:1999-10-10
     */
    private String birthday;

}
