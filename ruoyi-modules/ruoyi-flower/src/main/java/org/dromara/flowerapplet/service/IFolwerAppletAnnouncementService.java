package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.bo.FolwerAppletAnnouncementBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.flowerapplet.domain.vo.FolwerAppletAnnouncementVo;

import java.util.Collection;
import java.util.List;

/**
 * 公告Service接口
 *
 * @author mlhxj
 * @date 2025-03-31
 */
public interface IFolwerAppletAnnouncementService {

    /**
     * 查询公告
     *
     * @param announcementId 主键
     * @return 公告
     */
    FolwerAppletAnnouncementVo queryById(Long announcementId);

    /**
     * 分页查询公告列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 公告分页列表
     */
    TableDataInfo<FolwerAppletAnnouncementVo> queryPageList(FolwerAppletAnnouncementBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的公告列表
     *
     * @param bo 查询条件
     * @return 公告列表
     */
    List<FolwerAppletAnnouncementVo> queryList(FolwerAppletAnnouncementBo bo);

    /**
     * 新增公告
     *
     * @param bo 公告
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletAnnouncementBo bo);

    /**
     * 修改公告
     *
     * @param bo 公告
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletAnnouncementBo bo);

    /**
     * 校验并批量删除公告信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
