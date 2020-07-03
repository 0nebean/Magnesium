package net.onebean.api.mngt.vo;

import net.onebean.core.base.BaseReflectModel;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description nginx节点管理 model
* @date 2019-03-01 12:00:33
*/
public class ServerMachineNodeSyncVo extends BaseReflectModel {


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

        private String accessUser;
        public String getAccessUser(){
            return this.accessUser;
        }
        public void setAccessUser(String accessUser){
            this.accessUser = accessUser;
        }

        /**
         * 服务节点类型,0：openresty，1：kubernetes-master
         */
        private String serverMachineType;
        public String getServerMachineType(){
            return this.serverMachineType;
        }
        public void setServerMachineType(String serverMachineType){
            this.serverMachineType = serverMachineType;
        }


        /**
         * 访问密码
         */
        private String accessPassword;
        public String getAccessPassword(){
            return this.accessPassword;
        }
        public void setAccessPassword(String accessPassword){
            this.accessPassword = accessPassword;
        }


        /**
         * 访问rsa_path
         */
        private String accessRsaPath;
        public String getAccessRsaPath(){
            return this.accessRsaPath;
        }
        public void setAccessRsaPath(String accessRsaPath){
            this.accessRsaPath = accessRsaPath;
        }


        /**
         * 访问端口号
         */
        private String accessPort;
        public String getAccessPort(){
            return this.accessPort;
        }
        public void setAccessPort(String accessPort){
            this.accessPort = accessPort;
        }


        /**
         * 访问授权方式 (0: 密码模式,1:公私钥模式)
         */
        private String accessAuthType;
        public String getAccessAuthType(){
            return this.accessAuthType;
        }
        public void setAccessAuthType(String accessAuthType){
            this.accessAuthType = accessAuthType;
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