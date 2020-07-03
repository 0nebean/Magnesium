package net.onebean.user.mngt.service;


import net.onebean.user.mngt.api.model.UagOperationLogMqReq;
import net.onebean.user.mngt.model.UagOperatorLog;
import net.onebean.user.mngt.api.model.FindUagLogReq;
import net.onebean.user.mngt.api.model.UagLogVo;
import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;

import java.util.List;


/**
* @author 0neBean
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