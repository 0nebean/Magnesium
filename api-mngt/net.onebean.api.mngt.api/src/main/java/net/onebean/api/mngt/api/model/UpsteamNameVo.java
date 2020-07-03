package net.onebean.api.mngt.api.model;

import java.sql.Timestamp;

public class UpsteamNameVo {

    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 节点名称
     */
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
    private String deployType;
    public String getDeployType(){
        return this.deployType;
    }
    public void setDeployType(String deployType){
        this.deployType = deployType;
    }


    /**
     * 创建时间
     */
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime = createTime;
    }


    /**
     * 更新时间
     */
    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Timestamp updateTime){
        this.updateTime = updateTime;
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
