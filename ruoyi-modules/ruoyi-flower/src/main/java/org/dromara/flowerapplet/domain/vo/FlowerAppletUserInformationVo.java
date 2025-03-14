package org.dromara.flowerapplet.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flowerapplet.domain.FlowerAppletUserInformation;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 小程序用户信息视图对象 applet_user_information
 *
 * @author mlhxj
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowerAppletUserInformation.class)
public class FlowerAppletUserInformationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long userId;

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 会员ID 最大11位
     */
    @ExcelProperty(value = "会员ID 最大11位")
    private String memberId;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private Long avatarUrl;

    /**
     * 头像Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "avatarUrl")
    private String avatarUrlUrl;
    /**
     * 用户类型 xcx 表示小程序
     */
    @ExcelProperty(value = "用户类型 xcx 表示小程序")
    private String userType;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号")
    private String idNumber;

    /**
     * 小程序openid
     */
    @ExcelProperty(value = "小程序openid")
    private String openid;

    /**
     * 状态 0 否 1 是
     */
    @ExcelProperty(value = "状态 0 否 1 是", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "radio_status")
    private Long status;

    /**
     * 微信号
     */
    @ExcelProperty(value = "微信号")
    private String wechatNumber;

    /**
     * 用户分组id
     */
    @ExcelProperty(value = "用户分组id")
    private Long groupId;

    /**
     * 会员等级id
     */
    @ExcelProperty(value = "会员等级id")
    private Long memberLevelId;

    /**
     * 性别 0 女 1 男 2 未知 (默认 0 )
     */
    @ExcelProperty(value = "性别 0 女 1 男 2 未知 (默认 0 )")
    private Long gender;

    /**
     * 积分
     */
    @ExcelProperty(value = "积分")
    private Long points;

    /**
     * 累计消费金额
     */
    @ExcelProperty(value = "累计消费金额")
    private Long amount;

    /**
     * 累计消费次数
     */
    @ExcelProperty(value = "累计消费次数")
    private Long total;

    /**
     * 推广次数
     */
    @ExcelProperty(value = "推广次数")
    private Long promotion;

    /**
     * 累计金币
     */
    @ExcelProperty(value = "累计金币")
    private Long gold;

    /**
     * 介绍人ID 0 表示没有介绍人
     */
    @ExcelProperty(value = "介绍人ID 0 表示没有介绍人")
    private Long parentId;

    /**
     * 行政区域(如云南省昆明市盘龙区拓东街道)
     */
    @ExcelProperty(value = "行政区域(如云南省昆明市盘龙区拓东街道)")
    private String district;

    /**
     * 地址详细位置
     */
    @ExcelProperty(value = "地址详细位置")
    private String addDetail;

    /**
     * 生日
     */
    @ExcelProperty(value = "生日")
    private String birthday;


    /**
     * 是否认证 0:未认证 1:已认证
     */
    @ExcelProperty(value = "是否认证 0:未认证 1:已认证")
    private Long isAuth;


}
