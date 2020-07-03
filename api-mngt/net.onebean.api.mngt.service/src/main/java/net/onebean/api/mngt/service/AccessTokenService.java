package net.onebean.api.mngt.service;


import net.onebean.api.mngt.vo.GetAccTokenRequest;
import net.onebean.api.mngt.vo.GetAccessTokenResponse;
import net.onebean.util.WebUtils;

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
