package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.MemberLevelPrivilegeBo;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.dromara.flower.domain.MemberLevelPrivilege;
import org.dromara.flower.mapper.MemberLevelPrivilegeMapper;
import org.dromara.flower.service.IMemberLevelPrivilegeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 会员中心--会员等级--权益名称Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@RequiredArgsConstructor
@Service
public class MemberLevelPrivilegeServiceImpl implements IMemberLevelPrivilegeService {

    private final MemberLevelPrivilegeMapper baseMapper;

    /**
     * 查询会员中心--会员等级--权益名称
     *
     * @param id 主键
     * @return 会员中心--会员等级--权益名称
     */
    @Override
    public MemberLevelPrivilegeVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询会员中心--会员等级--权益名称列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员中心--会员等级--权益名称分页列表
     */
    @Override
    public TableDataInfo<MemberLevelPrivilegeVo> queryPageList(MemberLevelPrivilegeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MemberLevelPrivilege> lqw = buildQueryWrapper(bo);
        Page<MemberLevelPrivilegeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员中心--会员等级--权益名称列表
     *
     * @param bo 查询条件
     * @return 会员中心--会员等级--权益名称列表
     */
    @Override
    public List<MemberLevelPrivilegeVo> queryList(MemberLevelPrivilegeBo bo) {
        LambdaQueryWrapper<MemberLevelPrivilege> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MemberLevelPrivilege> buildQueryWrapper(MemberLevelPrivilegeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MemberLevelPrivilege> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MemberLevelPrivilege::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), MemberLevelPrivilege::getName, bo.getName());
        lqw.eq(bo.getAmount() != null, MemberLevelPrivilege::getAmount, bo.getAmount());
        lqw.eq(bo.getMemberLevelId() != null, MemberLevelPrivilege::getMemberLevelId, bo.getMemberLevelId());
        return lqw;
    }

    /**
     * 新增会员中心--会员等级--权益名称
     *
     * @param bo 会员中心--会员等级--权益名称
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MemberLevelPrivilegeBo bo) {
        MemberLevelPrivilege add = MapstructUtils.convert(bo, MemberLevelPrivilege.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改会员中心--会员等级--权益名称
     *
     * @param bo 会员中心--会员等级--权益名称
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MemberLevelPrivilegeBo bo) {
        MemberLevelPrivilege update = MapstructUtils.convert(bo, MemberLevelPrivilege.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MemberLevelPrivilege entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员中心--会员等级--权益名称信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
