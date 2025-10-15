package org.dromara.flower.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.util.List;

/**
 * 地区表
 *
 * @author mlhxj
 * @date 2025-09-15
 */
@Schema(description = "地区表")
@Data
//@EqualsAndHashCode(callSuper = true)
public class FolwerDeliveryArea{
    @JsonProperty("id")
    @Schema(description = "地区ID" )
    private String id;

    @JsonProperty("name")
    @Schema(description = "地区名字" )
    private String name;

    @JsonProperty("code")
    @Schema(description = "地区编码" )
    private String code;

    @JsonProperty("parentId")
    @Schema(description = "父级ID" )
    private String parentId;

    @JsonProperty("children")
    @Schema(description = "子集" )
    private List<FolwerDeliveryArea> children;
}
