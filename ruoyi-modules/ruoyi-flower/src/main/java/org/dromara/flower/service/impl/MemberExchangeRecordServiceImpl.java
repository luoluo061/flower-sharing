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
import org.dromara.flower.domain.bo.MemberExchangeRecordBo;
import org.dromara.flower.domain.vo.MemberExchangeRecordVo;
import org.dromara.flower.domain.MemberExchangeRecord;
import org.dromara.flower.mapper.MemberExchangeRecordMapper;
import org.dromara.flower.service.IMemberExchangeRecordService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 会员中心--兑换记录Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class MemberExchangeRecordServiceImpl implements IMemberExchangeRecordService {

    private final MemberExchangeRecordMapper baseMapper;
    private final AppletUserInformationMapper appletUserInformationMapper;

    private static Long ZERO = 0L;

    /**
     * 查询会员中心--兑换记录
     *
     * @param id 主键
     * @return 会员中心--兑换记录
     */
    @Override
    public MemberExchangeRecordVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询会员中心--兑换记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员中心--兑换记录分页列表
     */
    @Override
    public TableDataInfo<MemberExchangeRecordVo> queryPageList(MemberExchangeRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MemberExchangeRecord> lqw = buildQueryWrapper(bo);
        Page<MemberExchangeRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员中心--兑换记录列表
     *
     * @param bo 查询条件
     * @return 会员中心--兑换记录列表
     */
    @Override
    public List<MemberExchangeRecordVo> queryList(MemberExchangeRecordBo bo) {
        LambdaQueryWrapper<MemberExchangeRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MemberExchangeRecord> buildQueryWrapper(MemberExchangeRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MemberExchangeRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MemberExchangeRecord::getDeptId, bo.getDeptId());
        lqw.eq(bo.getAmount() != null, MemberExchangeRecord::getAmount, bo.getAmount());
        lqw.eq(bo.getGold() != null, MemberExchangeRecord::getGold, bo.getGold());
        lqw.eq(bo.getBalance() != null, MemberExchangeRecord::getBalance, bo.getBalance());
        lqw.like(StringUtils.isNotBlank(bo.getCreateName()), MemberExchangeRecord::getCreateName, bo.getCreateName());
        return lqw;
    }

    /**
     * 新增会员中心--兑换记录
     *
     * @param bo 会员中心--兑换记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MemberExchangeRecordBo bo) {
        MemberExchangeRecord add = MapstructUtils.convert(bo, MemberExchangeRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改会员中心--兑换记录
     *
     * @param bo 会员中心--兑换记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MemberExchangeRecordBo bo) {
        MemberExchangeRecord update = MapstructUtils.convert(bo, MemberExchangeRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MemberExchangeRecord entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员中心--兑换记录信息
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
    public Boolean exchangeCash(AppletUserInformationBo bo) {
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
        if (app.getGold() == null || app.getGold() <= ZERO) {
            throw new RuntimeException("没有金币，不能兑换");
        }
        // TODO 后期从配置表去积分兑换金币规则
        Long exchange = ZERO;
        if (app.getGold() - bo.getModifiedValue() > 0) {
            exchange = app.getGold() - bo.getModifiedValue();
            app.setGold(exchange);
        } else {
            exchange = bo.getModifiedValue();
            app.setGold(ZERO);
        }
        // 修改金币数
        appletUserInformationMapper.updateById(app);

        // TODO 存入记录 兑换记录 计算金额
        MemberExchangeRecord mer = new MemberExchangeRecord();
        mer.setGold(exchange);
        mer.setCreateName(loginUser.getUsername());
        mer.setAmount(exchange / 10);
        mer.setBalance(app.getGold());
        boolean flag = baseMapper.insert(mer) > 0;
        if (flag){
            return true;
        }
        return false;
    }
}
