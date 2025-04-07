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
import org.dromara.flower.domain.bo.FolwerAnnouncementBo;
import org.dromara.flower.domain.vo.FolwerAnnouncementVo;
import org.dromara.flower.domain.FolwerAnnouncement;
import org.dromara.flower.mapper.FolwerAnnouncementMapper;
import org.dromara.flower.service.IFolwerAnnouncementService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 公告Service业务层处理
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@RequiredArgsConstructor
@Service
public class FolwerAnnouncementServiceImpl implements IFolwerAnnouncementService {

    private final FolwerAnnouncementMapper baseMapper;

    /**
     * 查询公告
     *
     * @param announcementId 主键
     * @return 公告
     */
    @Override
    public FolwerAnnouncementVo queryById(Long announcementId){
        return baseMapper.selectVoById(announcementId);
    }

    /**
     * 分页查询公告列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 公告分页列表
     */
    @Override
    public TableDataInfo<FolwerAnnouncementVo> queryPageList(FolwerAnnouncementBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAnnouncement> lqw = buildQueryWrapper(bo);
        Page<FolwerAnnouncementVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的公告列表
     *
     * @param bo 查询条件
     * @return 公告列表
     */
    @Override
    public List<FolwerAnnouncementVo> queryList(FolwerAnnouncementBo bo) {
        LambdaQueryWrapper<FolwerAnnouncement> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAnnouncement> buildQueryWrapper(FolwerAnnouncementBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAnnouncement> lqw = Wrappers.lambdaQuery();
//        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), FolwerAnnouncement::getTitle, bo.getTitle());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), FolwerAnnouncement::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), FolwerAnnouncement::getContent, bo.getContent());
        lqw.eq(bo.getVisits() != null, FolwerAnnouncement::getVisits, bo.getVisits());
        lqw.eq(bo.getStatus() != null, FolwerAnnouncement::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增公告
     *
     * @param bo 公告
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAnnouncementBo bo) {
        FolwerAnnouncement add = MapstructUtils.convert(bo, FolwerAnnouncement.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAnnouncementId(add.getAnnouncementId());
        }
        return flag;
    }

    /**
     * 修改公告
     *
     * @param bo 公告
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAnnouncementBo bo) {
        FolwerAnnouncement update = MapstructUtils.convert(bo, FolwerAnnouncement.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAnnouncement entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除公告信息
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
