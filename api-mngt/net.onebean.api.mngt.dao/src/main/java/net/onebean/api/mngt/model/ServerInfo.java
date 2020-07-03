package net.onebean.api.mngt.model;

import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description server info model
* @date 2019-01-22 09:13:50
*/
@TableName("t_server_info")
public class ServerInfo extends BaseModel implements InterfaceBaseDeletedModel {



        /**
        * 服务名称
        */
        private String serverName;
        @FiledName("serverName")
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
        @FiledName("upsteam_node_name")
        public String getUpsteamNodeName() {
            return upsteamNodeName;
        }
        public void setUpsteamNodeName(String upsteamNodeName) {
            this.upsteamNodeName = upsteamNodeName;
        }

        /**
         * 选中版本
         */
        private String selectedVersion;
        @FiledName("selected_version")
        public String getSelectedVersion(){
            return this.selectedVersion;
        }
        public void setSelectedVersion(String selectedVersion){
            this.selectedVersion = selectedVersion;
        }


        /**
         * 部署类型 0:物理地址部署 1:kubernetes部署
         */
        private String deployType;
        @FiledName("deploy_type")
        public String getDeployType(){
            return this.deployType;
        }
        public void setDeployType(String deployType){
            this.deployType = deployType;
        }


        /*是否是前端项目,0否1是*/
        private String isFront;
        @FiledName("is_front")
        public String getIsFront() {
            return isFront;
        }
        public void setIsFront(String isFront) {
            this.isFront = isFront;
        }

        /*是否开启ssl*/
        private String isSsl;
        @FiledName("is_ssl")
        public String getIsSsl() {
            return isSsl;
        }
        public void setIsSsl(String isSsl) {
            this.isSsl = isSsl;
        }

        /*监听端口号*/
        private String listenPort;
        @FiledName("listen_port")
        public String getListenPort() {
            return listenPort;
        }
        public void setListenPort(String listenPort) {
            this.listenPort = listenPort;
        }

        /*ssl监听端口号*/
        private String sslListenPort;
        @FiledName("ssl_listen_port")
        public String getSslListenPort() {
            return sslListenPort;
        }
        public void setSslListenPort(String sslListenPort) {
            this.sslListenPort = sslListenPort;
        }

        /*域名*/
        private String serverHost;
        @FiledName("server_host")
        public String getServerHost() {
            return serverHost;
        }
        public void setServerHost(String serverHost) {
            this.serverHost = serverHost;
        }

        /*证书key路径*/
        private String sslCrtKeyPath;
        @FiledName("ssl_crt_key_path")
        public String getSslCrtKeyPath() {
            return sslCrtKeyPath;
        }
        public void setSslCrtKeyPath(String sslCrtKeyPath) {
            this.sslCrtKeyPath = sslCrtKeyPath;
        }

        /*证书路径*/
        private String sslCrtPath;
        @FiledName("ssl_crt_path")
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
        @FiledName("createTime")
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
        @FiledName("updateTime")
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
        @FiledName("operatorId")
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
        @FiledName("operatorName")
        public String getOperatorName(){
            return this.operatorName;
        }
        public void setOperatorName(String operatorName){
            this.operatorName = operatorName;
        }


        /**
        * 逻辑删除,0否1是
        */
        private String isDeleted;
        @FiledName("isDeleted")
        public String getIsDeleted(){
            return this.isDeleted;
        }
        public void setIsDeleted(String isDeleted){
            this.isDeleted = isDeleted;
        }




}