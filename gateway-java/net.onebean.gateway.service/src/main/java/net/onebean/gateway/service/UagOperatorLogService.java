package net.onebean.gateway.service;


import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.model.UagOperatorLog;
import net.onebean.gateway.vo.FindUagLogReq;
import net.onebean.gateway.vo.UagLogVo;
import net.onebean.gateway.vo.UagOperationLogMqReq;

import java.util.List;


/**
* @author Icc
* @description 操作日志 service
* @date 2019-06-27 23:45:23
*/

public interface UagOperatorLogService extends IBaseBiz<UagOperatorLog> {

    /**
     * 报错用户操作记录
     * @param req 参数
     * @return bool
     */
    Boolean saveUagOperatorLog(UagOperationLogMqReq req);

    /**
     * 查询操作日志列表
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<UagLogVo> findUagLogInfo(FindUagLogReq param, Pagination page, Sort sort);
}