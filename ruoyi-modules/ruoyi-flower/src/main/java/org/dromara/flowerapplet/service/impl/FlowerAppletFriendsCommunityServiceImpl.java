package org.dromara.flowerapplet.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.FlowerFriendsCommunity;
import org.dromara.flower.domain.bo.FlowerFriendsCommunityBo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityCommentVo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityVo;
import org.dromara.flower.mapper.FlowerFriendsCommunityCommentMapper;
import org.dromara.flower.mapper.FlowerFriendsCommunityLikeMapper;
import org.dromara.flower.mapper.FlowerFriendsCommunityMapper;
import org.dromara.flower.mapper.MemberLevelMapper;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flowerapplet.service.IFlowerAppletFriendsCommunityService;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.mapper.SysOssMapper;
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

    private final FlowerFriendsCommunityMapper baseMapper;
    private final MemberLevelMapper memberLevelMapper;
    private final ISysOssService iSysOssService;
    private final FlowerFriendsCommunityCommentMapper communityCommentMapper;
    private final AppletUserInformationMapper appletUserInformationMapper;
    private final SysOssMapper sysOssMapper;
    private final FlowerFriendsCommunityLikeMapper flowerFriendsCommunityLikeMapper;


    /**
     * 查询花友圈
     *
     * @param id 主键
     * @return 花友圈
     */
    @Override
    public FlowerFriendsCommunityVo queryById(Long id){
        FlowerFriendsCommunityVo vo = baseMapper.selectVoById(id);
        if (vo != null && vo.getVideoImagesIds() != null){
            List<Long> idList = convertToLongList(vo.getVideoImagesIds());
            Map<String, String> url = iSysOssService.listUrlByIds(idList);
            if (!url.isEmpty()){
                vo.setVideoImagesUrl(url.values().stream().toList());
            }
        }
        // 查询用户的头像url
        if (vo != null && vo.getMemberId() != null){
            AppletUserInformationVo aui = appletUserInformationMapper.getNameAndAvatarUrlVoById(vo.getCreateBy());
            if (aui != null){
                vo.setUrl(aui.getAvatarUrlUrl());
                vo.setMemberName(aui.getName());
            }

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
    public TableDataInfo<FlowerFriendsCommunityVo> queryPageList(FlowerFriendsCommunityBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerFriendsCommunity> lqw = buildQueryWrapper(bo);
        Page<FlowerFriendsCommunityVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        // 查询人员头像，中文名称信息
        if (!result.getRecords().isEmpty()){
            List<Long> createByIds = result.getRecords().stream()
                .map(FlowerFriendsCommunityVo::getCreateBy)
                .filter(Objects::nonNull)
                .toList();
            if (!createByIds.isEmpty()){
                List<AppletUserInformationVo> aui = appletUserInformationMapper.selectUserInfoByIds(createByIds);
                setNameAndUrl(result,aui);
            }
            result.getRecords().forEach(v->{
                if (Objects.nonNull(v.getVideoImagesIds())) {
                    List<Long> list = Arrays.stream(v.getVideoImagesIds().split(","))
                        .filter(Objects::nonNull)
                        .map(Long::valueOf) // 将每个字符串元素转换为 Long 类型
                        .distinct()
                        .collect(Collectors.toList());
                    List<String> sysOssVos = sysOssMapper.selectUrlByIdCreateTimeAsc(list);
                    v.setVideoImagesUrl(sysOssVos);
                }
            });
            // 是否点赞数据查询
            LoginUser loginUser = LoginHelper.getLoginUser();
            List<Long> isLike = result.getRecords().stream()
                .map(FlowerFriendsCommunityVo::getId)
                .distinct()
                .toList();
            List<Long> likes = flowerFriendsCommunityLikeMapper.selectVoListByIdsAndUserId(isLike,loginUser.getUserId());
            if (!likes.isEmpty()){
                result.getRecords().forEach(v->{
                    if (likes.contains(v.getId())){
                        v.setIsLike(1);
                    }
                });
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 设置用户的头像url和名称
     * @param records
     * @param auis
     */
    private void setNameAndUrl(Page<FlowerFriendsCommunityVo> records, List<AppletUserInformationVo> auis) {
        if (!auis.isEmpty()){
            Map<Long, AppletUserInformationVo> collect = auis.stream()
                .collect(Collectors.toMap(
                    AppletUserInformationVo::getUserId,
                    user -> user,
                    (existing, duplicate) -> existing
                ));
            records.getRecords().forEach(vo->{
                if (collect.containsKey(vo.getCreateBy())){
                    AppletUserInformationVo auiv = collect.get(vo.getCreateBy());
                    vo.setMemberName(auiv.getName());
                    vo.setUrl(auiv.getAvatarUrlUrl());
                }
            });
        }
    }

    /**
     * 查询符合条件的花友圈列表
     *
     * @param bo 查询条件
     * @return 花友圈列表
     */
    @Override
    public List<FlowerFriendsCommunityVo> queryList(FlowerFriendsCommunityBo bo) {
        LambdaQueryWrapper<FlowerFriendsCommunity> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowerFriendsCommunity> buildQueryWrapper(FlowerFriendsCommunityBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowerFriendsCommunity> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, FlowerFriendsCommunity::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), FlowerFriendsCommunity::getTitle, bo.getTitle());
        lqw.eq(bo.getType() != null, FlowerFriendsCommunity::getType, bo.getType());
        lqw.like(StringUtils.isNotBlank(bo.getMemberId()), FlowerFriendsCommunity::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getMemberName()), FlowerFriendsCommunity::getMemberName, bo.getMemberName());
        lqw.eq(StringUtils.isNotBlank(bo.getGrade()), FlowerFriendsCommunity::getGrade, bo.getGrade());
        lqw.eq(bo.getPageView() != null, FlowerFriendsCommunity::getPageView, bo.getPageView());
        lqw.eq(bo.getLikes() != null, FlowerFriendsCommunity::getLikes, bo.getLikes());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), FlowerFriendsCommunity::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getVideoImagesIds()), FlowerFriendsCommunity::getVideoImagesIds, bo.getVideoImagesIds());
        lqw.eq(bo.getStatus() != null, FlowerFriendsCommunity::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增花友圈
     *
     * @param bo 花友圈
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlowerFriendsCommunityBo bo) {
        FlowerFriendsCommunity add = MapstructUtils.convert(bo, FlowerFriendsCommunity.class);
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
    public Boolean updateByBo(FlowerFriendsCommunityBo bo) {
        FlowerFriendsCommunity update = MapstructUtils.convert(bo, FlowerFriendsCommunity.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlowerFriendsCommunity entity){
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
    public List<FlowerFriendsCommunityCommentVo> getCommentById(Long communityId) {
        List<FlowerFriendsCommunityCommentVo> list = communityCommentMapper.selectVoListByCommunityId(communityId);
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

    public static List<FlowerFriendsCommunityCommentVo> buildTree(List<FlowerFriendsCommunityCommentVo> nodes) {
        // 存储所有节点的 Map，key 是节点 ID，value 是节点对象
        Map<Long, FlowerFriendsCommunityCommentVo> nodeMap = new HashMap<>();
        // 存储根节点
        List<FlowerFriendsCommunityCommentVo> roots = new ArrayList<>();

        // 1. 将所有节点放入 nodeMap
        for (FlowerFriendsCommunityCommentVo node : nodes) {
            nodeMap.put(node.getId(), node);
        }

        // 2. 遍历节点，根据 parentId 将子节点加入父节点的 child 列表
        for (FlowerFriendsCommunityCommentVo node : nodes) {
            if (node.getParentId() == null || node.getParentId() == 0) {
                // 如果 parentId 为 null 或 0，表示是根节点
                roots.add(node);
            } else {
                // 找到父节点并加入 child 列表
                FlowerFriendsCommunityCommentVo parent = nodeMap.get(node.getParentId());
                if (parent != null) {
                    parent.getReplies().add(node);
                }
            }
        }

        return roots;
    }
}
