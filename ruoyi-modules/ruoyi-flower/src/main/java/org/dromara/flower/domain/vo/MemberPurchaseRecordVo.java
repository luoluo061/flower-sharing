package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.MemberPurchaseRecord;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 会员购买记录视图对象 member_purchase_record
 *
 * @author chzl
 * @date 2024-12-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MemberPurchaseRecord.class)
public class MemberPurchaseRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    private String orderCode;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private String memberId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String memberName;

    /**
     * 会员电话
     */
    @ExcelProperty(value = "会员电话")
    private String phone;

    /**
     * 会员等级ID
     */
    @ExcelProperty(value = "会员等级ID")
    private Long memberLevelId;

    /**
     * 等级
     */
    @ExcelProperty(value = "等级")
    private String grade;

    /**
     * 等级中文名称
     */
    @ExcelProperty(value = "等级中文名称")
    private String gradeName;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格")
    private Long price;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 会员到期时间
     */
    private Date endTime;

    /**
     * 会员状态 0 关闭  1 正常
     */
    @ExcelProperty(value = "会员状态 0 关闭  1 正常")
    private Long status;

    /**
     * 支付状态 0 待支付  1 已支付
     */
    @ExcelProperty(value = "支付状态 0 待支付  1 已支付")
    private Long payStatus;

    /**
     * 会员等级
     */
    @ExcelProperty(value = "会员等级")
    private MemberLevelVo memberLevelVo;

    /**
     * 支付信息，支付接口返回信息JSON字符串
     */
    @ExcelProperty(value = "支付信息")
    private String payInfo;

}
