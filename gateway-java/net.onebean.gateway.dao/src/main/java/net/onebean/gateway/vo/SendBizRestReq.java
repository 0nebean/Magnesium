package net.onebean.gateway.vo;

import org.hibernate.validator.constraints.NotBlank;

public class SendBizRestReq {

    @NotBlank(message = "param of filed telPhone can not be empty")
    private String telPhone;
    public String getTelPhone() {
        return telPhone;
    }
    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    @NotBlank(message = "param of filed deviceToken can not be empty")
    private String deviceToken;
    public String getDeviceToken() {
        return deviceToken;
    }
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
