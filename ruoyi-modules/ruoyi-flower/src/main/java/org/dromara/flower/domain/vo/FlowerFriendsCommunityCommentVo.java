package org.dromara.flower.domain.vo;

import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.FlowerFriendsCommunityComment;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 花友圈--评论详情视图对象 flower_friends_community_comment
 *
 * @author mlhxj
 * @date 2024-12-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowerFriendsCommunityComment.class)
public class FlowerFriendsCommunityCommentVo implements Serializable {

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
     * 花友圈ID
     */
    @ExcelProperty(value = "花友圈ID")
    private Long flowerFriendsCommunityId;

    /**
     * 跑路时间
     */
    @ExcelProperty(value = "跑路时间")
    private Date commentTime;

    /**
     * 评论内容
     */
    @ExcelProperty(value = "评论内容")
    private String commentContent;

    /**
     * 评论Id，0标识顶级评论，不为 0 表示回复
     */
    @ExcelProperty(value = "评论Id，0标识顶级评论，不为 0 表示沟通回复")
    private Long parentId;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long appletUserInformationId;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


    /**
     * 评论的子级
     */
    private List<FlowerFriendsCommunityCommentVo> replies = new ArrayList<>();
}
