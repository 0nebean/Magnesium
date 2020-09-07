package net.onebean.gateway.vo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Icc
 * @description 应用信息 model
 * @date 2019-01-03 16:14:09
 */
public class AppInfoModifyRequest {

    @NotEmpty(message = "id can not be empty")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty(message = "appName can not be empty")
    private String appName;

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    private String authType;

    public String getAuthType() {
        return this.authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    @NotEmpty(message = "appStatus can not be empty")
    private String appStatus;

    public String getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    private String loginType;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    private String oauthBaseUrl;

    public String getOauthBaseUrl() {
        return oauthBaseUrl;
    }

    public void setOauthBaseUrl(String oauthBaseUrl) {
        this.oauthBaseUrl = oauthBaseUrl;
    }

    /**
     * 操作人ID
     */
    private Integer operatorId;
    public Integer getOperatorId() {
        return this.operatorId;
    }
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }


    /**
     * 操作人姓名
     */
    private String operatorName;
    public String getOperatorName() {
        return this.operatorName;
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


}