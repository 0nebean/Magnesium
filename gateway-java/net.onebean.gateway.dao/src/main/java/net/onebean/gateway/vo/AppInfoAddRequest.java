package net.onebean.gateway.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.onebean.core.Json.TimeStamp_Deserializer;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

/**
 * @author Icc
 * @description 应用信息 model
 * @date 2019-01-03 16:14:09
 */
public class AppInfoAddRequest {

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

    @JsonDeserialize(using = TimeStamp_Deserializer.class)
    private Timestamp createTime;

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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