package org.dromara.flowerapplet.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flowerapplet.domain.FolwerAppletBasket;
import org.dromara.flowerapplet.domain.vo.FolwerAppletBasketVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 小程序购物车Mapper接口
 *
 * @author mlhxj
 * @date 2025-01-02
 */
public interface FolwerAppletBasketMapper extends BaseMapperPlus<FolwerAppletBasket, FolwerAppletBasketVo> {

    /**
     * 获取购物项
     * @param userId 用户id
     * @return 购物项列表
     */
    List<FolwerAppletBasketVo> getShopCartItems(@Param("userId") Long userId);

}
