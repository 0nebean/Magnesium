package net.onebean.gateway.service;


import net.onebean.gateway.vo.SendLoginSmsRestReq;

public interface SendSmsService {
    /**
     * 发送登录验证短信
     *
     * @param req 参数体
     * @return bool
     */
    Boolean sendLoginSms(SendLoginSmsRestReq req);
    /**
     * 发送验证码
     * @param code   验证码
     * @param mobile 手机号
     * @return bool
     */
    Boolean doSendSmsCode(String code, String mobile);
}
