package org.dromara.flowerapplet.service;

import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.flower.domain.bo.CoursesPurchaseRecordsBo;
import org.dromara.flower.domain.vo.CoursesPurchaseRecordsVo;
import org.dromara.flowerapplet.domain.PayParam;

import java.util.Collection;
import java.util.List;

/**
 * 课程管理-课程购买记录Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface ICoursesAppletPurchaseRecordsService {

    /**
     * 查询课程管理-课程购买记录
     *
     * @param id 主键
     * @return 课程管理-课程购买记录
     */
    CoursesPurchaseRecordsVo queryById(Long id);

    /**
     * 分页查询课程管理-课程购买记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程管理-课程购买记录分页列表
     */
    TableDataInfo<CoursesPurchaseRecordsVo> queryPageList(CoursesPurchaseRecordsBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的课程管理-课程购买记录列表
     *
     * @param bo 查询条件
     * @return 课程管理-课程购买记录列表
     */
    List<CoursesPurchaseRecordsVo> queryList(CoursesPurchaseRecordsBo bo);

    /**
     * 新增课程管理-课程购买记录
     *
     * @param bo 课程管理-课程购买记录
     * @return 是否新增成功
     */
    Boolean insertByBo(CoursesPurchaseRecordsBo bo);

    /**
     * 修改课程管理-课程购买记录
     *
     * @param bo 课程管理-课程购买记录
     * @return 是否修改成功
     */
    Boolean updateByBo(CoursesPurchaseRecordsBo bo);

    /**
     * 校验并批量删除课程管理-课程购买记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 微信支付接口
     *
     * @param payParam 订单参数
     * @return 支付信息
     */
    R<WxJsapiResponse> submitOrders(PayParam payParam) throws Exception;

    /**
     * 退款
     * @param wxRefundRequest 请求参数
     * @return 操作状态
     */
    R<String> refundOrder(WxRefundRequest wxRefundRequest) throws Exception;
}
