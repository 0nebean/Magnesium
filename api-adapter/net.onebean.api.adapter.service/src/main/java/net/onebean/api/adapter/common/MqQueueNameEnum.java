package net.onebean.api.adapter.common;

public enum MqQueueNameEnum {
    AUTH_SET_ACCESS_TOKEN_CACHE("auth.set.access.token.cache"),
    MESSAGE_CENTER_SEND_SMS_MESSAGE("message.center.send.sms.message"),
    DEVOPS_K8S_NOTIFICATION_ADD("devops.k8s.notification.add"),
    ;
    private String name;

    MqQueueNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}