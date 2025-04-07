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
import org.dromara.flower.domain.bo.FolwerDeliveryRuleBo;
import org.dromara.flower.domain.vo.FolwerDeliveryRuleVo;
import org.dromara.flower.domain.FolwerDeliveryRule;
import org.dromara.flower.mapper.FolwerDeliveryRuleMapper;
import org.dromara.flower.service.IFolwerDeliveryRuleService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 运费规则Service业务层处理
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@RequiredArgsConstructor
@Service
public class FolwerDeliveryRuleServiceImpl implements IFolwerDeliveryRuleService {

    private final FolwerDeliveryRuleMapper baseMapper;

    /**
     * 查询运费规则
     *
     * @param deliveryRuleId 主键
     * @return 运费规则
     */
    @Override
    public FolwerDeliveryRuleVo queryById(Long deliveryRuleId){
        return baseMapper.selectVoById(deliveryRuleId);
    }

    /**
     * 分页查询运费规则列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 运费规则分页列表
     */
    @Override
    public TableDataInfo<FolwerDeliveryRuleVo> queryPageList(FolwerDeliveryRuleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerDeliveryRule> lqw = buildQueryWrapper(bo);
        Page<FolwerDeliveryRuleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的运费规则列表
     *
     * @param bo 查询条件
     * @return 运费规则列表
     */
    @Override
    public List<FolwerDeliveryRuleVo> queryList(FolwerDeliveryRuleBo bo) {
        LambdaQueryWrapper<FolwerDeliveryRule> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerDeliveryRule> buildQueryWrapper(FolwerDeliveryRuleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerDeliveryRule> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getProvince()), FolwerDeliveryRule::getProvince, bo.getProvince());
        lqw.like(StringUtils.isNotBlank(bo.getDeliveryName()), FolwerDeliveryRule::getDeliveryName, bo.getDeliveryName());
        lqw.eq(bo.getFirstWeight() != null, FolwerDeliveryRule::getFirstWeight, bo.getFirstWeight());
        lqw.eq(bo.getFirstPrice() != null, FolwerDeliveryRule::getFirstPrice, bo.getFirstPrice());
        lqw.eq(bo.getAdditional1Price() != null, FolwerDeliveryRule::getAdditional1Price, bo.getAdditional1Price());
        lqw.eq(bo.getAdditional2Price() != null, FolwerDeliveryRule::getAdditional2Price, bo.getAdditional2Price());
        lqw.eq(bo.getValueAddedPrice() != null, FolwerDeliveryRule::getValueAddedPrice, bo.getValueAddedPrice());
        lqw.eq(bo.getValueAddedWeight() != null, FolwerDeliveryRule::getValueAddedWeight, bo.getValueAddedWeight());
        lqw.eq(bo.getParentChildPackageWeight() != null, FolwerDeliveryRule::getParentChildPackageWeight, bo.getParentChildPackageWeight());
        lqw.eq(bo.getParentChildPackageVolume() != null, FolwerDeliveryRule::getParentChildPackageVolume, bo.getParentChildPackageVolume());
        lqw.eq(bo.getNumber() != null, FolwerDeliveryRule::getNumber, bo.getNumber());
        lqw.eq(bo.getPackagPrice() != null, FolwerDeliveryRule::getPackagPrice, bo.getPackagPrice());
        return lqw;
    }

    /**
     * 新增运费规则
     *
     * @param bo 运费规则
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerDeliveryRuleBo bo) {
        FolwerDeliveryRule add = MapstructUtils.convert(bo, FolwerDeliveryRule.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDeliveryRuleId(add.getDeliveryRuleId());
        }
        return flag;
    }

    /**
     * 修改运费规则
     *
     * @param bo 运费规则
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerDeliveryRuleBo bo) {
        FolwerDeliveryRule update = MapstructUtils.convert(bo, FolwerDeliveryRule.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerDeliveryRule entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除运费规则信息
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
