package org.dromara.common.mypay.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 金额信息
 *
 * @author L.cm
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundAmount {

    /***
     * 退款金额
     */
    private long refund;

//    @ApiModelProperty("退款出资账户及金额(选填)")
//    @JsonProperty("from")
//    private List<Froms> from;

    /***
     * 原订单金额(必填)
     */
    private long total;

    /***
     * 退款币种(必填)
     */
    private String currency = "CNY";


}
