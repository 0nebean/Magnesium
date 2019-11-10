package net.onebean.server.mngt.service;


import net.onebean.server.mngt.model.UnLoginAccessApiWhiteList;
import net.onebean.server.mngt.vo.UnLoginAccessApiWhiteListVo;
import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;

import java.util.List;


/**
* @author 0neBean
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
}