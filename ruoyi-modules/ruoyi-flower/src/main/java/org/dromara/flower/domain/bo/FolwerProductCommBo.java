package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerProductComm;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 商品评价业务对象 folwer_product_comm
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerProductComm.class, reverseConvertGenerate = false)
public class FolwerProductCommBo extends BaseEntity {

    /**
     * ID
     */
//    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long prodCommId;

    /**
     * 商品ID
     */
//    @NotNull(message = "商品ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long prodId;

    /**
     * 商品名称
     */
//    @NotBlank(message = "商品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String prodName;

    /**
     * 会员ID
     */
//    @NotBlank(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userId;

    /**
     * 会员名称
     */
//    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 评论内容
     */
//    @NotBlank(message = "评论内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 回复内容
     */
//    @NotBlank(message = "回复内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String replyContent;

    /**
     * 记录时间
     */
//    @NotNull(message = "记录时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date recTime;

    /**
     * 回复时间
     */
//    @NotNull(message = "回复时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date replyTime;

    /**
     * 得分，0-5分
     */
//    @NotNull(message = "得分，0-5分不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long score;

    /**
     * 是否匿名(1:是  0:否)
     */
//    @NotNull(message = "是否匿名(1:是  0:否)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long isAnonymous;

    /**
     * 是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1
     */
//    @NotNull(message = "是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;


}
