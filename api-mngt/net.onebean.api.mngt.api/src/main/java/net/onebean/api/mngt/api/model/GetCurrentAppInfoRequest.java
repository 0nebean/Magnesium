package net.onebean.api.mngt.api.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author 0neBean
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