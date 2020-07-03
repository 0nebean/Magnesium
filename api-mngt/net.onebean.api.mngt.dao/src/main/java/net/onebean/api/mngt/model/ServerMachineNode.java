package net.onebean.api.mngt.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import net.onebean.core.model.InterfaceBaseDeletedModel;


import java.sql.Timestamp;

/**
* @author 0nebean
* @description 服务器节点 model
* @date 2019-11-01 14:19:09
*/
@TableName("t_server_machine_node")
public class ServerMachineNode extends BaseModel implements InterfaceBaseDeletedModel {



        /**
        * ip地址
        */
        private String ipAddress;
        @FiledName("ip_address")
        public String getIpAddress(){
            return this.ipAddress;
        }
        public void setIpAddress(String ipAddress){
            this.ipAddress = ipAddress;
        }


        /**
        * 访问账户
        */
        private String accessUser;
        @FiledName("access_user")
        public String getAccessUser(){
            return this.accessUser;
        }
        public void setAccessUser(String accessUser){
            this.accessUser = accessUser;
        }


        /**
        * 访问密码
        */
        private String accessPassword;
        @FiledName("access_password")
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
        @FiledName("access_rsa_path")
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
        @FiledName("access_port")
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
        @FiledName("access_auth_type")
        public String getAccessAuthType(){
            return this.accessAuthType;
        }
        public void setAccessAuthType(String accessAuthType){
            this.accessAuthType = accessAuthType;
        }

        /**
        * 服务节点类型,0：openresty，1：kubernetes-master
        */
        private String serverMachineType;
        @FiledName("server_machine_type")
        public String getServerMachineType(){
            return this.serverMachineType;
        }
        public void setServerMachineType(String serverMachineType){
            this.serverMachineType = serverMachineType;
        }


        /**
        * 创建时间
        */
        private Timestamp createTime;
        @FiledName("create_time")
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
        @FiledName("update_time")
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
        @FiledName("operator_id")
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
        @FiledName("operator_name")
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
        @FiledName("is_deleted")
        public String getIsDeleted(){
            return this.isDeleted;
        }
        public void setIsDeleted(String isDeleted){
            this.isDeleted = isDeleted;
        }




}