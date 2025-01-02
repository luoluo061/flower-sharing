package org.dromara.flower.service;

import cn.hutool.core.lang.tree.Tree;
import org.dromara.common.core.domain.R;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.domain.bo.MemberLevelBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 会员等级Service接口
 *
 * @author chzl
 * @date 2024-12-24
 */
public interface IMemberLevelService {

    /**
     * 查询会员等级
     *
     * @param id 主键
     * @return 会员等级
     */
    MemberLevelVo queryById(Long id);

    /**
     * 分页查询会员等级列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员等级分页列表
     */
    TableDataInfo<MemberLevelVo> queryPageList(MemberLevelBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员等级列表
     *
     * @param bo 查询条件
     * @return 会员等级列表
     */
    List<MemberLevelVo> queryList(MemberLevelBo bo);

    /**
     * 新增会员等级
     *
     * @param bo 会员等级
     * @return 是否新增成功
     */
    Boolean insertByBo(MemberLevelBo bo);

    /**
     * 修改会员等级
     *
     * @param bo 会员等级
     * @return 是否修改成功
     */
    Boolean updateByBo(MemberLevelBo bo);

    /**
     * 校验并批量删除会员等级信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 下拉类型树结构列表
     * @return 树结构
     */
    R<List<Map<String,String>>> getMemberLevelTree();

}
