package org.dromara.flower.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.mapper.MarketingMemberPromotionPlanMapper;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.MarketingMemberPromotionPecordBo;
import org.dromara.flower.domain.vo.MarketingMemberPromotionPecordVo;
import org.dromara.flower.domain.MarketingMemberPromotionPecord;
import org.dromara.flower.mapper.MarketingMemberPromotionPecordMapper;
import org.dromara.flower.service.IMarketingMemberPromotionPecordService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 会员推广记录Service业务层处理
 *
 * @author chy
 * @date 2024-12-31
 */
@RequiredArgsConstructor
@Service
public class MarketingMemberPromotionPecordServiceImpl implements IMarketingMemberPromotionPecordService {

    private final MarketingMemberPromotionPecordMapper baseMapper;

    private final MarketingMemberPromotionPlanMapper basePlanMapper;

    /**
     * 查询会员推广记录
     *
     * @param id 主键
     * @return 会员推广记录
     */
    @Override
    public MarketingMemberPromotionPecordVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询会员推广记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员推广记录分页列表
     */
    @Override
    public TableDataInfo<MarketingMemberPromotionPecordVo> queryPageList(MarketingMemberPromotionPecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MarketingMemberPromotionPecord> lqw = buildQueryWrapper(bo);
        Page<MarketingMemberPromotionPecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员推广记录列表
     *
     * @param bo 查询条件
     * @return 会员推广记录列表
     */
    @Override
    public List<MarketingMemberPromotionPecordVo> queryList(MarketingMemberPromotionPecordBo bo) {
        LambdaQueryWrapper<MarketingMemberPromotionPecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MarketingMemberPromotionPecord> buildQueryWrapper(MarketingMemberPromotionPecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MarketingMemberPromotionPecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MarketingMemberPromotionPecord::getDeptId, bo.getDeptId());
        lqw.eq(bo.getPromotionId() != null, MarketingMemberPromotionPecord::getPromotionId, bo.getPromotionId());
        lqw.eq(StringUtils.isNotBlank(bo.getMemberId()), MarketingMemberPromotionPecord::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getMemberName()), MarketingMemberPromotionPecord::getMemberName, bo.getMemberName());
        lqw.eq(bo.getPromotedPersonId() != null, MarketingMemberPromotionPecord::getPromotedPersonId, bo.getPromotedPersonId());
        lqw.like(StringUtils.isNotBlank(bo.getPromotedPersonName()), MarketingMemberPromotionPecord::getPromotedPersonName, bo.getPromotedPersonName());
        lqw.eq(bo.getPromotedPersonStatus() != null, MarketingMemberPromotionPecord::getPromotedPersonStatus, bo.getPromotedPersonStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getPromotedPersonLevel()), MarketingMemberPromotionPecord::getPromotedPersonLevel, bo.getPromotedPersonLevel());
        lqw.eq(bo.getRewardSetting() != null, MarketingMemberPromotionPecord::getRewardSetting, bo.getRewardSetting());
        lqw.eq(bo.getCreatedAt() != null, MarketingMemberPromotionPecord::getCreatedAt, bo.getCreatedAt());
        return lqw;
    }

    /**
     * 新增会员推广记录
     *
     * @param bo 会员推广记录
     * @return 是否新增成功
     */
    @Override
    @Transactional
    public Boolean insertByBo(MarketingMemberPromotionPecordBo bo) {
        MarketingMemberPromotionPecord add = MapstructUtils.convert(bo, MarketingMemberPromotionPecord.class);
        validEntityBeforeSave(add);
        //1. 设置推广记录编号
        add.setPromotionId(String.valueOf(IdUtil.getSnowflakeNextId()));

        // 设置默认值
        // 2. 设置  “被推销人” 是否充值会员默认状态
        add.setPromotedPersonStatus(0L);
        // 设置 “被推广人” 默认等级
        /*add.setPromotedPersonLevel("v1");*/

        //3. 设置被 推广人默认购买会员金额
        add.setPromoterAmount(new BigDecimal(0.0));
        //4. 设置被 推广人默认消费金额
        add.setConsumptionAmount(new BigDecimal(0.0));
        //5. 设置 默认购物返点
        add.setShoppingRebate(new BigDecimal(0.0));
        //6. 设置 默认 推广返现小计
        add.setPromotionCashback(new BigDecimal(0.0));
        //7. 设置默认的 是否奖励
        add.setRewardSetting(0L);








        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;



    }

    /**
     * 修改会员推广记录
     *
     * @param bo 会员推广记录
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public Boolean updateByBo(MarketingMemberPromotionPecordBo bo) {
        MarketingMemberPromotionPecord update = MapstructUtils.convert(bo, MarketingMemberPromotionPecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MarketingMemberPromotionPecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员推广记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    @Transactional
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }


    /**
     * 判断该用户是否已经是被推荐人了
     * @param id
     * @return
     */
    @Override
    public MarketingMemberPromotionPecordVo getPromoted(Long id) {
        QueryWrapper<MarketingMemberPromotionPecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("promoted_person_id",id);

        return baseMapper.selectVoOne(queryWrapper);
    }







}
