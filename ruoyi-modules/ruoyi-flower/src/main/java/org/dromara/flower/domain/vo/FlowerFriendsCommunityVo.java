package org.dromara.flower.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.flower.domain.FlowerFriendsCommunity;
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
import java.util.Map;


/**
 * 花友圈视图对象 flower_friends_community
 *
 * @author mlhxj
 * @date 2024-12-30
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowerFriendsCommunity.class)
public class FlowerFriendsCommunityVo implements Serializable {

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
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 发布类型 0 视频 1 图文 2 其它
     */
    @ExcelProperty(value = "发布类型 0 视频 1 图文 2 其它")
    private Long type;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private String memberId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String memberName;

    /**
     * 会员等级
     */
    @ExcelProperty(value = "会员等级")
    private String grade;

    /**
     * 会员等级名称
     */
    @ExcelProperty(value = "会员等级会员等级名称")
    private String gradeName;

    /**
     * 浏览量
     */
    @ExcelProperty(value = "浏览量")
    private Long pageView;

    /**
     * 点赞数
     */
    @ExcelProperty(value = "点赞数")
    private Long likes;

    /**
     * 发表文本内容
     */
    @ExcelProperty(value = "发表文本内容")
    private String content;

    /**
     * 视频或图片ID，多个文件逗号(,)分隔
     */
    @ExcelProperty(value = "视频或图片ID，多个文件逗号(,)分隔")
    private String videoImagesIds;
    /**
     * 视频或图片URL
     */
    @ExcelProperty(value = "视频或图片URL")
    private List<String> videoImagesUrl;

    /**
     * 是否隐匿 0 否 1是
     */
    @ExcelProperty(value = "是否隐匿 0 否 1是")
    private Long status;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private Long createBy;

    /**
     * 头像url
     */
    @ExcelProperty(value = "头像url")
    private String url;

    /**
     * 评论详情
     */
    private List<FlowerFriendsCommunityCommentVo> commentVos;
}
