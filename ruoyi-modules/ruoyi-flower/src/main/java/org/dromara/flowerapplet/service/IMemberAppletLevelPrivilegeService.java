package org.dromara.flowerapplet.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.bo.MemberLevelPrivilegeBo;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;

import java.util.Collection;
import java.util.List;

/**
 * 会员中心--会员等级--权益名称Service接口
 *
 * @author mlhxj
 * @date 2024-12-26
 */
public interface IMemberAppletLevelPrivilegeService {

    /**
     * 查询会员中心--会员等级--权益名称
     *
     * @param id 主键
     * @return 会员中心--会员等级--权益名称
     */
    MemberLevelPrivilegeVo queryById(Long id);

    /**
     * 分页查询会员中心--会员等级--权益名称列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员中心--会员等级--权益名称分页列表
     */
    TableDataInfo<MemberLevelPrivilegeVo> queryPageList(MemberLevelPrivilegeBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员中心--会员等级--权益名称列表
     *
     * @param bo 查询条件
     * @return 会员中心--会员等级--权益名称列表
     */
    List<MemberLevelPrivilegeVo> queryList(MemberLevelPrivilegeBo bo);

    /**
     * 新增会员中心--会员等级--权益名称
     *
     * @param bo 会员中心--会员等级--权益名称
     * @return 是否新增成功
     */
    Boolean insertByBo(MemberLevelPrivilegeBo bo);

    /**
     * 修改会员中心--会员等级--权益名称
     *
     * @param bo 会员中心--会员等级--权益名称
     * @return 是否修改成功
     */
    Boolean updateByBo(MemberLevelPrivilegeBo bo);

    /**
     * 校验并批量删除会员中心--会员等级--权益名称信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    MemberPurchaseRecordVo getPurchasPrivilege(Long id);
}
