package net.onebean.api.mngt.vo;

public class GetAccessTokenResponse {


    public GetAccessTokenResponse(String appId, String accessToken, String deviceToken, String expireIn) {
        this.accessToken = accessToken;
        this.deviceToken = deviceToken;
        this.expireIn = expireIn;
        this.appId = appId;
    }

    private String accessToken;
    private String deviceToken;
    private String expireIn;
    private String appId;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(String expireIn) {
        this.expireIn = expireIn;
    }

    public GetAccessTokenResponse() {
    }
}
