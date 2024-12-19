package org.dromara.system.platform.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.system.platform.domain.MerchantRegion;


import java.util.Date;

/**
 * 行政区划代码业务对象 sys_region
 *
 * @author LionLi
 * @date 2024-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MerchantRegion.class, reverseConvertGenerate = false)
public class MerchantRegionBo extends BaseEntity {

    /**
     * 创建人
     */
    @NotNull(message = "创建人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long createdBy;

    /**
     * 创建时间
     */
    @NotNull(message = "创建时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createdTime;

    /**
     * 更新人
     */
    @NotNull(message = "更新人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long updatedBy;

    /**
     * 更新时间
     */
    @NotNull(message = "更新时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date updatedTime;

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 省、直辖市、自治区代码
     */
    @NotBlank(message = "省、直辖市、自治区代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String provinceCode;

    /**
     * 城市代码
     */
    @NotBlank(message = "城市代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cityCode;

    /**
     * 区县代码
     */
    @NotBlank(message = "区县代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String countyCode;

    /**
     * 乡镇代码
     */
    @NotBlank(message = "乡镇代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String townCode;

    /**
     * 街道代码
     */
    @NotBlank(message = "街道代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String streetCode;

    /**
     * 街道名称
     */
    @NotBlank(message = "街道名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String streetName;

    /**
     * 城乡街道分类代码
     */
    @NotBlank(message = "城乡街道分类代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String streetType;

    /**
     * 数据级别：省市区县乡镇街道
     */
    @NotNull(message = "数据级别：省市区县乡镇街道不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long levelType;

    /**
     * 区划激活时间
     */
    @NotNull(message = "区划激活时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date enabledDate;

    /**
     * 第一次出现的年份
     */
    @NotNull(message = "第一次出现的年份不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createdDate;

    /**
     * 某个区域被取消的时候会在这里标记年份
     */
    @NotNull(message = "某个区域被取消的时候会在这里标记年份不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date disabledDate;

    /**
     * 取消的原因
     */
    @NotBlank(message = "取消的原因不能为空", groups = { AddGroup.class, EditGroup.class })
    private String disabledMark;

    /**
     * 由于历史变更，有些乡变镇，县变区，县变市的城市会从历史中消失不见，此时将该记录给隐藏
     */
    @NotNull(message = "由于历史变更，有些乡变镇，县变区，县变市的城市会从历史中消失不见，此时将该记录给隐藏不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long isEnabled;

    /**
     * 数据类型：1:GB2260、2:GB10114_88
     */
    @NotNull(message = "数据类型：1:GB2260、2:GB10114_88不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long regionType;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
