package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.FlowerFriendsCommunity;
import org.dromara.flower.domain.bo.FlowerFriendsCommunityBo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityVo;
import org.dromara.flower.mapper.FlowerFriendsCommunityMapper;
import org.dromara.flower.service.IFlowerFriendsCommunityService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/** 弹窗管理 Service（精简版） */
@RequiredArgsConstructor
@Service
public class FlowerFriendsCommunityServiceImpl implements IFlowerFriendsCommunityService {

    private final FlowerFriendsCommunityMapper baseMapper;

    @Override
    public FlowerFriendsCommunityVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    public TableDataInfo<FlowerFriendsCommunityVo> queryPageList(FlowerFriendsCommunityBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerFriendsCommunity> lqw = buildQueryWrapper(bo);
        Page<FlowerFriendsCommunityVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<FlowerFriendsCommunityVo> queryList(FlowerFriendsCommunityBo bo) {
        LambdaQueryWrapper<FlowerFriendsCommunity> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    /** 列表查询条件（含 id 精确筛选） */
    private LambdaQueryWrapper<FlowerFriendsCommunity> buildQueryWrapper(FlowerFriendsCommunityBo bo) {
        LambdaQueryWrapper<FlowerFriendsCommunity> lqw = Wrappers.lambdaQuery();

        // ① 主键精确过滤
        lqw.eq(bo.getId() != null, FlowerFriendsCommunity::getId, bo.getId());

        // ② 其他搜索项
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), FlowerFriendsCommunity::getTitle, bo.getTitle());
        lqw.like(StringUtils.isNotBlank(bo.getTextContent()), FlowerFriendsCommunity::getTextContent, bo.getTextContent());
        lqw.eq(StringUtils.isNotBlank(bo.getPopupImageId()), FlowerFriendsCommunity::getPopupImageId, bo.getPopupImageId());
        lqw.eq(bo.getIsUsed() != null, FlowerFriendsCommunity::getIsUsed, bo.getIsUsed());
        lqw.eq(bo.getPublishType() != null, FlowerFriendsCommunity::getPublishType, bo.getPublishType());

        // 可选排序（按需要保留/删除）
        lqw.orderByDesc(FlowerFriendsCommunity::getCreateTime);
        return lqw;
    }

    @Override
    public Boolean insertByBo(FlowerFriendsCommunityBo bo) {
        FlowerFriendsCommunity e = new FlowerFriendsCommunity();
        e.setId(bo.getId());
        e.setTitle(bo.getTitle());
        e.setTextContent(bo.getTextContent());
        e.setPopupImageId(bo.getPopupImageId());
        e.setIsUsed(bo.getIsUsed());
        e.setPublishType(bo.getPublishType());
        validEntityBeforeSave(e);
        boolean ok = baseMapper.insert(e) > 0;
        if (ok) {
            bo.setId(e.getId());
        }
        return ok;
    }

    @Override
    public Boolean updateByBo(FlowerFriendsCommunityBo bo) {
        FlowerFriendsCommunity e = new FlowerFriendsCommunity();
        e.setId(bo.getId());
        e.setTitle(bo.getTitle());
        e.setTextContent(bo.getTextContent());
        e.setPopupImageId(bo.getPopupImageId());
        e.setIsUsed(bo.getIsUsed());
        e.setPublishType(bo.getPublishType());
        validEntityBeforeSave(e);
        return baseMapper.updateById(e) > 0;
    }

    private void validEntityBeforeSave(FlowerFriendsCommunity e) {
        // 可补充唯一性/启用规则等校验
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (Boolean.TRUE.equals(isValid)) {
            // 业务删除校验（可选）
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
