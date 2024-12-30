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
import org.dromara.flower.domain.bo.MarketingAdvertisementBo;
import org.dromara.flower.domain.vo.MarketingAdvertisementVo;
import org.dromara.flower.domain.MarketingAdvertisement;
import org.dromara.flower.mapper.MarketingAdvertisementMapper;
import org.dromara.flower.service.IMarketingAdvertisementService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 广告管理Service业务层处理
 *
 * @author chy
 * @date 2024-12-31
 */
@RequiredArgsConstructor
@Service
public class MarketingAdvertisementServiceImpl implements IMarketingAdvertisementService {

    private final MarketingAdvertisementMapper baseMapper;

    /**
     * 查询广告管理
     *
     * @param id 主键
     * @return 广告管理
     */
    @Override
    public MarketingAdvertisementVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询广告管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 广告管理分页列表
     */
    @Override
    public TableDataInfo<MarketingAdvertisementVo> queryPageList(MarketingAdvertisementBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MarketingAdvertisement> lqw = buildQueryWrapper(bo);
        Page<MarketingAdvertisementVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的广告管理列表
     *
     * @param bo 查询条件
     * @return 广告管理列表
     */
    @Override
    public List<MarketingAdvertisementVo> queryList(MarketingAdvertisementBo bo) {
        LambdaQueryWrapper<MarketingAdvertisement> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MarketingAdvertisement> buildQueryWrapper(MarketingAdvertisementBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MarketingAdvertisement> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MarketingAdvertisement::getDeptId, bo.getDeptId());
        lqw.eq(bo.getSortId() != null, MarketingAdvertisement::getSortId, bo.getSortId());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), MarketingAdvertisement::getType, bo.getType());
        lqw.like(StringUtils.isNotBlank(bo.getName()), MarketingAdvertisement::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getThumbnail()), MarketingAdvertisement::getThumbnail, bo.getThumbnail());
        lqw.eq(StringUtils.isNotBlank(bo.getLink()), MarketingAdvertisement::getLink, bo.getLink());
        lqw.eq(bo.getStatus() != null, MarketingAdvertisement::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增广告管理
     *
     * @param bo 广告管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MarketingAdvertisementBo bo) {
        MarketingAdvertisement add = MapstructUtils.convert(bo, MarketingAdvertisement.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改广告管理
     *
     * @param bo 广告管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MarketingAdvertisementBo bo) {
        MarketingAdvertisement update = MapstructUtils.convert(bo, MarketingAdvertisement.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MarketingAdvertisement entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除广告管理信息
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
