package net.onebean.gateway.model;

import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.sql.Timestamp;

/**
* @author Icc
* @description 私有令牌应用 model
* @date 2020-07-03 21:15:42
*/
@TableName("uag_user_private_auth_app_info")
public class PrivateAuthAppInfo extends BaseModel implements InterfaceBaseDeletedModel {



        /**
        * 应用名称
        */
        private String appName;
        @FiledName("app_name")
        public String getAppName(){
            return this.appName;
        }
        public void setAppName(String appName){
            this.appName = appName;
        }


        /**
        * 应用的openId
        */
        private String appId;
        @FiledName("app_id")
        public String getAppId() {
            return appId;
        }
        public void setAppId(String appId) {
            this.appId = appId;
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