package org.dromara.flower.service.impl;

import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.flower.domain.bo.FolwerSkuBo;
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.service.IFolwerProductService;
import org.dromara.flower.service.IFolwerSkuService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerDeliveryBoxBo;
import org.dromara.flower.domain.vo.FolwerDeliveryBoxVo;
import org.dromara.flower.domain.FolwerDeliveryBox;
import org.dromara.flower.mapper.FolwerDeliveryBoxMapper;
import org.dromara.flower.service.IFolwerDeliveryBoxService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 物流箱型Service业务层处理
 *
 * @author mlhxj
 * @date 2025-03-29
 */
@RequiredArgsConstructor
@Service
public class FolwerDeliveryBoxServiceImpl implements IFolwerDeliveryBoxService {

    private final FolwerDeliveryBoxMapper baseMapper;

    private final IFolwerSkuService folwerSkuService;

    /**
     * 查询物流箱型
     *
     * @param boxId 主键
     * @return 物流箱型
     */
    @Override
    public FolwerDeliveryBoxVo queryById(Long boxId){
        return baseMapper.selectVoById(boxId);
    }

    /**
     * 分页查询物流箱型列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 物流箱型分页列表
     */
    @Override
    public TableDataInfo<FolwerDeliveryBoxVo> queryPageList(FolwerDeliveryBoxBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerDeliveryBox> lqw = buildQueryWrapper(bo);
        Page<FolwerDeliveryBoxVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的物流箱型列表
     *
     * @param bo 查询条件
     * @return 物流箱型列表
     */
    @Override
    public List<FolwerDeliveryBoxVo> queryList(FolwerDeliveryBoxBo bo) {
        LambdaQueryWrapper<FolwerDeliveryBox> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerDeliveryBox> buildQueryWrapper(FolwerDeliveryBoxBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerDeliveryBox> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getBoxName()), FolwerDeliveryBox::getBoxName, bo.getBoxName());
        lqw.eq(bo.getLength() != null, FolwerDeliveryBox::getLength, bo.getLength());
        lqw.eq(bo.getWidth() != null, FolwerDeliveryBox::getWidth, bo.getWidth());
        lqw.eq(bo.getHeight() != null, FolwerDeliveryBox::getHeight, bo.getHeight());
        lqw.eq(bo.getVolume() != null, FolwerDeliveryBox::getVolume, bo.getVolume());
        lqw.eq(bo.getCostPrice() != null, FolwerDeliveryBox::getCostPrice, bo.getCostPrice());
        lqw.eq(bo.getPackagPrice() != null, FolwerDeliveryBox::getPackagPrice, bo.getPackagPrice());
        lqw.eq(bo.getStatus() != null, FolwerDeliveryBox::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增物流箱型
     *
     * @param bo 物流箱型
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerDeliveryBoxBo bo) {
        FolwerDeliveryBox add = MapstructUtils.convert(bo, FolwerDeliveryBox.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setBoxId(add.getBoxId());
        }
        return flag;
    }

    /**
     * 修改物流箱型
     *
     * @param bo 物流箱型
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerDeliveryBoxBo bo) {
        FolwerDeliveryBox update = MapstructUtils.convert(bo, FolwerDeliveryBox.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerDeliveryBox entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除物流箱型信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public R<List<FolwerSkuVo>> deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        if (ids.isEmpty()){
            return R.fail("物流箱型ID为空");
        }
        Long boxId = ids.iterator().next();
        FolwerSkuBo bo = new FolwerSkuBo();
        bo.setBoxId(boxId);
        bo.setStatus(1L);
        List<FolwerSkuVo> folwerSkuVos = folwerSkuService.queryList(bo);
        if (folwerSkuVos.size() > 0){
            return R.ok(folwerSkuVos);
        }
        else {
            boolean b = baseMapper.deleteByIds(ids) > 0;
            if (b){
                return R.ok("删除成功！");
            }
        }
        return null;
    }
}
