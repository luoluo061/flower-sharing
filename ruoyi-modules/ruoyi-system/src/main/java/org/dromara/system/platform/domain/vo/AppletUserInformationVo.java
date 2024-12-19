package org.dromara.system.platform.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.platform.domain.AppletUserInformation;

import java.io.Serial;
import java.io.Serializable;



/**
 * 小程序用户信息视图对象 applet_user_information
 *
 * @author LionLi
 * @date 2024-11-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppletUserInformation.class)
public class AppletUserInformationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long userId;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String avatarUrl;

    /**
     * 用户类型
     */
    @ExcelProperty(value = "用户类型")
    private String userType;


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
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 微信唯一标识
     */
    @ExcelProperty(value = "小程序openid")
    private String openid;

    /**
     * 积分
     */
    @ExcelProperty(value = "积分")
    private Long points;



    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    private String status;

    /**
     * 微信号
     */
    @ExcelProperty(value = "微信号")
    private String wechatNumber;

    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号")
    private String idNumber;
    /**
     * 等级id
     */
    //private Long rankId;
    /**
     * 等级名
     */
    //private String rankName;
    /**
     * 分组id
     */
    //private Long groupId;
    /**
     * 分组名
     */
    //private String groupName;

}
