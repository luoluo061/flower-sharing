package org.dromara.flower.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户配送地址对象 folwer_pick_addr
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_pick_addr")
public class FolwerPickAddr extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "addr_id")
    private Long addrId;

    /**
     * 自提点名称
     */
    private String addrName;

    /**
     * 地址
     */
    private String addr;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 省份ID
     */
    private Long provinceId;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市ID
     */
    private Long cityId;

    /**
     * 城市
     */
    private String city;

    /**
     * 区/县ID
     */
    private Long areaId;

    /**
     * 区/县
     */
    private String area;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
