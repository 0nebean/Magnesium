package net.onebean.gateway.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.sql.Timestamp;

/**
* @author Icc
* @description 短信发送记录 model
* @date 2020-07-13 13:19:03
*/
@TableName("uag_user_sms_record")
public class UserSmsRecord extends BaseModel implements InterfaceBaseDeletedModel {

        public UserSmsRecord() {
        }


        public UserSmsRecord(Integer uagId, String phoneNumber, String context) {
            this.uagId = uagId;
            this.phoneNumber = phoneNumber;
            this.context = context;
        }

        /**
        * 网关用户ID
        */
        private Integer uagId;
        @FiledName("uag_id")
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
        @FiledName("phone_number")
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
        @FiledName("context")
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