package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.flower.mapper.MemberLevelMapper;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flowerapplet.domain.FlowerAppletFriendsCommunity;
import org.dromara.flowerapplet.domain.bo.FlowerAppletFriendsCommunityBo;
import org.dromara.flowerapplet.domain.vo.FlowerAppletFriendsCommunityCommentVo;
import org.dromara.flowerapplet.domain.vo.FlowerAppletFriendsCommunityVo;
import org.dromara.flowerapplet.mapper.FlowerAppletFriendsCommunityCommentMapper;
import org.dromara.flowerapplet.mapper.FlowerAppletFriendsCommunityMapper;
import org.dromara.flowerapplet.service.IFlowerAppletFriendsCommunityService;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 花友圈Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-30
 */
@RequiredArgsConstructor
@Service
public class FlowerAppletFriendsCommunityServiceImpl implements IFlowerAppletFriendsCommunityService {

    private final FlowerAppletFriendsCommunityMapper baseMapper;
    private final MemberLevelMapper memberLevelMapper;
    private final ISysOssService iSysOssService;
    private final FlowerAppletFriendsCommunityCommentMapper communityCommentMapper;
    private final AppletUserInformationMapper appletUserInformationMapper;


    /**
     * 查询花友圈
     *
     * @param id 主键
     * @return 花友圈
     */
    @Override
    public FlowerAppletFriendsCommunityVo queryById(Long id){
        FlowerAppletFriendsCommunityVo vo = baseMapper.selectVoById(id);
        if (vo != null && vo.getVideoImagesIds() != null){
            List<Long> idList = convertToLongList(vo.getVideoImagesIds());
            Map<String, String> url = iSysOssService.listUrlByIds(idList);
            if (!url.isEmpty()){
                vo.setVideoImagesUrl(url.values().stream().toList());
            }
        }
        // 查询用户的头像url
        if (vo != null && vo.getMemberId() != null){
            String avatarUrl = appletUserInformationMapper.getUserAvatarUrlByMemberId(vo.getMemberId());
            vo.setUrl(avatarUrl);
        }
        // 查询评论详情
        if (vo != null){
            vo.setCommentVos(this.getCommentById(id));
        }
        return vo;
    }

    /**
     * 分页查询花友圈列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 花友圈分页列表
     */
    @Override
    public TableDataInfo<FlowerAppletFriendsCommunityVo> queryPageList(FlowerAppletFriendsCommunityBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerAppletFriendsCommunity> lqw = buildQueryWrapper(bo);
        Page<FlowerAppletFriendsCommunityVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (!result.getRecords().isEmpty()){
            List<Long> list = result.getRecords().stream()
                .filter(Objects::nonNull) // 过滤掉 null 值
                .map(FlowerAppletFriendsCommunityVo::getGrade)
                .distinct()
                .filter(grade -> {
                    try {
                        Long.parseLong(grade); // 尝试转换为 Long
                        return true; // 转换成功，保留
                    } catch (NumberFormatException e) {
                        return false; // 转换失败，过滤掉
                    }
                })
                .map(Long::parseLong) // 转换为 Long
                .toList();
            // 查询会员等级 中文
            MapResultHandler<Long,String> resultHandler = new MapResultHandler<>();
            if (!list.isEmpty()){
                memberLevelMapper.selectMapByIds(resultHandler,list);
            }
            Map<Long,String> map = resultHandler.getMappedResults();
            if (!map.isEmpty()){
                result.getRecords().forEach(v -> {
                    if (v.getGrade() != null && map.containsKey(Long.parseLong(v.getGrade()))) {
                        v.setGradeName(map.get(Long.parseLong(v.getGrade())));
                    }
                });
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的花友圈列表
     *
     * @param bo 查询条件
     * @return 花友圈列表
     */
    @Override
    public List<FlowerAppletFriendsCommunityVo> queryList(FlowerAppletFriendsCommunityBo bo) {
        LambdaQueryWrapper<FlowerAppletFriendsCommunity> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowerAppletFriendsCommunity> buildQueryWrapper(FlowerAppletFriendsCommunityBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowerAppletFriendsCommunity> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, FlowerAppletFriendsCommunity::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), FlowerAppletFriendsCommunity::getTitle, bo.getTitle());
        lqw.eq(bo.getType() != null, FlowerAppletFriendsCommunity::getType, bo.getType());
        lqw.like(StringUtils.isNotBlank(bo.getMemberId()), FlowerAppletFriendsCommunity::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getMemberName()), FlowerAppletFriendsCommunity::getMemberName, bo.getMemberName());
        lqw.eq(StringUtils.isNotBlank(bo.getGrade()), FlowerAppletFriendsCommunity::getGrade, bo.getGrade());
        lqw.eq(bo.getPageView() != null, FlowerAppletFriendsCommunity::getPageView, bo.getPageView());
        lqw.eq(bo.getLikes() != null, FlowerAppletFriendsCommunity::getLikes, bo.getLikes());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), FlowerAppletFriendsCommunity::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getVideoImagesIds()), FlowerAppletFriendsCommunity::getVideoImagesIds, bo.getVideoImagesIds());
        lqw.eq(bo.getStatus() != null, FlowerAppletFriendsCommunity::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增花友圈
     *
     * @param bo 花友圈
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlowerAppletFriendsCommunityBo bo) {
        FlowerAppletFriendsCommunity add = MapstructUtils.convert(bo, FlowerAppletFriendsCommunity.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改花友圈
     *
     * @param bo 花友圈
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FlowerAppletFriendsCommunityBo bo) {
        FlowerAppletFriendsCommunity update = MapstructUtils.convert(bo, FlowerAppletFriendsCommunity.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlowerAppletFriendsCommunity entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除花友圈信息
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
    public List<FlowerAppletFriendsCommunityCommentVo> getCommentById(Long communityId) {
        List<FlowerAppletFriendsCommunityCommentVo> list = communityCommentMapper.selectVoListByCommunityId(communityId);
        if (!list.isEmpty()){
            list = buildTree(list);
        }
        return list;
    }

    public static List<Long> convertToLongList(String ids) {
        if (ids == null || ids.trim().isEmpty()) {
            return List.of();  // 返回空列表
        }

        return List.of(ids.split(","))  // 将字符串按逗号分割为数组
            .stream()            // 转换为 Stream
            .map(String::trim)   // 去除空格
            .map(Long::valueOf)
            .distinct()
            .collect(Collectors.toList());  // 收集为 List<Long>
    }

    public static List<FlowerAppletFriendsCommunityCommentVo> buildTree(List<FlowerAppletFriendsCommunityCommentVo> nodes) {
        // 存储所有节点的 Map，key 是节点 ID，value 是节点对象
        Map<Long, FlowerAppletFriendsCommunityCommentVo> nodeMap = new HashMap<>();
        // 存储根节点
        List<FlowerAppletFriendsCommunityCommentVo> roots = new ArrayList<>();

        // 1. 将所有节点放入 nodeMap
        for (FlowerAppletFriendsCommunityCommentVo node : nodes) {
            nodeMap.put(node.getId(), node);
        }

        // 2. 遍历节点，根据 parentId 将子节点加入父节点的 child 列表
        for (FlowerAppletFriendsCommunityCommentVo node : nodes) {
            if (node.getParentId() == null || node.getParentId() == 0) {
                // 如果 parentId 为 null 或 0，表示是根节点
                roots.add(node);
            } else {
                // 找到父节点并加入 child 列表
                FlowerAppletFriendsCommunityCommentVo parent = nodeMap.get(node.getParentId());
                if (parent != null) {
                    parent.getReplies().add(node);
                }
            }
        }

        return roots;
    }
}
