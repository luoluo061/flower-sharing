package org.dromara.flowerapplet.mapper;

import org.dromara.flowerapplet.domain.FolwerAppletProduct;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductColorVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 小程序端商品管理Mapper接口
 *
 * @author LL
 * @date 2024-12-31
 */
public interface FolwerAppletProductMapper extends BaseMapperPlus<FolwerAppletProduct, FolwerAppletProductVo> {

    List<FolwerAppletProductColorVo> selectByColor(FolwerAppletProductBo bo);

    List<FolwerAppletProductColorVo> selectByLevel(FolwerAppletProductBo bo);

    List<FolwerAppletProductColorVo> selectBySoldNum(FolwerAppletProductBo bo);


}
