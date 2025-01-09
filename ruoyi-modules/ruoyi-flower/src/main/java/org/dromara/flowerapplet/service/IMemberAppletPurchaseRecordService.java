package org.dromara.flowerapplet.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.bo.MemberPurchaseRecordBo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;

import java.util.Collection;
import java.util.List;

/**
 * 小程序会员购买记录Service接口
 *
 * @author chzl
 * @date 2024-12-24
 */
public interface IMemberAppletPurchaseRecordService {

    /**
     * 查询会员购买记录
     *
     * @param id 主键
     * @return 会员购买记录
     */
    MemberPurchaseRecordVo queryById(Long id);

    /**
     * 分页查询会员购买记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员购买记录分页列表
     */
    TableDataInfo<MemberPurchaseRecordVo> queryPageList(MemberPurchaseRecordBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员购买记录列表
     *
     * @param bo 查询条件
     * @return 会员购买记录列表
     */
    List<MemberPurchaseRecordVo> queryList(MemberPurchaseRecordBo bo);

    /**
     * 新增会员购买记录
     *
     * @param bo 会员购买记录
     * @return 是否新增成功
     */
    MemberPurchaseRecordVo insertByBo(MemberPurchaseRecordBo bo);

    /**
     * 修改会员购买记录
     *
     * @param bo 会员购买记录
     * @return 是否修改成功
     */
    Boolean updateByBo(MemberPurchaseRecordBo bo);

    /**
     * 校验并批量删除会员购买记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 支付成功后修改会员购买记录
     * @param bo 会员购买记录
     * @return 成功状态
     */
    Boolean payLaterUpdateByBo(MemberPurchaseRecordBo bo);
}
