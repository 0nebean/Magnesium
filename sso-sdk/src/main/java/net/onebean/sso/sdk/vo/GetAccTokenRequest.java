package net.onebean.sso.sdk.vo;

/**
 * 获取AccToken 的请求体
 * @author world
 */
public class GetAccTokenRequest {
    /*应用ID*/
    private String appId;
    /*应用秘钥*/
    private String secret;
    /*时间戳*/
    private String timestamp;
    /*签名*/
    private String sign;

    private String deviceToken;

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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
