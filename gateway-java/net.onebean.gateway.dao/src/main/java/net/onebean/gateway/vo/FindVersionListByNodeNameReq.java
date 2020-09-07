package net.onebean.gateway.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class FindVersionListByNodeNameReq {

    @NotEmpty(message = "upsteamNodeName can not be empty")
    private String upsteamNodeName;

    public String getUpsteamNodeName() {
        return upsteamNodeName;
    }

    public void setUpsteamNodeName(String upsteamNodeName) {
        this.upsteamNodeName = upsteamNodeName;
    }
}
