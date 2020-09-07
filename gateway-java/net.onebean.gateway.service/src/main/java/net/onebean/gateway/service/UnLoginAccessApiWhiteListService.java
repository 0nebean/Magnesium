package net.onebean.gateway.service;


import net.onebean.gateway.vo.UnLoginAccessApiWhiteListVo;
import net.onebean.gateway.model.UnLoginAccessApiWhiteList;
import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;

import java.util.List;


/**
* @author Icc
* @description 未登录访问API白名单 service
* @date 2019-06-28 11:02:32
*/

public interface UnLoginAccessApiWhiteListService extends IBaseBiz<UnLoginAccessApiWhiteList> {
    /**
     * 查询列表
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<UnLoginAccessApiWhiteListVo> findByRsSalesUnLoginAccessWhiteListQueryRequest(UnLoginAccessApiWhiteListVo param, Pagination page, Sort sort);
    /**
     * 查询未绑定的数据
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<UnLoginAccessApiWhiteListVo> findUnBindingData(UnLoginAccessApiWhiteListVo param, Pagination page, Sort sort);
    /**
     * 查找私有令牌需要绑定的未登录白名单API
     * @return list
     */
    List<UnLoginAccessApiWhiteList> findPrivateTokenAuthWhiteList();
}