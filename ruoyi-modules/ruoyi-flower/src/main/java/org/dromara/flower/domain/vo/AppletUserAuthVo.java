package org.dromara.flower.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flower.domain.AppletUserAuth;
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
 * 小程序用户信息认证视图对象 applet_user_auth
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppletUserAuth.class)
public class AppletUserAuthVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long authId;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private Long userId;

    /**
     * 商店名称
     */
    @ExcelProperty(value = "商店名称")
    private String merchantName;

    /**
     * 营业编号
     */
    @ExcelProperty(value = "营业编号")
    private String merchantType;

    /**
     * 经营许可
     */
    @ExcelProperty(value = "经营许可")
    private String merchantAuthPic;

    /**
     * 经营许可Url
     */
    @ExcelProperty(value = "经营许可Url")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "merchantAuthPic")
    private String merchantAuthPicUrl;

    /**
     * 商店门头照片
     */
    @ExcelProperty(value = "商店门头照片")
    private String merchantFacePic;

    /**
     * 商店门头照片Url
     */
    @ExcelProperty(value = "商店门头照片Url")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "merchantFacePic")
    private String merchantFacePicUrl;

    /**
     * 商店内照片
     */
    @ExcelProperty(value = "商店内照片")
    private String merchantPic;

    /**
     * 商店内照片Url
     */
    @ExcelProperty(value = "商店内照片Url")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "merchantPic")
    private String merchantPicUrl;

    /**
     * 店铺联系人
     */
    @ExcelProperty(value = "店铺联系人")
    private String contactName;

    /**
     * 身份证正面
     */
    @ExcelProperty(value = "身份证正面")
    private String cardFrontPic;

    /**
     * 身份证正面Url
     */
    @ExcelProperty(value = "身份证正面Url")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "cardFrontPic")
    private String cardFrontPicUrl;

    /**
     * 身份证反面
     */
    @ExcelProperty(value = "身份证反面")
    private String cardBackPic;

    /**
     * 身份证反面Url
     */
    @ExcelProperty(value = "身份证反面Url")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "cardBackPic")
    private String cardBackPicUrl;

    /**
     * 身份证ID
     */
    @ExcelProperty(value = "身份证ID")
    private String contactCard;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String contactPhone;

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
     * 状态 0:待审核，1:已审核
     */
    @ExcelProperty(value = "状态 0:待审核，1:已审核")
    private Long status;

    /**
     * 是否通过 0:未通过， 1:已通过
     */
    @ExcelProperty(value = "是否通过 0:未通过， 1:已通过")
    private Long isPass;


}
