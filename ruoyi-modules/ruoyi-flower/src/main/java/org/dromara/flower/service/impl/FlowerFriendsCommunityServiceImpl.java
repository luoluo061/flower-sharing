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
import org.dromara.flower.domain.bo.FlowerFriendsCommunityBo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityVo;
import org.dromara.flower.domain.FlowerFriendsCommunity;
import org.dromara.flower.mapper.FlowerFriendsCommunityMapper;
import org.dromara.flower.service.IFlowerFriendsCommunityService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 花友圈Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-30
 */
@RequiredArgsConstructor
@Service
public class FlowerFriendsCommunityServiceImpl implements IFlowerFriendsCommunityService {

    private final FlowerFriendsCommunityMapper baseMapper;

    /**
     * 查询花友圈
     *
     * @param id 主键
     * @return 花友圈
     */
    @Override
    public FlowerFriendsCommunityVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询花友圈列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 花友圈分页列表
     */
    @Override
    public TableDataInfo<FlowerFriendsCommunityVo> queryPageList(FlowerFriendsCommunityBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerFriendsCommunity> lqw = buildQueryWrapper(bo);
        Page<FlowerFriendsCommunityVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的花友圈列表
     *
     * @param bo 查询条件
     * @return 花友圈列表
     */
    @Override
    public List<FlowerFriendsCommunityVo> queryList(FlowerFriendsCommunityBo bo) {
        LambdaQueryWrapper<FlowerFriendsCommunity> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowerFriendsCommunity> buildQueryWrapper(FlowerFriendsCommunityBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowerFriendsCommunity> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, FlowerFriendsCommunity::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), FlowerFriendsCommunity::getTitle, bo.getTitle());
        lqw.eq(bo.getType() != null, FlowerFriendsCommunity::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getMemberId()), FlowerFriendsCommunity::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getMemberName()), FlowerFriendsCommunity::getMemberName, bo.getMemberName());
        lqw.eq(StringUtils.isNotBlank(bo.getGrade()), FlowerFriendsCommunity::getGrade, bo.getGrade());
        lqw.eq(bo.getPageView() != null, FlowerFriendsCommunity::getPageView, bo.getPageView());
        lqw.eq(bo.getLikes() != null, FlowerFriendsCommunity::getLikes, bo.getLikes());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), FlowerFriendsCommunity::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getVideoImagesUrl()), FlowerFriendsCommunity::getVideoImagesUrl, bo.getVideoImagesUrl());
        lqw.eq(bo.getStatus() != null, FlowerFriendsCommunity::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增花友圈
     *
     * @param bo 花友圈
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlowerFriendsCommunityBo bo) {
        FlowerFriendsCommunity add = MapstructUtils.convert(bo, FlowerFriendsCommunity.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改花友圈
     *
     * @param bo 花友圈
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlowerFriendsCommunityBo bo) {
        FlowerFriendsCommunity update = MapstructUtils.convert(bo, FlowerFriendsCommunity.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlowerFriendsCommunity entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除花友圈信息
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
