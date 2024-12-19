package org.dromara.system.platform.service;

import org.dromara.system.platform.domain.MerchantInformation;
import org.dromara.system.platform.domain.query.MerchantInformationQuery;
import org.dromara.system.platform.domain.vo.MerchantInformationVo;
import org.dromara.system.platform.domain.bo.MerchantInformationBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.List;

/**
 * 商户信息Service接口
 *
 * @author Lion Li
 * @date 2024-12-05
 */
public interface IMerchantInformationService {

    /**
     * 查询商户信息
     *
     * @param id 主键
     * @return 商户信息
     */
    MerchantInformation queryById(Long id);

    /**
     * 分页查询商户信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商户信息分页列表
     */
    TableDataInfo<MerchantInformationVo> queryPageList(MerchantInformationQuery query, PageQuery pageQuery);

    /**
     * 查询符合条件的商户信息列表
     *
     * @param bo 查询条件
     * @return 商户信息列表
     */
    List<MerchantInformationVo> queryList(MerchantInformationQuery query);

    /**
     * 新增商户信息
     *
     * @param bo 商户信息
     * @return 是否新增成功
     */
    Boolean insertByBo(MerchantInformationBo bo);

    /**
     * 修改商户信息
     *
     * @param bo 商户信息
     * @return 是否修改成功
     */
    Boolean updateByBo(MerchantInformationBo bo);

    /**
     * 校验并批量删除商户信息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 更新状态
     * @param status
     * @param id
     * @return
     */
    boolean updateStatus(@PathVariable String status, Long id);
}
