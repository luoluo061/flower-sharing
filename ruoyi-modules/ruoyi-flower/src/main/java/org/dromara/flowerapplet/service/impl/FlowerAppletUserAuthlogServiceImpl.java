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
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserAuthlogBo;
import org.dromara.flowerapplet.domain.vo.FlowerAppletUserAuthlogVo;
import org.dromara.flowerapplet.domain.FlowerAppletUserAuthlog;
import org.dromara.flowerapplet.mapper.FlowerAppletUserAuthlogMapper;
import org.dromara.flowerapplet.service.IFlowerAppletUserAuthlogService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 小程序用户信息认证记录Service业务层处理
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@RequiredArgsConstructor
@Service
public class FlowerAppletUserAuthlogServiceImpl implements IFlowerAppletUserAuthlogService {

    private final FlowerAppletUserAuthlogMapper baseMapper;

    /**
     * 查询小程序用户信息认证记录
     *
     * @param authlogId 主键
     * @return 小程序用户信息认证记录
     */
    @Override
    public FlowerAppletUserAuthlogVo queryById(Long authlogId){
        return baseMapper.selectVoById(authlogId);
    }

    /**
     * 分页查询小程序用户信息认证记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息认证记录分页列表
     */
    @Override
    public TableDataInfo<FlowerAppletUserAuthlogVo> queryPageList(FlowerAppletUserAuthlogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerAppletUserAuthlog> lqw = buildQueryWrapper(bo);
        Page<FlowerAppletUserAuthlogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序用户信息认证记录列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息认证记录列表
     */
    @Override
    public List<FlowerAppletUserAuthlogVo> queryList(FlowerAppletUserAuthlogBo bo) {
        LambdaQueryWrapper<FlowerAppletUserAuthlog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowerAppletUserAuthlog> buildQueryWrapper(FlowerAppletUserAuthlogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowerAppletUserAuthlog> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FlowerAppletUserAuthlog::getUserId, bo.getUserId());
        lqw.eq(bo.getAuthId() != null, FlowerAppletUserAuthlog::getAuthId, bo.getAuthId());
        lqw.eq(bo.getStatus() != null, FlowerAppletUserAuthlog::getStatus, bo.getStatus());
        lqw.eq(bo.getIsPass() != null, FlowerAppletUserAuthlog::getIsPass, bo.getIsPass());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FlowerAppletUserAuthlog::getRemarks, bo.getRemarks());
        lqw.eq(bo.getAuthTime() != null, FlowerAppletUserAuthlog::getAuthTime, bo.getAuthTime());
        return lqw;
    }

    /**
     * 新增小程序用户信息认证记录
     *
     * @param bo 小程序用户信息认证记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlowerAppletUserAuthlogBo bo) {
        FlowerAppletUserAuthlog add = MapstructUtils.convert(bo, FlowerAppletUserAuthlog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAuthlogId(add.getAuthlogId());
        }
        return flag;
    }

    /**
     * 修改小程序用户信息认证记录
     *
     * @param bo 小程序用户信息认证记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlowerAppletUserAuthlogBo bo) {
        FlowerAppletUserAuthlog update = MapstructUtils.convert(bo, FlowerAppletUserAuthlog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlowerAppletUserAuthlog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除小程序用户信息认证记录信息
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
