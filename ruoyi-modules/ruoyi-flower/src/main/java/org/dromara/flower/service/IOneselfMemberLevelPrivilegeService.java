package org.dromara.flower.service;

import org.dromara.flower.domain.vo.OneselfMemberLevelPrivilegeVo;
import org.dromara.flower.domain.bo.OneselfMemberLevelPrivilegeBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 会员中心--个人会员权益详情记录Service接口
 *
 * @author mlhxj
 * @date 2025-01-13
 */
public interface IOneselfMemberLevelPrivilegeService {

    /**
     * 查询会员中心--个人会员权益详情记录
     *
     * @param id 主键
     * @return 会员中心--个人会员权益详情记录
     */
    OneselfMemberLevelPrivilegeVo queryById(Long id);

    /**
     * 分页查询会员中心--个人会员权益详情记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员中心--个人会员权益详情记录分页列表
     */
    TableDataInfo<OneselfMemberLevelPrivilegeVo> queryPageList(OneselfMemberLevelPrivilegeBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员中心--个人会员权益详情记录列表
     *
     * @param bo 查询条件
     * @return 会员中心--个人会员权益详情记录列表
     */
    List<OneselfMemberLevelPrivilegeVo> queryList(OneselfMemberLevelPrivilegeBo bo);

    /**
     * 新增会员中心--个人会员权益详情记录
     *
     * @param bo 会员中心--个人会员权益详情记录
     * @return 是否新增成功
     */
    Boolean insertByBo(OneselfMemberLevelPrivilegeBo bo);

    /**
     * 修改会员中心--个人会员权益详情记录
     *
     * @param bo 会员中心--个人会员权益详情记录
     * @return 是否修改成功
     */
    Boolean updateByBo(OneselfMemberLevelPrivilegeBo bo);

    /**
     * 校验并批量删除会员中心--个人会员权益详情记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
