package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 商品评价对象 folwer_product_comm
 *
 * @author Lion Li
 * @date 2024-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_product_comm")
public class FolwerProductComm extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "prod_comm_id")
    private Long prodCommId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 会员ID
     */
    private String userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 记录时间
     */
    private Date recTime;

    /**
     * 回复时间
     */
    private Date replyTime;

    /**
     * 得分，0-5分
     */
    private Long score;

    /**
     * 是否匿名(1:是  0:否)
     */
    private Long isAnonymous;

    /**
     * 是否显示，1:为显示，0:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是0,，否则1
     */
    private Long status;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
