package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerCreditOrder;
import org.dromara.system.domain.bo.SysDictDataBo;
import org.dromara.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerCreditGetrecordsBo;
import org.dromara.flower.domain.vo.FolwerCreditGetrecordsVo;
import org.dromara.flower.domain.FolwerCreditGetrecords;
import org.dromara.flower.mapper.FolwerCreditGetrecordsMapper;
import org.dromara.flower.service.IFolwerCreditGetrecordsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分获取记录Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class FolwerCreditGetrecordsServiceImpl implements IFolwerCreditGetrecordsService {

    private final FolwerCreditGetrecordsMapper baseMapper;

    private final ISysDictDataService dictDataService;

    /**
     * 查询积分获取记录
     *
     * @param recordId 主键
     * @return 积分获取记录
     */
    @Override
    public FolwerCreditGetrecordsVo queryById(Long recordId){
        FolwerCreditGetrecordsVo folwerCreditGetrecordsVo = baseMapper.selectVoById(recordId);
        String creditSourName = dictDataService.selectDictLabel("source_points", String.valueOf(folwerCreditGetrecordsVo.getCreditSourId()));
        folwerCreditGetrecordsVo.setCreditSourName(creditSourName);
        return folwerCreditGetrecordsVo;
    }

    /**
     * 分页查询积分获取记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分获取记录分页列表
     */
    @Override
    public TableDataInfo<FolwerCreditGetrecordsVo> queryPageList(FolwerCreditGetrecordsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCreditGetrecords> lqw = buildQueryWrapper(bo);
        Page<FolwerCreditGetrecordsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        result.getRecords().forEach(record -> {
            String creditSourName = dictDataService.selectDictLabel("source_points", String.valueOf(record.getCreditSourId()));
            record.setCreditSourName(creditSourName);
        });
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分获取记录列表
     *
     * @param bo 查询条件
     * @return 积分获取记录列表
     */
    @Override
    public List<FolwerCreditGetrecordsVo> queryList(FolwerCreditGetrecordsBo bo) {
        LambdaQueryWrapper<FolwerCreditGetrecords> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerCreditGetrecords> buildQueryWrapper(FolwerCreditGetrecordsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerCreditGetrecords> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FolwerCreditGetrecords::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerCreditGetrecords::getUserName, bo.getUserName());
        lqw.eq(bo.getCreditSourId() != null, FolwerCreditGetrecords::getCreditSourId, bo.getCreditSourId());
        lqw.eq(bo.getMemberLevelId() != null, FolwerCreditGetrecords::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(StringUtils.isNotBlank(bo.getGetTotal()), FolwerCreditGetrecords::getGetTotal, bo.getGetTotal());
        lqw.eq(bo.getGetTime() != null, FolwerCreditGetrecords::getGetTime, bo.getGetTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerCreditGetrecords::getRemarks, bo.getRemarks());
        lqw.eq(bo.getStatus() != null, FolwerCreditGetrecords::getStatus, bo.getStatus());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerCreditGetrecords::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    /**
     * 新增积分获取记录
     *
     * @param bo 积分获取记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerCreditGetrecordsBo bo) {
        FolwerCreditGetrecords add = MapstructUtils.convert(bo, FolwerCreditGetrecords.class);
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
    public Boolean updateByBo(FolwerCreditGetrecordsBo bo) {
        FolwerCreditGetrecords update = MapstructUtils.convert(bo, FolwerCreditGetrecords.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerCreditGetrecords entity){
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
