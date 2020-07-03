package net.onebean.user.mngt.service;


import net.onebean.user.mngt.vo.SendLoginSmsRestReq;

public interface SendSmsService {
    /**
     * 发送登录验证短信
     * @param req 参数体
     * @return bool
     */
    Boolean sendLoginSms(SendLoginSmsRestReq req);
}
