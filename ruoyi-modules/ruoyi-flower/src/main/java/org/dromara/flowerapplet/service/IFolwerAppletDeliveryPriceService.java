package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FolwerAppletDeliveryPriceVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletDeliveryPriceBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 物流计费Service接口
 *
 * @author mlhxj
 * @date 2025-07-16
 */
public interface IFolwerAppletDeliveryPriceService {

    /**
     * 查询物流计费
     *
     * @param logisticId 主键
     * @return 物流计费
     */
    FolwerAppletDeliveryPriceVo queryById(Long logisticId);

    /**
     * 分页查询物流计费列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 物流计费分页列表
     */
    TableDataInfo<FolwerAppletDeliveryPriceVo> queryPageList(FolwerAppletDeliveryPriceBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的物流计费列表
     *
     * @param bo 查询条件
     * @return 物流计费列表
     */
    List<FolwerAppletDeliveryPriceVo> queryList(FolwerAppletDeliveryPriceBo bo);

    /**
     * 新增物流计费
     *
     * @param bo 物流计费
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletDeliveryPriceBo bo);

    /**
     * 修改物流计费
     *
     * @param bo 物流计费
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletDeliveryPriceBo bo);

    /**
     * 校验并批量删除物流计费信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 运费计算
     *
     * @param deliveryId 物流公司ID
     * @param userId     用户ID
     * @param basketIds  购物车ID集合
     * @param skuId      skuID
     * @param skuByNum   sku数量
     */
    BigDecimal calculateFreight(Long deliveryId, Long userId, List<String> basketIds, Long skuId, Integer skuByNum, int insulationNum) throws Exception;
}
