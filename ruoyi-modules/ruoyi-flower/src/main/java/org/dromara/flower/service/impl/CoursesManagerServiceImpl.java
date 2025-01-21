package org.dromara.flower.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.RedissonLockExecutor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.CodeUtils;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.constant.LockKeyString;
import org.dromara.flower.domain.CoursesManagerDetail;
import org.dromara.flower.domain.vo.CoursesManagerVideoVo;
import org.dromara.flower.domain.vo.CoursesPurchaseRecordsVo;
import org.dromara.flower.mapper.CoursesManagerDetailMapper;
import org.dromara.flower.mapper.CoursesManagerVideoMapper;
import org.dromara.flower.mapper.CoursesPurchaseRecordsMapper;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.system.mapper.SysOssMapper;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.CoursesManagerBo;
import org.dromara.flower.domain.vo.CoursesManagerVo;
import org.dromara.flower.domain.CoursesManager;
import org.dromara.flower.mapper.CoursesManagerMapper;
import org.dromara.flower.service.ICoursesManagerService;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 视频管理Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CoursesManagerServiceImpl implements ICoursesManagerService {

    private final CoursesManagerMapper baseMapper;
    private final LockTemplate lockTemplate;
    private final CoursesManagerDetailMapper coursesManagerDetailMapper;
    private final SysOssMapper sysOssMapper;
    private final CoursesManagerVideoMapper coursesManagerVideoMapper;
    private final CoursesPurchaseRecordsMapper coursesPurchaseRecordsMapper;
    private final AppletUserInformationMapper appletUserInformationMapper;

    /**
     * 超时时间
     */
    private static final long TIMEOUT = 86400;
    /**
     * 观看状态 0 关 1 开
     */
    private static final long STATUS_OPEN = 1L;
    private static final long STATUS_CLOSE = 0L;

    // 全部观看权限字符串
    private static final String ALL = "ALL";

    /**
     * 查询视频管理
     *
     * @param id 主键
     * @return 视频管理
     */
    @Override
    public CoursesManagerVo queryById(Long id) {
        LoginUser loginUser = getLoginUser();
        CoursesManagerVo vo = baseMapper.selectVoById(id);
        if (vo == null) {
            return new CoursesManagerVo();
        }
        // 查询所有的集数信息
        List<CoursesManagerVideoVo> videoVoList = coursesManagerVideoMapper.getVideoByCoursesManagerId(id);
        vo.setVideoVoList(videoVoList);
        vo.setStatus(STATUS_CLOSE);
        // 查看当前用户是否购买
        if (loginUser != null) {
            CoursesPurchaseRecordsVo purchaseRecordsVo = coursesPurchaseRecordsMapper.getPurchaseRecordsByUserIdAndCoursesId(id, loginUser.getUserId());
            if (purchaseRecordsVo != null) {
                vo.setStatus(STATUS_OPEN);
            }
            // 会员等级是否达到要求
            String level = appletUserInformationMapper.getMemberLevelInfoById(loginUser.getUserId());
            if (level != null && vo.getAccessIds().contains(level)) {
                vo.setStatus(STATUS_OPEN);
            }
        }
        if (ALL.equals(vo.getAccessIds())){
            vo.setStatus(STATUS_OPEN);
        }
        return vo;
    }

    /**
     * 分页查询视频管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 视频管理分页列表
     */
    @Override
    public TableDataInfo<CoursesManagerVo> queryPageList(CoursesManagerBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CoursesManager> lqw = buildQueryWrapper(bo);
        Page<CoursesManagerVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        // 根据当前课程类型父级id查询课程类型名称
        if (!result.getRecords().isEmpty()) {
            LoginUser loginUser = LoginHelper.getLoginUser();
            MapResultHandler<Long, String> map = new MapResultHandler<>();
            List<Long> ids = result.getRecords().stream()
                .filter(Objects::nonNull)
                .map(CoursesManagerVo::getCourseTypeId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
            if (!ids.isEmpty()) {
                baseMapper.selectIdCoursesType(map, ids);
            }
            Map<Long, String> mappedResults = map.getMappedResults();
            Map<Long, String> reversedResults;
            if (!mappedResults.isEmpty()) {
                reversedResults = reversePathsWithStream(mappedResults);
            } else {
                reversedResults = new HashMap<>();
            }
            if (!reversedResults.isEmpty()) {
                result.getRecords().forEach(vo -> {
                    vo.setCourseTypeName(reversedResults.get(vo.getCourseTypeId()));
                });
            }
            // 课程封面URL
            MapResultHandler<Long, String> resultHandler = new MapResultHandler<>();
            List<Long> cover = result.getRecords().stream()
                .map(CoursesManagerVo::getCoverUrlId)
                .filter(Objects::nonNull)
                .toList();
            if (!cover.isEmpty()) {
                sysOssMapper.getIdMapUrlByIds(resultHandler, cover);
            }
            if (!resultHandler.getMappedResults().isEmpty()) {
                Map<Long, String> resultMap = resultHandler.getMappedResults();
                result.getRecords().forEach(vo -> {
                    vo.setCoverUrl(resultMap.get(vo.getCoverUrlId()));
                });
            }
            if ("xcx".equals(loginUser.getUserType())){
                AppletUserInformationVo app = appletUserInformationMapper.selectVoById(loginUser.getUserId());
                for (CoursesManagerVo item : result.getRecords()) {
                    // 获取 accessIds 字段（假设是字符串）
                    String accessIds = item.getAccessIds();

                    // 判断 accessIds 是否包含 "12345"
                    if (accessIds != null && accessIds.contains(app.getMemberLevelId().toString())) {
                        // 如果包含，设置 status = 1
                        item.setAccessStatus(1);
                    }else {
                        item.setAccessStatus(0);
                    }
                }
            }
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的视频管理列表
     *
     * @param bo 查询条件
     * @return 视频管理列表
     */
    @Override
    public List<CoursesManagerVo> queryList(CoursesManagerBo bo) {
        LambdaQueryWrapper<CoursesManager> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CoursesManager> buildQueryWrapper(CoursesManagerBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CoursesManager> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, CoursesManager::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), CoursesManager::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getSubtitle()), CoursesManager::getSubtitle, bo.getSubtitle());
        lqw.like(StringUtils.isNotBlank(bo.getCode()), CoursesManager::getCode, bo.getCode());
        lqw.eq(bo.getCourseTypeId() != null, CoursesManager::getCourseTypeId, bo.getCourseTypeId());
        lqw.between(bo.getBeginDate() != null && bo.getEndDate() != null, CoursesManager::getCreateTime, bo.getBeginDate(), bo.getEndDate());
        lqw.eq(bo.getNumber() != null, CoursesManager::getNumber, bo.getNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getAccessLevel()), CoursesManager::getAccessLevel, bo.getAccessLevel());
        lqw.eq(bo.getPrice() != null, CoursesManager::getPrice, bo.getPrice());
        lqw.eq(bo.getStatus() != null, CoursesManager::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), CoursesManager::getDescription, bo.getDescription());
        lqw.eq(bo.getCoverUrlId() != null, CoursesManager::getCoverUrlId, bo.getCoverUrlId());
        return lqw;
    }

    /**
     * 新增视频管理
     *
     * @param bo 视频管理
     * @return 是否新增成功
     */
    @Override
    @Transactional
    public Boolean insertByBo(CoursesManagerBo bo) {
        CoursesManager add = MapstructUtils.convert(bo, CoursesManager.class);
        validEntityBeforeSave(add);
        if (add == null) {
            return false;
        }
        // 生成课程编号
        String coursesCode = createCourses();
        add.setPublishDate(new Date());
        add.setCode(coursesCode);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        // 保存课程相关的富文本
        if (bo.getDetailBo() != null) {
            CoursesManagerDetail cmd = MapstructUtils.convert(bo.getDetailBo(), CoursesManagerDetail.class);
            cmd.setCoursesManagerId(add.getId());
            coursesManagerDetailMapper.insert(cmd);
        }
        return flag;
    }

    /**
     * 修改视频管理
     *
     * @param bo 视频管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CoursesManagerBo bo) {
        CoursesManager update = MapstructUtils.convert(bo, CoursesManager.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CoursesManager entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除视频管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    /**
     * 批量修改视频下架
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean editCoursesStatus(Long[] ids) {
        if (ids.length == 0) {
            return false;
        }
        boolean flag = baseMapper.updateCoursesById(ids) > 0;
        if (flag) {
            return true;
        }
        return false;
    }

    /**
     * 生成课程编号 cv2024123000001
     *
     * @return
     */
    private String createCourses() {
        String coursesCode = "";
        final LockInfo lockInfo = lockTemplate.lock(LockKeyString.COURSES_CODE_LOCK_KEY, 30000L, 5000L, RedissonLockExecutor.class);
        if (null == lockInfo) {
            throw new RuntimeException("业务处理中,请稍后再试");
        }
        // 获取日期字符串
        String date = DateUtils.dateTime();
        // 获取锁成功，处理业务
        try {
            try {
                QueryWrapper<CoursesManager> wrapper = new QueryWrapper<>();
                wrapper.like("code", date);
                wrapper.orderByDesc("create_time");
                wrapper.last("limit 1");
                CoursesManager cm = baseMapper.selectOne(wrapper);
                if (ObjectUtil.isEmpty(cm)) {
                    coursesCode = CodeUtils.codeGeneration(LockKeyString.COURSES_CODE, 5, 0, date);
                } else {
                    int index = Integer.parseInt(cm.getCode().substring(cm.getCode().length() - 5));
                    coursesCode = CodeUtils.codeGeneration(LockKeyString.COURSES_CODE, 5, index, date);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("执行简单方法1 , 当前线程:" + Thread.currentThread().getName());
        } finally {
            //释放锁
            lockTemplate.releaseLock(lockInfo);
        }
        //结束
        return coursesCode;
    }

    /**
     * 查询出来的等级为 c/b/a 转换为 a/b/c
     *
     * @param originalMap
     * @return
     */
    private static Map<Long, String> reversePathsWithStream(Map<Long, String> originalMap) {
        return originalMap.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,  // 保留原始的 key
                entry -> {
                    // 将路径按 "/" 分割，反转顺序，再拼接
                    String[] pathParts = entry.getValue().split("/");
                    String reversedPath = java.util.stream.Stream.of(pathParts)
                        .reduce((first, second) -> second + "/" + first)
                        .orElse("");
                    return reversedPath;
                }
            ));
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户的信息，如果用户未登录则返回 null
     */
    private LoginUser getLoginUser() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return new LoginUser();
        }
        return loginUser;
    }


}
