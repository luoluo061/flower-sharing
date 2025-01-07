package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.MarketingLogisticsExpressBo;
import org.dromara.flower.domain.vo.MarketingLogisticsExpressVo;
import org.dromara.flower.domain.MarketingLogisticsExpress;
import org.dromara.flower.mapper.MarketingLogisticsExpressMapper;
import org.dromara.flower.service.IMarketingLogisticsExpressService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 营销推广-物流快递Service业务层处理
 *
 * @author chy
 * @date 2025-01-06
 */
@RequiredArgsConstructor
@Service
public class MarketingLogisticsExpressServiceImpl implements IMarketingLogisticsExpressService {

    private final MarketingLogisticsExpressMapper baseMapper;

    /**
     * 查询营销推广-物流快递
     *
     * @param id 主键
     * @return 营销推广-物流快递
     */
    @Override
    public MarketingLogisticsExpressVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询营销推广-物流快递列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 营销推广-物流快递分页列表
     */
    @Override
    public TableDataInfo<MarketingLogisticsExpressVo> queryPageList(MarketingLogisticsExpressBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MarketingLogisticsExpress> lqw = buildQueryWrapper(bo);
        lqw.orderByAsc(MarketingLogisticsExpress::getSort);
        Page<MarketingLogisticsExpressVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的营销推广-物流快递列表
     *
     * @param bo 查询条件
     * @return 营销推广-物流快递列表
     */
    @Override
    public List<MarketingLogisticsExpressVo> queryList(MarketingLogisticsExpressBo bo) {
        LambdaQueryWrapper<MarketingLogisticsExpress> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MarketingLogisticsExpress> buildQueryWrapper(MarketingLogisticsExpressBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MarketingLogisticsExpress> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MarketingLogisticsExpress::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), MarketingLogisticsExpress::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getExpressCode()), MarketingLogisticsExpress::getExpressCode, bo.getExpressCode());
        lqw.eq(bo.getSort() != null, MarketingLogisticsExpress::getSort, bo.getSort());
        return lqw;
    }

    /**
     * 新增营销推广-物流快递
     *
     * @param bo 营销推广-物流快递
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MarketingLogisticsExpressBo bo) {
        MarketingLogisticsExpress add = MapstructUtils.convert(bo, MarketingLogisticsExpress.class);
        validEntityBeforeSave(add);

        //添加数据时查重
        QueryWrapper<MarketingLogisticsExpress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",add.getName());
        if (ObjectUtils.isNotEmpty(baseMapper.selectOne(queryWrapper))) throw new ServiceException("该快递名称已存在");

        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改营销推广-物流快递
     *
     * @param bo 营销推广-物流快递
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MarketingLogisticsExpressBo bo) {
        MarketingLogisticsExpress update = MapstructUtils.convert(bo, MarketingLogisticsExpress.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MarketingLogisticsExpress entity){
        //TODO 做一些数据校验,如唯一约束

        if (entity.getName().length()>16) throw new ServiceException("快递名称过长！");
        if (entity.getExpressCode().length()>33) throw new ServiceException("快递编码过长！");





    }

    /**
     * 校验并批量删除营销推广-物流快递信息
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
