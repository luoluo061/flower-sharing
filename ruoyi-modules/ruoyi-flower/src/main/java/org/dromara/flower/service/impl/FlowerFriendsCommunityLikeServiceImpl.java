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
import org.dromara.flower.domain.bo.FlowerFriendsCommunityLikeBo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityLikeVo;
import org.dromara.flower.domain.FlowerFriendsCommunityLike;
import org.dromara.flower.mapper.FlowerFriendsCommunityLikeMapper;
import org.dromara.flower.service.IFlowerFriendsCommunityLikeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 花友圈--点赞详情Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-23
 */
@RequiredArgsConstructor
@Service
public class FlowerFriendsCommunityLikeServiceImpl implements IFlowerFriendsCommunityLikeService {

    private final FlowerFriendsCommunityLikeMapper baseMapper;

    /**
     * 查询花友圈--点赞详情
     *
     * @param id 主键
     * @return 花友圈--点赞详情
     */
    @Override
    public FlowerFriendsCommunityLikeVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询花友圈--点赞详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 花友圈--点赞详情分页列表
     */
    @Override
    public TableDataInfo<FlowerFriendsCommunityLikeVo> queryPageList(FlowerFriendsCommunityLikeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerFriendsCommunityLike> lqw = buildQueryWrapper(bo);
        Page<FlowerFriendsCommunityLikeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的花友圈--点赞详情列表
     *
     * @param bo 查询条件
     * @return 花友圈--点赞详情列表
     */
    @Override
    public List<FlowerFriendsCommunityLikeVo> queryList(FlowerFriendsCommunityLikeBo bo) {
        LambdaQueryWrapper<FlowerFriendsCommunityLike> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowerFriendsCommunityLike> buildQueryWrapper(FlowerFriendsCommunityLikeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowerFriendsCommunityLike> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, FlowerFriendsCommunityLike::getDeptId, bo.getDeptId());
        lqw.eq(bo.getFlowerFriendsCommunityId() != null, FlowerFriendsCommunityLike::getFlowerFriendsCommunityId, bo.getFlowerFriendsCommunityId());
        lqw.eq(bo.getMemberId() != null, FlowerFriendsCommunityLike::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getMemberName()), FlowerFriendsCommunityLike::getMemberName, bo.getMemberName());
        lqw.eq(bo.getLikeTime() != null, FlowerFriendsCommunityLike::getLikeTime, bo.getLikeTime());
        lqw.eq(bo.getAppletUserInformationId() != null, FlowerFriendsCommunityLike::getAppletUserInformationId, bo.getAppletUserInformationId());
        return lqw;
    }

    /**
     * 新增花友圈--点赞详情
     *
     * @param bo 花友圈--点赞详情
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlowerFriendsCommunityLikeBo bo) {
        FlowerFriendsCommunityLike add = MapstructUtils.convert(bo, FlowerFriendsCommunityLike.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改花友圈--点赞详情
     *
     * @param bo 花友圈--点赞详情
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlowerFriendsCommunityLikeBo bo) {
        FlowerFriendsCommunityLike update = MapstructUtils.convert(bo, FlowerFriendsCommunityLike.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlowerFriendsCommunityLike entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除花友圈--点赞详情信息
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
