package net.onebean.user.mngt.api.model;

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

}
