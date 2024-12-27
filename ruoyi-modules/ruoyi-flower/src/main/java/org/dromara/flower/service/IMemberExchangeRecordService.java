package org.dromara.flower.service;

import org.dromara.flower.domain.vo.MemberExchangeRecordVo;
import org.dromara.flower.domain.bo.MemberExchangeRecordBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;

import java.util.Collection;
import java.util.List;

/**
 * 会员中心--兑换记录Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface IMemberExchangeRecordService {

    /**
     * 查询会员中心--兑换记录
     *
     * @param id 主键
     * @return 会员中心--兑换记录
     */
    MemberExchangeRecordVo queryById(Long id);

    /**
     * 分页查询会员中心--兑换记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员中心--兑换记录分页列表
     */
    TableDataInfo<MemberExchangeRecordVo> queryPageList(MemberExchangeRecordBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员中心--兑换记录列表
     *
     * @param bo 查询条件
     * @return 会员中心--兑换记录列表
     */
    List<MemberExchangeRecordVo> queryList(MemberExchangeRecordBo bo);

    /**
     * 新增会员中心--兑换记录
     *
     * @param bo 会员中心--兑换记录
     * @return 是否新增成功
     */
    Boolean insertByBo(MemberExchangeRecordBo bo);

    /**
     * 修改会员中心--兑换记录
     *
     * @param bo 会员中心--兑换记录
     * @return 是否修改成功
     */
    Boolean updateByBo(MemberExchangeRecordBo bo);

    /**
     * 校验并批量删除会员中心--兑换记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 金币兑换现金
     * @param bo
     * @return
     */
    Boolean exchangeCash(AppletUserInformationBo bo);
}
