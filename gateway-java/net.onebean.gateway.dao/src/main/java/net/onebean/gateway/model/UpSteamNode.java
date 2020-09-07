package net.onebean.gateway.model;

import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.sql.Timestamp;

/**
* @author Icc
* @description 服务节点信息 model
* @date 2019-11-01 16:51:06
*/
@TableName("t_upsteam_node")
public class UpSteamNode extends BaseModel implements InterfaceBaseDeletedModel {


        /**
         *物理地址
         */
        private String filePath;
        @FiledName("filePath")
        public String getFilePath() {
            return filePath;
        }
        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        /**
         *程序类型: 0前端,1后端
         */
        private String programType;
        @FiledName("programType")
        public String getProgramType() {
            return programType;
        }
        public void setProgramType(String programType) {
            this.programType = programType;
        }

        /**
        * 节点名称
        */
        private String nodeName;
        @FiledName("node_name")
        public String getNodeName(){
            return this.nodeName;
        }
        public void setNodeName(String nodeName){
            this.nodeName = nodeName;
        }


        /**
        * 节点命名空间
        */
        private String nodeNamespace;
        @FiledName("node_namespace")
        public String getNodeNamespace(){
            return this.nodeNamespace;
        }
        public void setNodeNamespace(String nodeNamespace){
            this.nodeNamespace = nodeNamespace;
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
        * 当前版本
        */
        private String currentVersion;
        @FiledName("current_version")
        public String getCurrentVersion(){
            return this.currentVersion;
        }
        public void setCurrentVersion(String currentVersion){
            this.currentVersion = currentVersion;
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


        /**
        * 物理地址
        */
        private String physicalPath;
        @FiledName("physical_path")
        public String getPhysicalPath(){
            return this.physicalPath;
        }
        public void setPhysicalPath(String physicalPath){
            this.physicalPath = physicalPath;
        }


        /**
        * 运行状态，0运行中，1已停止
        */
        private String runningStatus;
        @FiledName("running_status")
        public String getRunningStatus(){
            return this.runningStatus;
        }
        public void setRunningStatus(String runningStatus){
            this.runningStatus = runningStatus;
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