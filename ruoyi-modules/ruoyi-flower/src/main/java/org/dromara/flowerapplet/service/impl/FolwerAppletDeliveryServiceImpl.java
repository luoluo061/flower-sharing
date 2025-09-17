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
import org.dromara.flowerapplet.domain.bo.FolwerAppletDeliveryBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletDeliveryVo;
import org.dromara.flowerapplet.domain.FolwerAppletDelivery;
import org.dromara.flowerapplet.mapper.FolwerAppletDeliveryMapper;
import org.dromara.flowerapplet.service.IFolwerAppletDeliveryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 物流公司Service业务层处理
 *
 * @author mlhxj
 * @date 2025-09-02
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletDeliveryServiceImpl implements IFolwerAppletDeliveryService {

    private final FolwerAppletDeliveryMapper baseMapper;

    /**
     * 查询物流公司
     *
     * @param dvyId 主键
     * @return 物流公司
     */
    @Override
    public FolwerAppletDeliveryVo queryById(Long dvyId){
        return baseMapper.selectVoById(dvyId);
    }

    /**
     * 分页查询物流公司列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 物流公司分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletDeliveryVo> queryPageList(FolwerAppletDeliveryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletDelivery> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletDeliveryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的物流公司列表
     *
     * @param bo 查询条件
     * @return 物流公司列表
     */
    @Override
    public List<FolwerAppletDeliveryVo> queryList(FolwerAppletDeliveryBo bo) {
        LambdaQueryWrapper<FolwerAppletDelivery> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletDelivery> buildQueryWrapper(FolwerAppletDeliveryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletDelivery> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getDvyName()), FolwerAppletDelivery::getDvyName, bo.getDvyName());
        lqw.eq(bo.getDvyType() != null, FolwerAppletDelivery::getDvyType, bo.getDvyType());
        lqw.eq(bo.getIsCod() != null, FolwerAppletDelivery::getIsCod, bo.getIsCod());
        lqw.eq(StringUtils.isNotBlank(bo.getReamrk()), FolwerAppletDelivery::getReamrk, bo.getReamrk());
        lqw.eq(bo.getSeq() != null, FolwerAppletDelivery::getSeq, bo.getSeq());
        lqw.eq(StringUtils.isNotBlank(bo.getDvyAddr()), FolwerAppletDelivery::getDvyAddr, bo.getDvyAddr());
        return lqw;
    }

    /**
     * 新增物流公司
     *
     * @param bo 物流公司
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletDeliveryBo bo) {
        FolwerAppletDelivery add = MapstructUtils.convert(bo, FolwerAppletDelivery.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDvyId(add.getDvyId());
        }
        return flag;
    }

    /**
     * 修改物流公司
     *
     * @param bo 物流公司
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletDeliveryBo bo) {
        FolwerAppletDelivery update = MapstructUtils.convert(bo, FolwerAppletDelivery.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletDelivery entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除物流公司信息
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
