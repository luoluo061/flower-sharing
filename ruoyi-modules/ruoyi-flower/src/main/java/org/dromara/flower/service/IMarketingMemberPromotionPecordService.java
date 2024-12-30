package org.dromara.flower.service;

import org.dromara.flower.domain.vo.MarketingMemberPromotionPecordVo;
import org.dromara.flower.domain.bo.MarketingMemberPromotionPecordBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 会员推广记录Service接口
 *
 * @author chy
 * @date 2024-12-31
 */
public interface IMarketingMemberPromotionPecordService {

    /**
     * 查询会员推广记录
     *
     * @param id 主键
     * @return 会员推广记录
     */
    MarketingMemberPromotionPecordVo queryById(Long id);

    /**
     * 分页查询会员推广记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员推广记录分页列表
     */
    TableDataInfo<MarketingMemberPromotionPecordVo> queryPageList(MarketingMemberPromotionPecordBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员推广记录列表
     *
     * @param bo 查询条件
     * @return 会员推广记录列表
     */
    List<MarketingMemberPromotionPecordVo> queryList(MarketingMemberPromotionPecordBo bo);

    /**
     * 新增会员推广记录
     *
     * @param bo 会员推广记录
     * @return 是否新增成功
     */
    Boolean insertByBo(MarketingMemberPromotionPecordBo bo);

    /**
     * 修改会员推广记录
     *
     * @param bo 会员推广记录
     * @return 是否修改成功
     */
    Boolean updateByBo(MarketingMemberPromotionPecordBo bo);

    /**
     * 校验并批量删除会员推广记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
