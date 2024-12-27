package org.dromara.flower.service;

import org.dromara.flower.domain.vo.MemberPointsExchangeGoldVo;
import org.dromara.flower.domain.bo.MemberPointsExchangeGoldBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;

import java.util.Collection;
import java.util.List;

/**
 * 会员中心--积分兑换为金币Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface IMemberPointsExchangeGoldService {

    /**
     * 查询会员中心--积分兑换为金币
     *
     * @param id 主键
     * @return 会员中心--积分兑换为金币
     */
    MemberPointsExchangeGoldVo queryById(Long id);

    /**
     * 分页查询会员中心--积分兑换为金币列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员中心--积分兑换为金币分页列表
     */
    TableDataInfo<MemberPointsExchangeGoldVo> queryPageList(MemberPointsExchangeGoldBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员中心--积分兑换为金币列表
     *
     * @param bo 查询条件
     * @return 会员中心--积分兑换为金币列表
     */
    List<MemberPointsExchangeGoldVo> queryList(MemberPointsExchangeGoldBo bo);

    /**
     * 新增会员中心--积分兑换为金币
     *
     * @param bo 会员中心--积分兑换为金币
     * @return 是否新增成功
     */
    Boolean insertByBo(MemberPointsExchangeGoldBo bo);

    /**
     * 修改会员中心--积分兑换为金币
     *
     * @param bo 会员中心--积分兑换为金币
     * @return 是否修改成功
     */
    Boolean updateByBo(MemberPointsExchangeGoldBo bo);

    /**
     * 校验并批量删除会员中心--积分兑换为金币信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 积分兑换金币
     * @param bo
     * @return
     */
    Boolean exchangeGoldByBo(AppletUserInformationBo bo);
}
