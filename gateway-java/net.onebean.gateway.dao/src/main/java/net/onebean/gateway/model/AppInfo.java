package net.onebean.gateway.model;

import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.sql.Timestamp;

/**
 * @author Icc
 * @description app info model
 * @date 2019-01-21 17:18:49
 */
@TableName("t_app_info")
public class AppInfo extends BaseModel implements InterfaceBaseDeletedModel {

    /**
     * 授权模式 0:innerAPi,1:openApi
     */
    private String authType;

    @FiledName("authType")
    public String getAuthType() {
        return this.authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }


    /**
     * inner api 访问令牌
     */
    private String innerApiToken;

    @FiledName("innerApiToken")
    public String getInnerApiToken() {
        return this.innerApiToken;
    }

    public void setInnerApiToken(String innerApiToken) {
        this.innerApiToken = innerApiToken;
    }


    /**
     *
     */
    private String openId;

    @FiledName("openId")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    /**
     * 秘钥
     */
    private String secret;

    @FiledName("secret")
    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


    /**
     * app名称
     */
    private String appName;

    @FiledName("appName")
    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * app状态 0:连接 1:断开
     */
    private String appStatus;

    @FiledName("appStatus")
    public String getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }


    /**
     * 创建时间
     */
    private Timestamp createTime;

    @FiledName("createTime")
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    /**
     * 更新时间
     */
    private Timestamp updateTime;

    @FiledName("updateTime")
    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }


    /**
     * 操作人ID
     */
    private Integer operatorId;

    @FiledName("operatorId")
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

    @FiledName("operatorName")
    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


    /**
     * 逻辑删除,0否1是
     */
    private String isDeleted;

    @FiledName("isDeleted")
    public String getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    private String loginType;

    @FiledName("loginType")
    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    private String oauthBaseUrl;

    @FiledName("oauthBaseUrl")
    public String getOauthBaseUrl() {
        return oauthBaseUrl;
    }

    public void setOauthBaseUrl(String oauthBaseUrl) {
        this.oauthBaseUrl = oauthBaseUrl;
    }
}