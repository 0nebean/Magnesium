package net.onebean.gateway.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.onebean.core.Json.TimeStamp_Deserializer;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

public class UnLoginAccessWhiteListModfiyReq {

    @NotEmpty(message = "id can not be empty")
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 应用ID
     */
    private Integer appId;
    public Integer getAppId(){
        return this.appId;
    }
    public void setAppId(Integer appId){
        this.appId = appId;
    }

    /**
     * 接口ID
     */
    private Integer apiId;
    public Integer getApiId() {
        return apiId;
    }
    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    /**
     * 接口名
     */
    private String apiName;
    public String getApiName(){
        return this.apiName;
    }
    public void setApiName(String apiName){
        this.apiName = apiName;
    }

    /**
     * API接口
     */
    private String apiPath;
    public String getApiPath(){
        return this.apiPath;
    }
    public void setApiPath(String apiPath){
        this.apiPath = apiPath;
    }

    @JsonDeserialize(using = TimeStamp_Deserializer.class)
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime = createTime;
    }

    @JsonDeserialize(using = TimeStamp_Deserializer.class)
    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Timestamp updateTime){
        this.updateTime = updateTime;
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
