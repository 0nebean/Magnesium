package net.onebean.user.mngt.service;

import net.onebean.user.mngt.vo.SendLoginSmsReq;

public interface UagInfoCacheService {
    /**
     * 缓存短信信息
     * @param req 参数体
     * @return bool
     */
    Boolean cacheSmsInfo(SendLoginSmsReq req);
}
