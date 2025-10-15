package org.dromara.flower.service;

import org.dromara.flower.domain.FolwerDeliveryArea;
import org.dromara.flower.domain.FolwerDeliveryPriceAdd;
import org.dromara.flower.domain.vo.FolwerDeliveryPriceVo;
import org.dromara.flower.domain.bo.FolwerDeliveryPriceBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 物流计费Service接口
 *
 * @author mlhxj
 * @date 2025-09-15
 */
public interface IFolwerDeliveryPriceService {

    /**
     * 查询物流计费
     *
     * @param logisticId 主键
     * @return 物流计费
     */
    FolwerDeliveryPriceVo queryById(Long logisticId);

    /**
     * 分页查询物流计费列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 物流计费分页列表
     */
    TableDataInfo<FolwerDeliveryPriceVo> queryPageList(FolwerDeliveryPriceBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的物流计费列表
     *
     * @param bo 查询条件
     * @return 物流计费列表
     */
    List<FolwerDeliveryPriceVo> queryList(FolwerDeliveryPriceBo bo);

    /**
     * 新增物流计费
     *
     * @param add 添加物流计费
     * @param areaList 地区
     * @return 是否新增成功
     */
    CompletableFuture<Boolean> insertByBo(FolwerDeliveryPriceAdd add, List<FolwerDeliveryArea>  areaList);

    /**
     * 修改物流计费
     *
     * @param bos 物流计费
     * @return 是否修改成功
     */
    Boolean updateByBo(List<FolwerDeliveryPriceBo> bos);

    /**
     * 校验并批量删除物流计费信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 根据物流公司id删除物流计费信息
     *
     * @param dvyId    物流公司id
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByDvyId(Long dvyId, Boolean isValid);
}
