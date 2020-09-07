package net.onebean.gateway.service;


import net.onebean.core.base.IBaseBiz;
import net.onebean.core.extend.Sort;
import net.onebean.core.query.Pagination;
import net.onebean.gateway.model.UserSmsRecord;
import net.onebean.gateway.vo.FindUserSmsRecordReq;
import net.onebean.gateway.vo.UserSmsRecordVo;

import java.util.List;


/**
* @author Icc
* @description 短信发送记录 service
* @date 2020-07-13 13:19:03
*/

public interface UserSmsRecordService extends IBaseBiz<UserSmsRecord> {

    /**
     * 查找短信发送记录
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<UserSmsRecordVo> findUserSmsRecord(FindUserSmsRecordReq param, Pagination page, Sort sort);
}