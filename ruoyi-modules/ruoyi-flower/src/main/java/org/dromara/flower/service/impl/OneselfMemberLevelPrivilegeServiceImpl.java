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
import org.dromara.flower.domain.bo.OneselfMemberLevelPrivilegeBo;
import org.dromara.flower.domain.vo.OneselfMemberLevelPrivilegeVo;
import org.dromara.flower.domain.OneselfMemberLevelPrivilege;
import org.dromara.flower.mapper.OneselfMemberLevelPrivilegeMapper;
import org.dromara.flower.service.IOneselfMemberLevelPrivilegeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 会员中心--个人会员权益详情记录Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-13
 */
@RequiredArgsConstructor
@Service
public class OneselfMemberLevelPrivilegeServiceImpl implements IOneselfMemberLevelPrivilegeService {

    private final OneselfMemberLevelPrivilegeMapper baseMapper;

    /**
     * 查询会员中心--个人会员权益详情记录
     *
     * @param id 主键
     * @return 会员中心--个人会员权益详情记录
     */
    @Override
    public OneselfMemberLevelPrivilegeVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询会员中心--个人会员权益详情记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员中心--个人会员权益详情记录分页列表
     */
    @Override
    public TableDataInfo<OneselfMemberLevelPrivilegeVo> queryPageList(OneselfMemberLevelPrivilegeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<OneselfMemberLevelPrivilege> lqw = buildQueryWrapper(bo);
        Page<OneselfMemberLevelPrivilegeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员中心--个人会员权益详情记录列表
     *
     * @param bo 查询条件
     * @return 会员中心--个人会员权益详情记录列表
     */
    @Override
    public List<OneselfMemberLevelPrivilegeVo> queryList(OneselfMemberLevelPrivilegeBo bo) {
        LambdaQueryWrapper<OneselfMemberLevelPrivilege> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<OneselfMemberLevelPrivilege> buildQueryWrapper(OneselfMemberLevelPrivilegeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<OneselfMemberLevelPrivilege> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, OneselfMemberLevelPrivilege::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), OneselfMemberLevelPrivilege::getName, bo.getName());
        lqw.eq(bo.getAmount() != null, OneselfMemberLevelPrivilege::getAmount, bo.getAmount());
        lqw.eq(bo.getMemberPurchaseRecordId() != null, OneselfMemberLevelPrivilege::getMemberPurchaseRecordId, bo.getMemberPurchaseRecordId());
        lqw.eq(bo.getStatus() != null, OneselfMemberLevelPrivilege::getStatus, bo.getStatus());
        lqw.eq(bo.getEndTime() != null, OneselfMemberLevelPrivilege::getEndTime, bo.getEndTime());
        lqw.eq(bo.getUsageQuantity() != null, OneselfMemberLevelPrivilege::getUsageQuantity, bo.getUsageQuantity());
        return lqw;
    }

    /**
     * 新增会员中心--个人会员权益详情记录
     *
     * @param bo 会员中心--个人会员权益详情记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(OneselfMemberLevelPrivilegeBo bo) {
        OneselfMemberLevelPrivilege add = MapstructUtils.convert(bo, OneselfMemberLevelPrivilege.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改会员中心--个人会员权益详情记录
     *
     * @param bo 会员中心--个人会员权益详情记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(OneselfMemberLevelPrivilegeBo bo) {
        OneselfMemberLevelPrivilege update = MapstructUtils.convert(bo, OneselfMemberLevelPrivilege.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(OneselfMemberLevelPrivilege entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员中心--个人会员权益详情记录信息
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
