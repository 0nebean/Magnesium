package net.onebean.gateway.vo;

public class UagOperationLogMqReq {

    String description;
    String appName;
    String uagUserId;
    String uagUserNickName;

    public UagOperationLogMqReq() {
    }

    public UagOperationLogMqReq(String description, String appName, String uagUserId, String uagUserNickName) {
        this.description = description;
        this.appName = appName;
        this.uagUserId = uagUserId;
        this.uagUserNickName = uagUserNickName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUagUserId() {
        return uagUserId;
    }

    public void setUagUserId(String uagUserId) {
        this.uagUserId = uagUserId;
    }

    public String getUagUserNickName() {
        return uagUserNickName;
    }

    public void setUagUserNickName(String uagUserNickName) {
        this.uagUserNickName = uagUserNickName;
    }
}
