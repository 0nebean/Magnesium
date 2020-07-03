package net.onebean.api.mngt.api.model;

import org.hibernate.validator.constraints.NotEmpty;

public class UpsteamNameAddReq {


    /**
     * 节点名称
     */
    @NotEmpty(message = "upsteamName can not be empty")
    private String upsteamName;
    public String getUpsteamName(){
        return this.upsteamName;
    }
    public void setUpsteamName(String upsteamName){
        this.upsteamName = upsteamName;
    }


    /**
     * 部署类型 0:物理地址部署 1:kubernetes部署
     */
    @NotEmpty(message = "deployType can not be empty")
    private String deployType;
    public String getDeployType(){
        return this.deployType;
    }
    public void setDeployType(String deployType){
        this.deployType = deployType;
    }



    private Integer operatorId;
    public Integer getOperatorId(){
        return this.operatorId;
    }
    public void setOperatorId(Integer operatorId){
        this.operatorId = operatorId;
    }



    private String operatorName;
    public String getOperatorName(){
        return this.operatorName;
    }
    public void setOperatorName(String operatorName){
        this.operatorName = operatorName;
    }
}
