package net.onebean.gateway.service;


import net.onebean.gateway.vo.GetAccTokenRequest;
import net.onebean.gateway.vo.GetAccessTokenResponse;

public interface AccessTokenService {
    /**
     * 获取accessToken
     * @param getAccTokenRequest 参数体
     * @return GetAccessTokenResponse
     */
    GetAccessTokenResponse getAccessToken(GetAccTokenRequest getAccTokenRequest);
    /**
     * 生成设备码
     * @param appId 应用ID
     * @return 设备码
     */
    String generateDeviceToken(String appId);




}
