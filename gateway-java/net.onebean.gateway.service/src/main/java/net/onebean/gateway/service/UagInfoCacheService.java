package net.onebean.gateway.service;

import net.onebean.gateway.vo.SendLoginSmsReq;
import net.onebean.gateway.vo.GetAccTokenVo;

public interface UagInfoCacheService {
    /**
     * 缓存短信信息
     * @param req 参数体
     * @return bool
     */
    Boolean cacheSmsInfo(SendLoginSmsReq req);

    /**
     * 计算短信一小时内 发送次数
     * @param telPhone 手机号
     * @return
     */
    void countSmsAnHour(String telPhone);
    /**
     * 判断一小时内 发送短信次数有没有达到上限
     * @param telPhone 手机号
     * @return bool
     */
    Boolean checkSmsAnHour(String telPhone);
    /**
     * 计数一天之内 同一IP地址 发送次数
     * @param ipAddress ip
     */
    void countIpAddressDay(String ipAddress);
    /**
     * 检查一天之内 同一IP地址 发送次数
     * @param ipAddress ip
     */
    Boolean checkIpAddressDay(String ipAddress);
    /**
     * 缓存accessToken
     * @param getAccTokenVo 实体类
     * @return bool
     */
    Boolean cacheAccessToken(GetAccTokenVo getAccTokenVo);
}
