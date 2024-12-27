package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 物流公司对象 folwer_delivery
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_delivery")
public class FolwerDelivery extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "dvy_id")
    private Long dvyId;

    /**
     * 配送公司名称
     */
    private String dvyName;

    /**
     * 配送方式 1:商家配送, 默认商家配送 2:物流快递 
     */
    private Long dvyType;

    /**
     * 快递代码
     */
    private String dvyCode;

    /**
     * 排序
     */
    private Long seq;

    /**
     * 建立时间
     */
    private Date recTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 物流查询接口
     */
    private String queryUrl;

    /**
     * 发货地址
     */
    private String dvyAddr;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
