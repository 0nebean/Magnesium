package net.onebean.gateway.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Icc
 */
public class ToggleIsLockStatusReq {


    @NotBlank(message = "filed of appId can not be empty")
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }



    @NotBlank(message = "filed of userId can not be empty")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 操作人ID
     */
    private Integer operatorId;
    public Integer getOperatorId(){
        return this.operatorId;
    }
    public void setOperatorId(Integer operatorId){
        this.operatorId = operatorId;
    }


    /**
     * 操作人姓名
     */
    private String operatorName;
    public String getOperatorName(){
        return this.operatorName;
    }
    public void setOperatorName(String operatorName){
        this.operatorName = operatorName;
    }
}