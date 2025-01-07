package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.FlowerFriendsCommunityComment;
import org.dromara.flower.domain.bo.FlowerFriendsCommunityCommentBo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityCommentVo;
import org.dromara.flower.mapper.FlowerFriendsCommunityCommentMapper;
import org.dromara.flowerapplet.service.IFlowerAppletFriendsCommunityCommentService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 花友圈--评论详情Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-31
 */
@RequiredArgsConstructor
@Service
public class FlowerAppletFriendsCommunityCommentServiceImpl implements IFlowerAppletFriendsCommunityCommentService {

    private final FlowerFriendsCommunityCommentMapper baseMapper;

    /**
     * 查询花友圈--评论详情
     *
     * @param id 主键
     * @return 花友圈--评论详情
     */
    @Override
    public FlowerFriendsCommunityCommentVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询花友圈--评论详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 花友圈--评论详情分页列表
     */
    @Override
    public TableDataInfo<FlowerFriendsCommunityCommentVo> queryPageList(FlowerFriendsCommunityCommentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerFriendsCommunityComment> lqw = buildQueryWrapper(bo);
        Page<FlowerFriendsCommunityCommentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的花友圈--评论详情列表
     *
     * @param bo 查询条件
     * @return 花友圈--评论详情列表
     */
    @Override
    public List<FlowerFriendsCommunityCommentVo> queryList(FlowerFriendsCommunityCommentBo bo) {
        LambdaQueryWrapper<FlowerFriendsCommunityComment> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowerFriendsCommunityComment> buildQueryWrapper(FlowerFriendsCommunityCommentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowerFriendsCommunityComment> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, FlowerFriendsCommunityComment::getDeptId, bo.getDeptId());
        lqw.eq(bo.getFlowerFriendsCommunityId() != null, FlowerFriendsCommunityComment::getFlowerFriendsCommunityId, bo.getFlowerFriendsCommunityId());
        lqw.eq(bo.getCommentTime() != null, FlowerFriendsCommunityComment::getCommentTime, bo.getCommentTime());
        lqw.eq(StringUtils.isNotBlank(bo.getCommentContent()), FlowerFriendsCommunityComment::getCommentContent, bo.getCommentContent());
        lqw.eq(bo.getParentId() != null, FlowerFriendsCommunityComment::getParentId, bo.getParentId());
        return lqw;
    }

    /**
     * 新增花友圈--评论详情
     *
     * @param bo 花友圈--评论详情
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlowerFriendsCommunityCommentBo bo) {
        FlowerFriendsCommunityComment add = MapstructUtils.convert(bo, FlowerFriendsCommunityComment.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改花友圈--评论详情
     *
     * @param bo 花友圈--评论详情
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlowerFriendsCommunityCommentBo bo) {
        FlowerFriendsCommunityComment update = MapstructUtils.convert(bo, FlowerFriendsCommunityComment.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlowerFriendsCommunityComment entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除花友圈--评论详情信息
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
