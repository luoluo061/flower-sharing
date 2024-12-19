package org.dromara.system.platform.service.impl;

import org.dromara.common.core.enums.Status;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.platform.domain.query.MerchantInformationQuery;
import org.springframework.stereotype.Service;
import org.dromara.system.platform.domain.bo.MerchantInformationBo;
import org.dromara.system.platform.domain.vo.MerchantInformationVo;
import org.dromara.system.platform.domain.MerchantInformation;
import org.dromara.system.platform.mapper.MerchantInformationMapper;
import org.dromara.system.platform.service.IMerchantInformationService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商户信息Service业务层处理
 *
 * @author Lion Li
 * @date 2024-12-05
 */
@RequiredArgsConstructor
@Service
public class MerchantInformationServiceImpl implements IMerchantInformationService {

    private final MerchantInformationMapper baseMapper;

    /**
     * 查询商户信息
     *
     * @param id 主键
     * @return 商户信息
     */
    @Override
    public MerchantInformation queryById(Long id){
        return baseMapper.selectById(id);
    }

    /**
     * 分页查询商户信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商户信息分页列表
     */
    @Override
    public TableDataInfo<MerchantInformationVo> queryPageList(MerchantInformationQuery query, PageQuery pageQuery) {
        LambdaQueryWrapper<MerchantInformation> lqw = buildQueryWrapper(query);
        Page<MerchantInformationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的商户信息列表
     *
     * @param bo 查询条件
     * @return 商户信息列表
     */
    @Override
    public List<MerchantInformationVo> queryList(MerchantInformationQuery query) {
        LambdaQueryWrapper<MerchantInformation> lqw = buildQueryWrapper(query);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MerchantInformation> buildQueryWrapper(MerchantInformationQuery query) {

        LambdaQueryWrapper<MerchantInformation> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(query.getMerchantName()), MerchantInformation::getMerchantName, query.getMerchantName());
        //lqw.like(StringUtils.isNotBlank(bo.getStoreName()), MerchantInformation::getStoreName, bo.getStoreName());
        lqw.eq(query.getCooperationType() != null, MerchantInformation::getCooperationType, query.getCooperationType());
        lqw.eq(query.getMerchantType() != null, MerchantInformation::getMerchantType, query.getMerchantType());
        lqw.eq(StringUtils.isNotBlank(query.getPhone()), MerchantInformation::getPhone, query.getPhone());
        return lqw;
    }

    /**
     * 新增商户信息
     *
     * @param bo 商户信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MerchantInformationBo bo) {
        MerchantInformation add = MapstructUtils.convert(bo, MerchantInformation.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改商户信息
     *
     * @param bo 商户信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MerchantInformationBo bo) {
        MerchantInformation update = MapstructUtils.convert(bo, MerchantInformation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MerchantInformation entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除商户信息信息
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

    @Override
    public boolean updateStatus(String status, Long id) {
        if(Status.DISABLE.equals(status) || Status.ENABLE.equals(status)) {
            return baseMapper.updateStatus(status, id);
        }
        return false;
    }
}
