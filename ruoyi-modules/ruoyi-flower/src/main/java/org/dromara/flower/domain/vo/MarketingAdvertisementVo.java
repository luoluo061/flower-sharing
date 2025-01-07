package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.MarketingAdvertisement;
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
 * 广告管理视图对象 marketing_advertisement
 *
 * @author chy
 * @date 2024-12-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MarketingAdvertisement.class)
public class MarketingAdvertisementVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 序号
     */
    @ExcelProperty(value = "序号")
    private Long sortId;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型")
    private String type;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String name;

    /**
     * 缩影图url
     */
    @ExcelProperty(value = "缩影图url")
    private String thumbnail;

    /**
     * 链接地址
     */
    @ExcelProperty(value = "链接地址")
    private String link;

    /**
     * 状态 0 否 1 是
     */
    @ExcelProperty(value = "状态 0 否 1 是")
    private Long status;

    @ExcelProperty(value = "缩影图url")
    private String thumbnailUrl;


    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;




}
