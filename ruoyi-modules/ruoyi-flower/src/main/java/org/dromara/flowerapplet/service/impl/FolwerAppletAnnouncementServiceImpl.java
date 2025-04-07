package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flowerapplet.domain.FolwerAppletAnnouncement;
import org.dromara.flowerapplet.domain.bo.FolwerAppletAnnouncementBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletAnnouncementVo;
import org.dromara.flowerapplet.mapper.FolwerAppletAnnouncementMapper;
import org.dromara.flowerapplet.service.IFolwerAppletAnnouncementService;
import org.springframework.stereotype.Service;

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
public class FolwerAppletAnnouncementServiceImpl implements IFolwerAppletAnnouncementService {

    private final FolwerAppletAnnouncementMapper baseMapper;

    /**
     * 查询公告
     *
     * @param announcementId 主键
     * @return 公告
     */
    @Override
    public FolwerAppletAnnouncementVo queryById(Long announcementId){
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
    public TableDataInfo<FolwerAppletAnnouncementVo> queryPageList(FolwerAppletAnnouncementBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletAnnouncement> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletAnnouncementVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的公告列表
     *
     * @param bo 查询条件
     * @return 公告列表
     */
    @Override
    public List<FolwerAppletAnnouncementVo> queryList(FolwerAppletAnnouncementBo bo) {
        LambdaQueryWrapper<FolwerAppletAnnouncement> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletAnnouncement> buildQueryWrapper(FolwerAppletAnnouncementBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletAnnouncement> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), FolwerAppletAnnouncement::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), FolwerAppletAnnouncement::getContent, bo.getContent());
        lqw.eq(bo.getVisits() != null, FolwerAppletAnnouncement::getVisits, bo.getVisits());
        lqw.eq(bo.getStatus() != null, FolwerAppletAnnouncement::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增公告
     *
     * @param bo 公告
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletAnnouncementBo bo) {
        FolwerAppletAnnouncement add = MapstructUtils.convert(bo, FolwerAppletAnnouncement.class);
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
    public Boolean updateByBo(FolwerAppletAnnouncementBo bo) {
        FolwerAppletAnnouncement update = MapstructUtils.convert(bo, FolwerAppletAnnouncement.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletAnnouncement entity){
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
