package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.MemberLevelPrivilege;
import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.flower.domain.OneselfMemberLevelPrivilege;
import org.dromara.flower.domain.bo.MemberPurchaseRecordBo;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.flower.mapper.MemberLevelPrivilegeMapper;
import org.dromara.flower.mapper.MemberPurchaseRecordMapper;
import org.dromara.flower.mapper.OneselfMemberLevelPrivilegeMapper;
import org.dromara.flower.service.IMemberPurchaseRecordService;
import org.dromara.flower.service.domain.MemberAssetDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 浼氬憳璐拱璁板綍Service涓氬姟灞傚鐞?
 *
 * @author chzl
 * @date 2024-12-24
 */
@RequiredArgsConstructor
@Service
public class MemberPurchaseRecordServiceImpl implements IMemberPurchaseRecordService {

    private final MemberPurchaseRecordMapper baseMapper;
    private final OneselfMemberLevelPrivilegeMapper oneselfMemberLevelPrivilegeMapper;
    private final MemberLevelPrivilegeMapper memberLevelPrivilegeMapper;
    private final MemberAssetDomainService memberAssetDomainService;

    @Override
    public MemberPurchaseRecordVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public TableDataInfo<MemberPurchaseRecordVo> queryPageList(MemberPurchaseRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = buildQueryWrapper(bo);
        Page<MemberPurchaseRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public List<MemberPurchaseRecordVo> queryList(MemberPurchaseRecordBo bo) {
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MemberPurchaseRecord> buildQueryWrapper(MemberPurchaseRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MemberPurchaseRecord::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getOrderCode()), MemberPurchaseRecord::getOrderCode, bo.getOrderCode());
        lqw.like(StringUtils.isNotBlank(bo.getMemberId()), MemberPurchaseRecord::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getMemberName()), MemberPurchaseRecord::getMemberName, bo.getMemberName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), MemberPurchaseRecord::getPhone, bo.getPhone());
        lqw.eq(bo.getMemberLevelId() != null, MemberPurchaseRecord::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(StringUtils.isNotBlank(bo.getGrade()), MemberPurchaseRecord::getGrade, bo.getGrade());
        lqw.like(StringUtils.isNotBlank(bo.getGradeName()), MemberPurchaseRecord::getGradeName, bo.getGradeName());
        lqw.eq(bo.getPrice() != null, MemberPurchaseRecord::getPrice, bo.getPrice());
        return lqw;
    }

    @Override
    @Transactional
    public Boolean insertByBo(MemberPurchaseRecordBo bo) {
        MemberPurchaseRecord add = MapstructUtils.convert(bo, MemberPurchaseRecord.class);
        if (add == null || add.getMemberLevelId() == null) {
            return false;
        }
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        createOneselfMemberInfo(add);
        return flag;
    }

    private void createOneselfMemberInfo(MemberPurchaseRecord add) {
        LambdaQueryWrapper<MemberLevelPrivilege> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MemberLevelPrivilege::getMemberLevelId, add.getMemberLevelId());
        List<MemberLevelPrivilegeVo> vos = memberLevelPrivilegeMapper.selectVoList(lqw);
        List<OneselfMemberLevelPrivilege> privileges = memberAssetDomainService.prepareMemberPrivilegeSnapshot(add, vos);
        if (!privileges.isEmpty()) {
            oneselfMemberLevelPrivilegeMapper.insertBatch(privileges);
        }
    }

    @Override
    public Boolean updateByBo(MemberPurchaseRecordBo bo) {
        MemberPurchaseRecord update = MapstructUtils.convert(bo, MemberPurchaseRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    private void validEntityBeforeSave(MemberPurchaseRecord entity) {
        // TODO 数据校验占位，Stage 3 不改现有会员购买语义
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 预留有效性校验，Stage 3 不扩写会员删除规则
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
