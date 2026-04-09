package org.dromara.flower.service.impl;

import cn.hutool.core.util.ObjectUtil;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.MemberPointsExchangeGoldBo;
import org.dromara.flower.domain.vo.MemberPointsExchangeGoldVo;
import org.dromara.flower.domain.MemberPointsExchangeGold;
import org.dromara.flower.mapper.MemberPointsExchangeGoldMapper;
import org.dromara.flower.service.IMemberPointsExchangeGoldService;
import org.dromara.flower.service.domain.PointsAssetDomainService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 会员中心--积分兑换为金币Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class MemberPointsExchangeGoldServiceImpl implements IMemberPointsExchangeGoldService {

    private final MemberPointsExchangeGoldMapper baseMapper;
    private final AppletUserInformationMapper appletUserInformationMapper;
    private final PointsAssetDomainService pointsAssetDomainService;

    private static Long ZERO = 0L;

    /**
     * 查询会员中心--积分兑换为金币
     *
     * @param id 主键
     * @return 会员中心--积分兑换为金币
     */
    @Override
    public MemberPointsExchangeGoldVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询会员中心--积分兑换为金币列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员中心--积分兑换为金币分页列表
     */
    @Override
    public TableDataInfo<MemberPointsExchangeGoldVo> queryPageList(MemberPointsExchangeGoldBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MemberPointsExchangeGold> lqw = buildQueryWrapper(bo);
        Page<MemberPointsExchangeGoldVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员中心--积分兑换为金币列表
     *
     * @param bo 查询条件
     * @return 会员中心--积分兑换为金币列表
     */
    @Override
    public List<MemberPointsExchangeGoldVo> queryList(MemberPointsExchangeGoldBo bo) {
        LambdaQueryWrapper<MemberPointsExchangeGold> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MemberPointsExchangeGold> buildQueryWrapper(MemberPointsExchangeGoldBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MemberPointsExchangeGold> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MemberPointsExchangeGold::getDeptId, bo.getDeptId());
        lqw.eq(bo.getPoints() != null, MemberPointsExchangeGold::getPoints, bo.getPoints());
        lqw.eq(bo.getGold() != null, MemberPointsExchangeGold::getGold, bo.getGold());
        lqw.eq(bo.getBalance() != null, MemberPointsExchangeGold::getBalance, bo.getBalance());
        lqw.like(StringUtils.isNotBlank(bo.getCreateName()), MemberPointsExchangeGold::getCreateName, bo.getCreateName());
        return lqw;
    }

    /**
     * 新增会员中心--积分兑换为金币
     *
     * @param bo 会员中心--积分兑换为金币
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MemberPointsExchangeGoldBo bo) {
        // 查询
        MemberPointsExchangeGold add = MapstructUtils.convert(bo, MemberPointsExchangeGold.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改会员中心--积分兑换为金币
     *
     * @param bo 会员中心--积分兑换为金币
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MemberPointsExchangeGoldBo bo) {
        MemberPointsExchangeGold update = MapstructUtils.convert(bo, MemberPointsExchangeGold.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MemberPointsExchangeGold entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员中心--积分兑换为金币信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    @Transactional
    public Boolean exchangeGoldByBo(AppletUserInformationBo bo) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (ObjectUtil.isEmpty(loginUser)) {
            throw new RuntimeException("请先登录，再兑换");
        }
        if (bo.getModifiedValue() == null || bo.getModifiedValue() < ZERO) {
            return false;
        }
        AppletUserInformation app = appletUserInformationMapper.selectById(bo.getUserId());
        // 对象不存在报错
        if (ObjectUtil.isEmpty(app)) {
            throw new RuntimeException("数据不存在");
        }
        // 金币为null或0也不允许兑换
        if (app.getPoints() == null || app.getPoints() <= ZERO) {
            throw new RuntimeException("没有积分，不能兑换");
        }
        // TODO 后期从配置表去积分兑换金币规则
        Long exchange = pointsAssetDomainService.resolveExchangePoints(app.getPoints(), bo.getModifiedValue());
        pointsAssetDomainService.applyPointsExchange(app, bo.getModifiedValue());
        appletUserInformationMapper.updateById(app);
        MemberPointsExchangeGold pxg = pointsAssetDomainService.preparePointsExchangeRecord(bo.getPoints(), exchange, loginUser.getUsername());
        boolean flag = baseMapper.insert(pxg) > 0;
        return flag;
    }
}
