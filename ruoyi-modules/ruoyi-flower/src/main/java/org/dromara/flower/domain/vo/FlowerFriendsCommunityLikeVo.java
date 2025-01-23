package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.FlowerFriendsCommunityLike;
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
 * 花友圈--点赞详情视图对象 flower_friends_community_like
 *
 * @author mlhxj
 * @date 2025-01-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowerFriendsCommunityLike.class)
public class FlowerFriendsCommunityLikeVo implements Serializable {

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
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private Long memberId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String memberName;

    /**
     * 点赞时间
     */
    @ExcelProperty(value = "点赞时间")
    private Date likeTime;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long appletUserInformationId;


}
