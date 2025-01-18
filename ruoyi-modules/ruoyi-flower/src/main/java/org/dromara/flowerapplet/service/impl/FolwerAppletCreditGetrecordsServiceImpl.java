package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditGetrecordsBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditGetrecordsVo;
import org.dromara.flowerapplet.domain.FolwerAppletCreditGetrecords;
import org.dromara.flowerapplet.mapper.FolwerAppletCreditGetrecordsMapper;
import org.dromara.flowerapplet.service.IFolwerAppletCreditGetrecordsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分获取记录Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-17
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletCreditGetrecordsServiceImpl implements IFolwerAppletCreditGetrecordsService {

    private final FolwerAppletCreditGetrecordsMapper baseMapper;

    private final ISysDictDataService dictDataService;

    /**
     * 查询积分获取记录
     *
     * @param recordId 主键
     * @return 积分获取记录
     */
    @Override
    public FolwerAppletCreditGetrecordsVo queryById(Long recordId){

        FolwerAppletCreditGetrecordsVo folwerAppletCreditGetrecordsVo = baseMapper.selectVoById(recordId);
        String creditSourName = dictDataService.selectDictLabel("source_points", String.valueOf(folwerAppletCreditGetrecordsVo.getCreditSourId()));
        folwerAppletCreditGetrecordsVo.setCreditSourName(creditSourName);

        return folwerAppletCreditGetrecordsVo;
    }

    /**
     * 分页查询积分获取记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分获取记录分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletCreditGetrecordsVo> queryPageList(FolwerAppletCreditGetrecordsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletCreditGetrecords> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletCreditGetrecordsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if(result != null){
            result.getRecords().forEach(record -> {
                String creditSourName = dictDataService.selectDictLabel("source_points", String.valueOf(record.getCreditSourId()));
                record.setCreditSourName(creditSourName);
            });
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分获取记录列表
     *
     * @param bo 查询条件
     * @return 积分获取记录列表
     */
    @Override
    public List<FolwerAppletCreditGetrecordsVo> queryList(FolwerAppletCreditGetrecordsBo bo) {
        LambdaQueryWrapper<FolwerAppletCreditGetrecords> lqw = buildQueryWrapper(bo);
        List<FolwerAppletCreditGetrecordsVo> folwerAppletCreditGetrecordsVos = baseMapper.selectVoList(lqw);
        if(folwerAppletCreditGetrecordsVos != null){
            folwerAppletCreditGetrecordsVos.forEach(record -> {
                String creditSourName = dictDataService.selectDictLabel("source_points", String.valueOf(record.getCreditSourId()));
                record.setCreditSourName(creditSourName);
            });
        }
        return folwerAppletCreditGetrecordsVos;
    }

    private LambdaQueryWrapper<FolwerAppletCreditGetrecords> buildQueryWrapper(FolwerAppletCreditGetrecordsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletCreditGetrecords> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FolwerAppletCreditGetrecords::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerAppletCreditGetrecords::getUserName, bo.getUserName());
        lqw.eq(bo.getMemberLevelId() != null, FolwerAppletCreditGetrecords::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(bo.getCreditSourId() != null, FolwerAppletCreditGetrecords::getCreditSourId, bo.getCreditSourId());
        lqw.like(StringUtils.isNotBlank(bo.getCreditSourName()), FolwerAppletCreditGetrecords::getCreditSourName, bo.getCreditSourName());
        lqw.eq(StringUtils.isNotBlank(bo.getGetTotal()), FolwerAppletCreditGetrecords::getGetTotal, bo.getGetTotal());
        lqw.eq(bo.getGetTime() != null, FolwerAppletCreditGetrecords::getGetTime, bo.getGetTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerAppletCreditGetrecords::getRemarks, bo.getRemarks());
        lqw.eq(bo.getStatus() != null, FolwerAppletCreditGetrecords::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增积分获取记录
     *
     * @param bo 积分获取记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletCreditGetrecordsBo bo) {
        FolwerAppletCreditGetrecords add = MapstructUtils.convert(bo, FolwerAppletCreditGetrecords.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRecordId(add.getRecordId());
        }
        return flag;
    }

    /**
     * 修改积分获取记录
     *
     * @param bo 积分获取记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletCreditGetrecordsBo bo) {
        FolwerAppletCreditGetrecords update = MapstructUtils.convert(bo, FolwerAppletCreditGetrecords.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletCreditGetrecords entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分获取记录信息
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
