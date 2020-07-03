package net.onebean.user.mngt.vo;

public class CreateAccountMqReq {


    public CreateAccountMqReq() {
    }

    public CreateAccountMqReq(String uagUserId, String uagUserName) {
        this.uagUserId = uagUserId;
        this.uagUsername = uagUserName;
    }

    private String uagUserId;
    private String uagUsername;

    public String getUagUserId() {
        return uagUserId;
    }

    public void setUagUserId(String uagUserId) {
        this.uagUserId = uagUserId;
    }

    public String getUagUsername() {
        return uagUsername;
    }

    public void setUagUsername(String uagUsername) {
        this.uagUsername = uagUsername;
    }

}
