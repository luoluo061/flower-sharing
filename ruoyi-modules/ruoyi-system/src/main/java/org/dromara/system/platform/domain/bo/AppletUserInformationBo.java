package org.dromara.system.platform.domain.bo;


import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.system.platform.domain.AppletUserInformation;

/**
 * 小程序用户信息业务对象 applet_user_information
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppletUserInformation.class, reverseConvertGenerate = false)
public class AppletUserInformationBo extends BaseEntity {

    /**
     * 主键id
     */
    private Long userId;

    /**
     * 部门id
     */
    private Long deptId;

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
     * 行政区域(如云南省昆明市盘龙区拓东街道)
     */
    private String district;

    /**
     * 地址详细位置
     */
    private String addDetail;

    /**
     * 生日 例子:1999-10-10
     */
    private String birthday;


    /**
     * 修改会员积分,金币,等级,兑换金币,金币兑现增减标识 add 加 sub 减
     */
    private String modified;

    /**
     * 修改会员积分,金币 值
     */
    private Long modifiedValue;

}
