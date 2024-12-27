package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.MemberPointsExchangeGold;
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
 * 会员中心--积分兑换为金币视图对象 member_points_exchange_gold
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MemberPointsExchangeGold.class)
public class MemberPointsExchangeGoldVo implements Serializable {

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
     * 兑换积分
     */
    @ExcelProperty(value = "兑换积分")
    private Long points;

    /**
     * 兑现金币
     */
    @ExcelProperty(value = "兑现金币")
    private Long gold;

    /**
     * 剩余积分
     */
    @ExcelProperty(value = "剩余积分")
    private Long balance;

    /**
     * 创建人中文名称
     */
    @ExcelProperty(value = "创建人中文名称")
    private String createName;


}
