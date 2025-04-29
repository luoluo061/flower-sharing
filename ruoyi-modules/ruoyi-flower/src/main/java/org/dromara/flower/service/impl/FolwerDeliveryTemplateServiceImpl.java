package org.dromara.flower.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerDeliveryTemplateBo;
import org.dromara.flower.domain.vo.FolwerDeliveryTemplateVo;
import org.dromara.flower.domain.FolwerDeliveryTemplate;
import org.dromara.flower.mapper.FolwerDeliveryTemplateMapper;
import org.dromara.flower.service.IFolwerDeliveryTemplateService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 运费模板Service业务层处理
 *
 * @author mlhxj
 * @date 2025-04-02
 */
@RequiredArgsConstructor
@Service
public class FolwerDeliveryTemplateServiceImpl implements IFolwerDeliveryTemplateService {

    private final FolwerDeliveryTemplateMapper baseMapper;

    /**
     * 查询运费模板
     *
     * @param tempId 主键
     * @return 运费模板
     */
    @Override
    public FolwerDeliveryTemplateVo queryById(Long tempId){
        FolwerDeliveryTemplateVo folwerDeliveryTemplateVo = baseMapper.selectVoById(tempId);
        if (folwerDeliveryTemplateVo == null){
            return null;
        }
        if (folwerDeliveryTemplateVo.getParentId() == 0L){
            FolwerDeliveryTemplateBo folwerDeliveryTemplateBo = new FolwerDeliveryTemplateBo();
            folwerDeliveryTemplateBo.setParentId(folwerDeliveryTemplateVo.getTempId());
            folwerDeliveryTemplateVo.setChildren(this.queryChildrenList(folwerDeliveryTemplateBo));
        }
        return folwerDeliveryTemplateVo;
    }

    /**
     * 分页查询运费模板列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 运费模板分页列表
     */
    @Override
    public TableDataInfo<FolwerDeliveryTemplateVo> queryPageList(FolwerDeliveryTemplateBo bo, PageQuery pageQuery) {
        bo.setParentId(0L);
        LambdaQueryWrapper<FolwerDeliveryTemplate> lqw = buildQueryWrapper(bo);
        Page<FolwerDeliveryTemplateVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        result.getRecords().forEach(record -> {
            FolwerDeliveryTemplateBo folwerDeliveryTemplateBo = new FolwerDeliveryTemplateBo();
            folwerDeliveryTemplateBo.setParentId(record.getTempId());
            List<FolwerDeliveryTemplateVo> childrenFolwerDeliveryTemplateVos = this.queryChildrenList(folwerDeliveryTemplateBo);
            record.setChildren(childrenFolwerDeliveryTemplateVos);
        });
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的运费模板列表
     *
     * @param bo 查询条件
     * @return 运费模板列表
     */
    @Override
    public List<FolwerDeliveryTemplateVo> queryList(FolwerDeliveryTemplateBo bo) {
        bo.setParentId(0L);
        LambdaQueryWrapper<FolwerDeliveryTemplate> lqw = buildQueryWrapper(bo);
        List<FolwerDeliveryTemplateVo> folwerDeliveryTemplateVos = baseMapper.selectVoList(lqw);
        folwerDeliveryTemplateVos.forEach(folwerDeliveryTemplateVo -> {
            FolwerDeliveryTemplateBo folwerDeliveryTemplateBo = new FolwerDeliveryTemplateBo();
            folwerDeliveryTemplateBo.setParentId(folwerDeliveryTemplateVo.getTempId());
            folwerDeliveryTemplateVo.setChildren(this.queryChildrenList(folwerDeliveryTemplateBo));
        });
        return folwerDeliveryTemplateVos;
    }

    /**
     * 查询符合条件的运费模板子集列表
     *
     * @param bo 查询条件
     * @return 运费模板列表
     */
    @Override
    public List<FolwerDeliveryTemplateVo> queryChildrenList(FolwerDeliveryTemplateBo bo) {
        LambdaQueryWrapper<FolwerDeliveryTemplate> lqw = buildQueryWrapper(bo);
        List<FolwerDeliveryTemplateVo> folwerDeliveryTemplateVos = baseMapper.selectVoList(lqw);
        return folwerDeliveryTemplateVos;
    }

    private LambdaQueryWrapper<FolwerDeliveryTemplate> buildQueryWrapper(FolwerDeliveryTemplateBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerDeliveryTemplate> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, FolwerDeliveryTemplate::getParentId, bo.getParentId());
        lqw.eq(StringUtils.isNotBlank(bo.getTempKey()), FolwerDeliveryTemplate::getTempKey, bo.getTempKey());
        lqw.eq(StringUtils.isNotBlank(bo.getTempValue()), FolwerDeliveryTemplate::getTempValue, bo.getTempValue());
        lqw.eq(bo.getStatus() != null, FolwerDeliveryTemplate::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增运费模板
     *
     * @param bos 运费模板
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBos(List<FolwerDeliveryTemplateBo> bos) {
        if (bos == null || bos.size() == 0) {
            return false;
        }
        FolwerDeliveryTemplateBo bo0 = null;
        for (FolwerDeliveryTemplateBo bo : bos){
            if (bo.getParentId() != null) {
                if (bo.getParentId() == 0L) {
                    bo0 = bo;
                    break;
                }
            }
        }
        FolwerDeliveryTemplate add = MapstructUtils.convert(bo0, FolwerDeliveryTemplate.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo0.setTempId(add.getTempId());

            for (FolwerDeliveryTemplateBo bo : bos){
                if (bo.getParentId() == null){
                    FolwerDeliveryTemplate folwerDeliveryTemplate = BeanUtil.copyProperties(bo, FolwerDeliveryTemplate.class);
                    folwerDeliveryTemplate.setParentId(add.getTempId());
                    baseMapper.insert(folwerDeliveryTemplate);
                }
            }
        }
        return flag;
    }

    /**
     * 新增运费模板
     *
     * @param bos 运费模板
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerDeliveryTemplateBo bo) {
        FolwerDeliveryTemplate add = MapstructUtils.convert(bo, FolwerDeliveryTemplate.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setTempId(add.getTempId());
        }
        return flag;
    }

    /**
     * 修改运费模板
     *
     * @param bos 运费模板
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(List<FolwerDeliveryTemplateBo> bos) {
        if (bos == null || bos.size() == 0) {
            return false;
        }
        boolean b = false;
        for (FolwerDeliveryTemplateBo bo : bos){
            FolwerDeliveryTemplate update = MapstructUtils.convert(bo, FolwerDeliveryTemplate.class);
            validEntityBeforeSave(update);
            b = baseMapper.updateById(update) > 0;
        }
        return b;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerDeliveryTemplate entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除运费模板信息
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
