package net.onebean.gateway.vo;


public class FindUagLogReq {

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

    /**
     * 项目名
     */
    private String appName;
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
}
