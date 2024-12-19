package org.dromara.system.platform.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.util.Date;

/**
 * 行政区划代码对象 merchant_region
 *
 * @author LionLi
 * @date 2024-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("merchant_region")
public class MerchantRegion extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Long delFlag;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 省、直辖市、自治区代码
     */
    private String provinceCode;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 区县代码
     */
    private String countyCode;

    /**
     * 乡镇代码
     */
    private String townCode;

    /**
     * 街道代码
     */
    private String streetCode;

    /**
     * 街道名称
     */
    private String streetName;

    /**
     * 城乡街道分类代码
     */
    private String streetType;

    /**
     * 数据级别：省市区县乡镇街道
     */
    private Long levelType;

    /**
     * 区划激活时间
     */
    private Date enabledDate;

    /**
     * 第一次出现的年份
     */
    private Date createdDate;

    /**
     * 某个区域被取消的时候会在这里标记年份
     */
    private Date disabledDate;

    /**
     * 取消的原因
     */
    private String disabledMark;

    /**
     * 由于历史变更，有些乡变镇，县变区，县变市的城市会从历史中消失不见，此时将该记录给隐藏
     */
    private Long isEnabled;

    /**
     * 数据类型：1:GB2260、2:GB10114_88
     */
    private Long regionType;

    /**
     * 备注
     */
    private String remark;


}
