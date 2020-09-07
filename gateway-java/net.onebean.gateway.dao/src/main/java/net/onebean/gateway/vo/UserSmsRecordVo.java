package net.onebean.gateway.vo;

import java.sql.Timestamp;

public class UserSmsRecordVo {

    /**
     * 网关用户ID
     */
    private Integer uagId;
    public Integer getUagId(){
        return this.uagId;
    }
    public void setUagId(Integer uagId){
        this.uagId = uagId;
    }


    /**
     * 手机号
     */
    private String phoneNumber;
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }



    /**
     * 内容
     */
    private String context;
    public String getContext(){
        return this.context;
    }
    public void setContext(String context){
        this.context = context;
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
