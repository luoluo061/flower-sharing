package org.dromara.system.platform.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 小程序用户信息对象 applet_user_information
 *
 * @author LionLi
 * @date 2024-11-19
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
     * 头像
     */
    private String avatarUrl;

    /**
     * 用户类型
     */
    private String userType;


    /**
     * 姓名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

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
     * 积分
     */
    private Long points;

    /**
     * 状态 Y 启用 N 禁用
     */
    private String status;

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
     * 微信号
     */
    private String wechatNumber;

    /**
     * 用户分组id
     */
    //private Long groupId;
    /**
     * 会员等级id
     */
    private Long memberLevelId;
}
