package net.onebean.api.mngt.api.model;

import net.onebean.api.mngt.api.model.enumModel.DeployTypeEnum;
import net.onebean.core.Json.EnumDeserialize;
import net.onebean.core.base.YesOrNoEnum;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

public class ServerInfoModifyReq {


    @NotEmpty(message = "id can not be empty")
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 服务名称
     */
    private String serverName;
    public String getServerName(){
        return this.serverName;
    }
    public void setServerName(String serverName){
        this.serverName = serverName;
    }

    /**
     * 访问地址
     */
    private String upsteamNodeName;
    public String getUpsteamNodeName() {
        return upsteamNodeName;
    }
    public void setUpsteamNodeName(String upsteamNodeName) {
        this.upsteamNodeName = upsteamNodeName;
    }


    /**
     * 部署类型 0:物理地址部署 1:kubernetes部署
     */
    @EnumDeserialize(using = DeployTypeEnum.class)
    private String deployType;
    public String getDeployType(){
        return this.deployType;
    }
    public void setDeployType(String deployType){
        this.deployType = deployType;
    }

    /**
     * 选中版本
     */
    private String selectedVersion;
    public String getSelectedVersion(){
        return this.selectedVersion;
    }
    public void setSelectedVersion(String selectedVersion){
        this.selectedVersion = selectedVersion;
    }


    /*是否是前端项目,0否1是*/
    @EnumDeserialize(using = YesOrNoEnum.class)
    private String isFront;
    public String getIsFront() {
        return isFront;
    }
    public void setIsFront(String isFront) {
        this.isFront = isFront;
    }

    /*是否开启ssl*/
    private String isSsl;
    public String getIsSsl() {
        return isSsl;
    }
    public void setIsSsl(String isSsl) {
        this.isSsl = isSsl;
    }

    /*监听端口号*/
    private String listenPort;
    public String getListenPort() {
        return listenPort;
    }
    public void setListenPort(String listenPort) {
        this.listenPort = listenPort;
    }

    /*ssl监听端口号*/
    private String sslListenPort;
    public String getSslListenPort() {
        return sslListenPort;
    }
    public void setSslListenPort(String sslListenPort) {
        this.sslListenPort = sslListenPort;
    }

    /*域名*/
    private String serverHost;
    public String getServerHost() {
        return serverHost;
    }
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }


    /*证书key路径*/
    private String sslCrtKeyPath;
    public String getSslCrtKeyPath() {
        return sslCrtKeyPath;
    }
    public void setSslCrtKeyPath(String sslCrtKeyPath) {
        this.sslCrtKeyPath = sslCrtKeyPath;
    }

    /*证书路径*/
    private String sslCrtPath;
    public String getSslCrtPath() {
        return sslCrtPath;
    }
    public void setSslCrtPath(String sslCrtPath) {
        this.sslCrtPath = sslCrtPath;
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
