package net.onebean.gateway.vo;

public class FindUserSmsRecordReq {

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
}
