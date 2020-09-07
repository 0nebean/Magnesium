package net.onebean.gateway.service;


import net.onebean.gateway.vo.IpWhiteListAddReq;
import net.onebean.gateway.vo.IpWhiteListQueryReq;
import net.onebean.gateway.vo.IpWhiteListVo;
import net.onebean.gateway.model.IpWhiteList;
import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;

import java.util.List;


/**
* @author 0neBeen
* @description ip白名单 service
* @date 2019-02-21 15:48:15
*/

public interface IpWhiteListService extends IBaseBiz<IpWhiteList> {
    /**
     * 根据req 查找list
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<IpWhiteListVo> findByIpWhiteListQueryReq(IpWhiteListQueryReq param, Pagination page, Sort sort);
    /**
     * 是否有改ip地址存在
     * @param ipAddress ip
     * @return int
     */
    Boolean isRepeatIpAddress(String ipAddress);
    /**
     * 新增
     * @param request req
     * @return id
     */
    Long add(IpWhiteListAddReq request);
    /**
     * 是否重复
     * @param ipAddress ip
     * @return bool
     */
    Boolean isRepeatByIpAddress(String ipAddress);
    /**
     * 查找需要同步的节点
     * @return list
     */
    List<IpWhiteListVo> findSyncList();
}