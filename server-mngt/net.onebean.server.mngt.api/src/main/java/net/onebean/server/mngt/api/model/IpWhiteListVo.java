package net.onebean.server.mngt.api.model;

import java.sql.Timestamp;

public class IpWhiteListVo {

    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ip地址
     */
    private String ipAddress;
    public String getIpAddress(){
        return this.ipAddress;
    }
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
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
