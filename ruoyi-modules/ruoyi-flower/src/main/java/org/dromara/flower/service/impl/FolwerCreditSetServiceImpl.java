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
import org.dromara.flower.domain.bo.FolwerCreditSetBo;
import org.dromara.flower.domain.vo.FolwerCreditSetVo;
import org.dromara.flower.domain.FolwerCreditSet;
import org.dromara.flower.mapper.FolwerCreditSetMapper;
import org.dromara.flower.service.IFolwerCreditSetService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分配置Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class FolwerCreditSetServiceImpl implements IFolwerCreditSetService {

    private final FolwerCreditSetMapper baseMapper;

    /**
     * 查询积分配置
     *
     * @param id 主键
     * @return 积分配置
     */
    @Override
    public FolwerCreditSetVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询积分配置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分配置分页列表
     */
    @Override
    public TableDataInfo<FolwerCreditSetVo> queryPageList(FolwerCreditSetBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCreditSet> lqw = buildQueryWrapper(bo);
        Page<FolwerCreditSetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分配置列表
     *
     * @param bo 查询条件
     * @return 积分配置列表
     */
    @Override
    public List<FolwerCreditSetVo> queryList(FolwerCreditSetBo bo) {
        LambdaQueryWrapper<FolwerCreditSet> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerCreditSet> buildQueryWrapper(FolwerCreditSetBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerCreditSet> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getGoodsPurchase() != null, FolwerCreditSet::getGoodsPurchase, bo.getGoodsPurchase());
        lqw.eq(bo.getGoodsCredit() != null, FolwerCreditSet::getGoodsCredit, bo.getGoodsCredit());
        lqw.eq(bo.getMemberPurchase() != null, FolwerCreditSet::getMemberPurchase, bo.getMemberPurchase());
        lqw.eq(bo.getMemberCredit() != null, FolwerCreditSet::getMemberCredit, bo.getMemberCredit());
        lqw.eq(bo.getLoginCredit() != null, FolwerCreditSet::getLoginCredit, bo.getLoginCredit());
        lqw.eq(bo.getInviteCredit() != null, FolwerCreditSet::getInviteCredit, bo.getInviteCredit());
        lqw.eq(bo.getGoldcoin() != null, FolwerCreditSet::getGoldcoin, bo.getGoldcoin());
        lqw.eq(bo.getGoldcoinCredit() != null, FolwerCreditSet::getGoldcoinCredit, bo.getGoldcoinCredit());
        lqw.eq(bo.getGoldcoinCash() != null, FolwerCreditSet::getGoldcoinCash, bo.getGoldcoinCash());
        lqw.eq(bo.getCashIn() != null, FolwerCreditSet::getCashIn, bo.getCashIn());
        lqw.eq(bo.getStatus() != null, FolwerCreditSet::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增积分配置
     *
     * @param bo 积分配置
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerCreditSetBo bo) {
        FolwerCreditSet add = MapstructUtils.convert(bo, FolwerCreditSet.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改积分配置
     *
     * @param bo 积分配置
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerCreditSetBo bo) {
        FolwerCreditSet update = MapstructUtils.convert(bo, FolwerCreditSet.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerCreditSet entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分配置信息
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
