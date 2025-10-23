package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FlowerAppletFriendsCommunityBo;
import org.dromara.flowerapplet.domain.vo.FlowerAppletFriendsCommunityVo;
import org.dromara.flowerapplet.domain.FlowerAppletFriendsCommunity;
import org.dromara.flowerapplet.mapper.FlowerAppletFriendsCommunityMapper;
import org.dromara.flowerapplet.service.IFlowerAppletFriendsCommunityService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 花友圈Service业务层处理
 *
 * @author mlhxj
 * @date 2025-10-20
 */
@RequiredArgsConstructor
@Service
public class FlowerAppletFriendsCommunityServiceImpl implements IFlowerAppletFriendsCommunityService {

    private final FlowerAppletFriendsCommunityMapper baseMapper;

    /**
     * 查询花友圈
     *
     * @param id 主键
     * @return 花友圈
     */
    @Override
    public FlowerAppletFriendsCommunityVo queryById(Long id){
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
    public TableDataInfo<FlowerAppletFriendsCommunityVo> queryPageList(FlowerAppletFriendsCommunityBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerAppletFriendsCommunity> lqw = buildQueryWrapper(bo);
        Page<FlowerAppletFriendsCommunityVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的花友圈列表
     *
     * @param bo 查询条件
     * @return 花友圈列表
     */
    @Override
    public List<FlowerAppletFriendsCommunityVo> queryList(FlowerAppletFriendsCommunityBo bo) {
        LambdaQueryWrapper<FlowerAppletFriendsCommunity> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowerAppletFriendsCommunity> buildQueryWrapper(FlowerAppletFriendsCommunityBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowerAppletFriendsCommunity> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, FlowerAppletFriendsCommunity::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), FlowerAppletFriendsCommunity::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getTextContent()), FlowerAppletFriendsCommunity::getTextContent, bo.getTextContent());
        lqw.eq(StringUtils.isNotBlank(bo.getPopupImageId()), FlowerAppletFriendsCommunity::getPopupImageId, bo.getPopupImageId());
        lqw.eq(bo.getIsUsed() != null, FlowerAppletFriendsCommunity::getIsUsed, bo.getIsUsed());
        lqw.eq(bo.getPublishType() != null, FlowerAppletFriendsCommunity::getPublishType, bo.getPublishType());
        return lqw;
    }

    /**
     * 新增花友圈
     *
     * @param bo 花友圈
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlowerAppletFriendsCommunityBo bo) {
        FlowerAppletFriendsCommunity add = MapstructUtils.convert(bo, FlowerAppletFriendsCommunity.class);
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
    public Boolean updateByBo(FlowerAppletFriendsCommunityBo bo) {
        FlowerAppletFriendsCommunity update = MapstructUtils.convert(bo, FlowerAppletFriendsCommunity.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlowerAppletFriendsCommunity entity){
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
