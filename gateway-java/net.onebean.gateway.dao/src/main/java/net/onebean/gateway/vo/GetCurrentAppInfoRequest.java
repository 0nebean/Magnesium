package net.onebean.gateway.vo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Icc
 */
public class GetCurrentAppInfoRequest {

    @NotEmpty(message = "appId can not be empty")
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}