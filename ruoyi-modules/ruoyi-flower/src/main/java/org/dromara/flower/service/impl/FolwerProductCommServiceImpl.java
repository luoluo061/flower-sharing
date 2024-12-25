package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerProductCommBo;
import org.dromara.flower.domain.vo.FolwerProductCommVo;
import org.dromara.flower.domain.FolwerProductComm;
import org.dromara.flower.mapper.FolwerProductCommMapper;
import org.dromara.flower.service.IFolwerProductCommService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商品评价Service业务层处理
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class FolwerProductCommServiceImpl implements IFolwerProductCommService {

    private final FolwerProductCommMapper baseMapper;

    /**
     * 查询商品评价
     *
     * @param prodCommId 主键
     * @return 商品评价
     */
    @Override
    public FolwerProductCommVo queryById(Long prodCommId){
        return baseMapper.selectVoById(prodCommId);
    }

    /**
     * 分页查询商品评价列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品评价分页列表
     */
    @Override
    public TableDataInfo<FolwerProductCommVo> queryPageList(FolwerProductCommBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerProductComm> lqw = buildQueryWrapper(bo);
        Page<FolwerProductCommVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的商品评价列表
     *
     * @param bo 查询条件
     * @return 商品评价列表
     */
    @Override
    public List<FolwerProductCommVo> queryList(FolwerProductCommBo bo) {
        LambdaQueryWrapper<FolwerProductComm> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerProductComm> buildQueryWrapper(FolwerProductCommBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerProductComm> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProdId() != null, FolwerProductComm::getProdId, bo.getProdId());
        lqw.like(StringUtils.isNotBlank(bo.getProdName()), FolwerProductComm::getProdName, bo.getProdName());
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), FolwerProductComm::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerProductComm::getUserName, bo.getUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), FolwerProductComm::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getReplyContent()), FolwerProductComm::getReplyContent, bo.getReplyContent());
        lqw.eq(bo.getRecTime() != null, FolwerProductComm::getRecTime, bo.getRecTime());
        lqw.eq(bo.getReplyTime() != null, FolwerProductComm::getReplyTime, bo.getReplyTime());
        lqw.eq(bo.getScore() != null, FolwerProductComm::getScore, bo.getScore());
        lqw.eq(bo.getIsAnonymous() != null, FolwerProductComm::getIsAnonymous, bo.getIsAnonymous());
        lqw.eq(bo.getStatus() != null, FolwerProductComm::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增商品评价
     *
     * @param bo 商品评价
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerProductCommBo bo) {
        FolwerProductComm add = MapstructUtils.convert(bo, FolwerProductComm.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setProdCommId(add.getProdCommId());
        }
        return flag;
    }

    /**
     * 修改商品评价
     *
     * @param bo 商品评价
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerProductCommBo bo) {
        FolwerProductComm update = MapstructUtils.convert(bo, FolwerProductComm.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerProductComm entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除商品评价信息
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
