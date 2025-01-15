package org.dromara.flower.platform.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.enums.Status;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.flower.mapper.MemberPurchaseRecordMapper;
import org.dromara.flower.platform.constant.AddAndSubtract;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.mapper.SysOssMapper;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

/**
 * 小程序用户信息Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class AppletUserInformationServiceImpl implements IAppletUserInformationService {

    private final AppletUserInformationMapper baseMapper;
    private final SysOssMapper sysOssMapper;
    private final MemberPurchaseRecordMapper memberPurchaseRecordMapper;

    private static Long ZERO = 0L;

    public static final List<String> staticString = new ArrayList<>(4) {{
        add("addPoints");
        add("subtractPoints");
        add("addGold");
        add("subtractGold");
    }};

    /**
     * 查询小程序用户信息
     *
     * @param userId 主键
     * @return 小程序用户信息
     */
    @Override
    public AppletUserInformationVo queryById(Long userId) {
        AppletUserInformationVo vo = baseMapper.selectVoById(userId);
        if (vo.getMemberLevelId() != null) {
            String grade = baseMapper.selectMemberLevelByid(vo.getMemberLevelId());
            if (ObjectUtil.isNotEmpty(grade)) {
                vo.setMemberLevelName(grade);
            }
        }
        // 查询地址信息

        return vo;
    }

    /**
     * 分页查询小程序用户信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息分页列表
     */
    @Override
    public TableDataInfo<AppletUserInformationVo> queryPageList(AppletUserInformationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppletUserInformation> lqw = buildQueryWrapper(bo);
        Page<AppletUserInformationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (!result.getRecords().isEmpty()) {
            // 获取会员等级中文
            List<Long> levelIds = result.getRecords().stream()
                .filter(Objects::nonNull)
                .map(AppletUserInformationVo::getMemberLevelId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
            if (!levelIds.isEmpty()) {
                MapResultHandler<Long, String> resultHandler = new MapResultHandler<>();
                baseMapper.getLevelNamesByIds(resultHandler, levelIds);
                Map<Long, String> levelNameMap = resultHandler.getMappedResults();
                result.getRecords().forEach(v -> {
                    if (v.getMemberLevelId() != null && levelNameMap.containsKey(v.getMemberLevelId())) {
                        v.setMemberLevelName(levelNameMap.get(v.getMemberLevelId()));
                    }
                });
            }
            // 获取推荐人中文
            List<Long> parentIds = result.getRecords().stream()
                .filter(Objects::nonNull)
                .map(AppletUserInformationVo::getParentId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
            MapResultHandler<Long, String> map = new MapResultHandler();
            baseMapper.getParentNameByIds(map, parentIds);
            Map<Long, String> mappedResults = map.getMappedResults();
            if (!mappedResults.isEmpty()){
                result.getRecords().forEach(v -> {
                    if (v.getParentId() != null && mappedResults.containsKey(v.getParentId())) {
                        v.setParentName(mappedResults.get(v.getParentId()));
                    }
                });
            }

        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序用户信息列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息列表
     */
    @Override
    public List<AppletUserInformationVo> queryList(AppletUserInformationBo bo) {
        LambdaQueryWrapper<AppletUserInformation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppletUserInformation> buildQueryWrapper(AppletUserInformationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppletUserInformation> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, AppletUserInformation::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getMemberId()), AppletUserInformation::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), AppletUserInformation::getName, bo.getName());
        lqw.like(StringUtils.isNotBlank(bo.getNickName()), AppletUserInformation::getNickName, bo.getNickName());
        lqw.eq(bo.getAvatarUrl() != null, AppletUserInformation::getAvatarUrl, bo.getAvatarUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getUserType()), AppletUserInformation::getUserType, bo.getUserType());
        lqw.like(StringUtils.isNotBlank(bo.getPhone()), AppletUserInformation::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getIdNumber()), AppletUserInformation::getIdNumber, bo.getIdNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getOpenid()), AppletUserInformation::getOpenid, bo.getOpenid());
        lqw.eq(bo.getStatus() != null, AppletUserInformation::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getWechatNumber()), AppletUserInformation::getWechatNumber, bo.getWechatNumber());
        lqw.eq(bo.getGroupId() != null, AppletUserInformation::getGroupId, bo.getGroupId());
        lqw.eq(bo.getMemberLevelId() != null, AppletUserInformation::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(bo.getGender() != null, AppletUserInformation::getGender, bo.getGender());
        lqw.eq(bo.getPoints() != null, AppletUserInformation::getPoints, bo.getPoints());
        lqw.eq(bo.getAmount() != null, AppletUserInformation::getAmount, bo.getAmount());
        lqw.eq(bo.getTotal() != null, AppletUserInformation::getTotal, bo.getTotal());
        lqw.eq(bo.getPromotion() != null, AppletUserInformation::getPromotion, bo.getPromotion());
        lqw.eq(bo.getGold() != null, AppletUserInformation::getGold, bo.getGold());
        lqw.eq(bo.getParentId() != null, AppletUserInformation::getParentId, bo.getParentId());
        return lqw;
    }

    /**
     * 新增小程序用户信息
     *
     * @param bo 小程序用户信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(AppletUserInformationBo bo) {
        AppletUserInformation add = MapstructUtils.convert(bo, AppletUserInformation.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改小程序用户信息
     *
     * @param bo 小程序用户信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(AppletUserInformationBo bo) {
        if (bo.getUserId() == null){
            bo.setUserId(LoginHelper.getLoginUser().getUserId());
        }
        AppletUserInformation update = MapstructUtils.convert(bo, AppletUserInformation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppletUserInformation entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除小程序用户信息信息
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

    @Override
    public AppletUserInformationVo getByPhone(String phone) {
        LambdaQueryWrapper<AppletUserInformation> lqw = Wrappers.lambdaQuery();
        lqw.eq(AppletUserInformation::getPhone, phone);
        AppletUserInformationVo appletUserInformationVo = baseMapper.selectVoOne(lqw);
        return appletUserInformationVo;
    }

    /**
     * 通过openid获取user
     *
     * @param openId
     * @return
     */
    @Override
    public AppletUserInformationVo getByOpenId(String openId) {
        LambdaQueryWrapper<AppletUserInformation> lqw = Wrappers.lambdaQuery();
        lqw.eq(AppletUserInformation::getOpenid, openId);
        AppletUserInformationVo appletUserInformationVo = baseMapper.selectVoOne(lqw);
        return appletUserInformationVo;
    }

    /**
     * 更新用户状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public boolean updateStatus(Long id, String status) {
        if (Status.DISABLE.equals(status) || Status.ENABLE.equals(status)) {
            return false;
        }
        return false;
    }

    @Override
    public Boolean updatePointsGoldByBo(AppletUserInformationBo bo) {
        if (!staticString.contains(bo.getModified())) {
            return false;
        }
        if (StringUtils.isBlank(bo.getModified())) {
            return false;
        }
        if (bo.getModifiedValue() < 0) {
            bo.setModifiedValue(0L);
        }
        AppletUserInformation app = baseMapper.selectById(bo.getUserId());
        if (app == null) {
            return false;
        }
        AppletUserInformation update = new AppletUserInformation();
        update.setUserId(app.getUserId());
        //修改积分
        if (AddAndSubtract.ADD_POINTS.equals(bo.getModified())) {
            addPoints(app, bo, update);
        } else if (AddAndSubtract.SUBTRACT_POINTS.equals(bo.getModified())) {
            subtractPoints(app, bo, update);
        } else if (AddAndSubtract.ADD_GOLD.equals(bo.getModified())) {
            addGold(app, bo, update);
        } else if (AddAndSubtract.SUBTRACT_GOLD.equals(bo.getModified())) {
            subtractGold(app, bo, update);
        }

        return baseMapper.updateById(update) > 0 ? true : false;
    }

    @Override
    public R<List<AppletUserInformationVo>> queryMemberInfoList() {
        List<AppletUserInformationVo> appletUserInformationVos = baseMapper.selectVoList();
        return R.ok(appletUserInformationVos);
    }

    /**
     * 生成图片二维码
     * @return 二维码字符串
     */
    @Override
    public R<String> generateQrCode() {
        // 生成图片二维码大小 376 * 376
        QrConfig config = new QrConfig(376,376);
        // 设置边距 , 既二维码和背景之间的边距
        config.setMargin(1);
        // 设置容错级别
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        // 获取当前人员的用户ID
        LoginUser loginUser = getLoginUser();
        if (loginUser == null){
            throw new RuntimeException("请先登陆!");
        }
        // 生成二维码
        String qrCode = QrCodeUtil.generateAsBase64(loginUser.getUserId().toString(), config, "png");

        return R.ok("操作成功",qrCode);
    }

    /**
     *
     * @return
     */
    @Override
    public AppletUserInformationVo queryUserInfo() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (loginUser == null){
            AppletUserInformationVo empty = new AppletUserInformationVo();
            empty.setUserId(-1L);
            return empty;
        }
        AppletUserInformationVo vo = baseMapper.selectVoById(loginUser.getUserId());
        if (vo.getMemberLevelId() != null) {
            String grade = baseMapper.selectMemberLevelByid(vo.getMemberLevelId());
            if (ObjectUtil.isNotEmpty(grade)) {
                vo.setMemberLevelName(grade);
            }
        }
        // 设置头像URL
        if (vo.getAvatarUrl() != null){
            SysOssVo sysOssVo = sysOssMapper.selectVoById(vo.getAvatarUrl());
            vo.setAvatarUrlUrl(sysOssVo.getUrl());
        }
        // 查询购买记录
        if (vo.getMemberLevelId() != null){
            LambdaQueryWrapper<MemberPurchaseRecord> lqw = new LambdaQueryWrapper<>();
            lqw.eq(MemberPurchaseRecord::getCreateBy, vo.getUserId());
            lqw.eq(MemberPurchaseRecord::getStatus, 1);
            vo.setPurchaseRecordVo(memberPurchaseRecordMapper.selectVoOne(lqw));
        }
        return vo;
    }

    /**
     * 减金币
     *
     * @param app
     * @param bo
     * @param update
     */
    private void subtractGold(AppletUserInformation app, AppletUserInformationBo bo, AppletUserInformation update) {
        if (app.getGold() != null && (bo != null || bo.getGold() != null)) {
            Long newPoints = app.getGold() - bo.getModifiedValue();
            newPoints = Math.max(newPoints, ZERO); // 确保积分不会小于0
            update.setGold(newPoints); // 设置新的积分值
        } else {
            update.setGold(app.getGold());
        }
    }

    /**
     * 加金币
     *
     * @param app
     * @param bo
     * @param update
     */
    private void addGold(AppletUserInformation app, AppletUserInformationBo bo, AppletUserInformation update) {
        if (app.getGold() != null && (bo != null || bo.getGold() != null)) {
            Long newPoints = app.getGold() + bo.getModifiedValue();
            newPoints = Math.max(newPoints, ZERO); // 确保积分不会小于0
            update.setGold(newPoints); // 设置新的积分值
        } else {
            update.setGold(app.getGold());
        }
    }

    /**
     * 减积分
     *
     * @param app
     * @param bo
     * @return
     */
    private void subtractPoints(AppletUserInformation app, AppletUserInformationBo bo, AppletUserInformation update) {
        if (app.getPoints() != null && (bo != null || bo.getGold() != null)) {
            Long newPoints = app.getPoints() - bo.getModifiedValue();
            newPoints = Math.max(newPoints, ZERO); // 确保积分不会小于0
            update.setPoints(newPoints); // 设置新的积分值
        } else {
            update.setPoints(app.getPoints());
        }
    }

    /**
     * 加积分
     *
     * @param app
     * @param bo
     */
    private void addPoints(AppletUserInformation app, AppletUserInformationBo bo, AppletUserInformation update) {
        if (app.getPoints() != null && (bo != null || bo.getGold() != null)) {
            Long newPoints = app.getPoints() + bo.getModifiedValue();
            newPoints = Math.max(newPoints, ZERO); // 确保积分不会小于0
            update.setPoints(newPoints); // 设置新的积分值
        } else {
            update.setPoints(app.getPoints());
        }
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
