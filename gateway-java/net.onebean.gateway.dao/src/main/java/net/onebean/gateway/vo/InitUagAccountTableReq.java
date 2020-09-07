package net.onebean.gateway.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class InitUagAccountTableReq {

    @NotEmpty(message = "appId can not be empty")
    private String appId;
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @NotEmpty(message = "appName can not be empty")
    private String appName;
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
}
