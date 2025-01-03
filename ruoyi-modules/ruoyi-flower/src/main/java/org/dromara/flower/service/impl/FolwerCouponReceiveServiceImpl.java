package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerCouponReceiveBo;
import org.dromara.flower.domain.vo.FolwerCouponReceiveVo;
import org.dromara.flower.domain.FolwerCouponReceive;
import org.dromara.flower.mapper.FolwerCouponReceiveMapper;
import org.dromara.flower.service.IFolwerCouponReceiveService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 优惠券领取记录Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@RequiredArgsConstructor
@Service
public class FolwerCouponReceiveServiceImpl implements IFolwerCouponReceiveService {

    private final FolwerCouponReceiveMapper baseMapper;

    private final ISysOssService sysOssService;

    /**
     * 查询优惠券领取记录
     *
     * @param id 主键
     * @return 优惠券领取记录
     */
    @Override
    public FolwerCouponReceiveVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询优惠券领取记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠券领取记录分页列表
     */
    @Override
    public TableDataInfo<FolwerCouponReceiveVo> queryPageList(FolwerCouponReceiveBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCouponReceive> lqw = buildQueryWrapper(bo);
        Page<FolwerCouponReceiveVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);

        List<Long> longList = new ArrayList<>();
        result.getRecords().forEach(record ->{
            if (record.getIcon() != null && !record.getIcon().isEmpty()){
                longList.add(Long.valueOf(record.getIcon()));
            }
        });

        if (!longList.isEmpty()){
            Map<String, String> longStringMap = sysOssService.listUrlByIds(longList);
            if (!longStringMap.isEmpty()){
                // 设置图片Url
                result.getRecords().forEach(record ->
                    record.setIconUrl(longStringMap.get(record.getIcon()))
                );
            }
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的优惠券领取记录列表
     *
     * @param bo 查询条件
     * @return 优惠券领取记录列表
     */
    @Override
    public List<FolwerCouponReceiveVo> queryList(FolwerCouponReceiveBo bo) {
        LambdaQueryWrapper<FolwerCouponReceive> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerCouponReceive> buildQueryWrapper(FolwerCouponReceiveBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerCouponReceive> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCouponId() != null, FolwerCouponReceive::getCouponId, bo.getCouponId());
        lqw.eq(bo.getUserId() != null, FolwerCouponReceive::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerCouponReceive::getUserName, bo.getUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), FolwerCouponReceive::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), FolwerCouponReceive::getIcon, bo.getIcon());
        lqw.eq(bo.getStatus() != null, FolwerCouponReceive::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增优惠券领取记录
     *
     * @param bo 优惠券领取记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerCouponReceiveBo bo) {
        FolwerCouponReceive add = MapstructUtils.convert(bo, FolwerCouponReceive.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改优惠券领取记录
     *
     * @param bo 优惠券领取记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerCouponReceiveBo bo) {
        FolwerCouponReceive update = MapstructUtils.convert(bo, FolwerCouponReceive.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerCouponReceive entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除优惠券领取记录信息
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
