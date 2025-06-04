package org.dromara.flowerapplet.mapper;

import org.dromara.flowerapplet.domain.FolwerAppletSku;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductColorVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuColorVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 单品SKUMapper接口
 *
 * @author mlhxj
 * @date 2025-01-16
 */
public interface FolwerAppletSkuMapper extends BaseMapperPlus<FolwerAppletSku, FolwerAppletSkuVo> {

    List<FolwerAppletSkuColorVo> selectByColor(FolwerAppletSkuBo bo);

    List<FolwerAppletSkuColorVo> selectByLevel(FolwerAppletSkuBo bo);

    List<FolwerAppletSkuColorVo> selectBySoldNum(FolwerAppletSkuBo bo);

}
