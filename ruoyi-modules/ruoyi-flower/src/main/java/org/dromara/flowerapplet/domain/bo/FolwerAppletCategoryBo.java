package org.dromara.flowerapplet.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flowerapplet.domain.FolwerAppletCategory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 小程序端产品类目业务对象 folwer_category
 *
 * @author Lion Li
 * @date 2025-01-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletCategory.class, reverseConvertGenerate = false)
public class FolwerAppletCategoryBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 产品类目名称
     */
    private String categoryName;

    /**
     * 类目图标
     */
    private String icon;

    /**
     * 排序
     */
    private Long seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    private Long status;

    /**
     * 是否显示花艺课程 默认是0，表示不显示,1为花艺课程
     */
    private Long isShowFeature;

    /**
     * 显示时间
     */
    private Date showTime;

    /**
     * 部门id
     */
//    private Long deptId;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
