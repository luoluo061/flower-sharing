package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.FolwerProductComm;
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
 * 商品评价视图对象 folwer_product_comm
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerProductComm.class)
public class FolwerProductCommVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long prodCommId;

    /**
     * 商品ID
     */
    @ExcelProperty(value = "商品ID")
    private Long prodId;

    /**
     * 商品名称
     */
    @ExcelProperty(value = "商品名称")
    private String prodName;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private String userId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String userName;

    /**
     * 评论内容
     */
    @ExcelProperty(value = "评论内容")
    private String content;

    /**
     * 回复内容
     */
    @ExcelProperty(value = "回复内容")
    private String replyContent;

    /**
     * 记录时间
     */
    @ExcelProperty(value = "记录时间")
    private Date recTime;

    /**
     * 回复时间
     */
    @ExcelProperty(value = "回复时间")
    private Date replyTime;

    /**
     * 得分，0-5分
     */
    @ExcelProperty(value = "得分，0-5分")
    private Long score;

    /**
     * 是否匿名(1:是  0:否)
     */
    @ExcelProperty(value = "是否匿名(1:是  0:否)")
    private Long isAnonymous;

    /**
     * 是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1
     */
    @ExcelProperty(value = "是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1")
    private Long status;


}
