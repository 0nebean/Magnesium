package net.onebean.gateway.vo;

import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseModel;

/**
* @author Icc
* @description server info model
* @date 2019-01-21 18:05:28
*/
public class ServerInfoFindReq extends BaseModel implements InterfaceBaseModel {


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