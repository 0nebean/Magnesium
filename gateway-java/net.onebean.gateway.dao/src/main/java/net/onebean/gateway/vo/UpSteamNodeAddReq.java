package net.onebean.gateway.vo;

import org.hibernate.validator.constraints.NotEmpty;

/**
* @author Icc
* @description upsteam node info model
* @date 2019-03-01 15:25:32
*/
public class UpSteamNodeAddReq {

        /**
        * 节点名称
        */
        @NotEmpty(message = "nodeName can not be empty")
        private String nodeName;
        public String getNodeName(){
            return this.nodeName;
        }
        public void setNodeName(String nodeName){
            this.nodeName = nodeName;
        }

        /**
         *物理地址
         */
        private String filePath;
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
        public String getProgramType() {
            return programType;
        }
        public void setProgramType(String programType) {
            this.programType = programType;
        }


        /**
        * 物理地址
        */
        private String physicalPath;
        public String getPhysicalPath(){
            return this.physicalPath;
        }
        public void setPhysicalPath(String physicalPath){
            this.physicalPath = physicalPath;
        }


        /**
         * 部署类型 0:物理地址部署 1:kubernetes部署
         */
        @NotEmpty(message = "deployType can not be empty")
        private String deployType;
        public String getDeployType(){
            return this.deployType;
        }
        public void setDeployType(String deployType){
            this.deployType = deployType;
        }



        /**
         * 节点命名空间
         */
        private String nodeNamespace;
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
        public String getCurrentVersion(){
            return this.currentVersion;
        }
        public void setCurrentVersion(String currentVersion){
            this.currentVersion = currentVersion;
        }


        /**
         * 运行状态，0运行中，1已停止
         */
        private String runningStatus;
        public String getRunningStatus(){
            return this.runningStatus;
        }
        public void setRunningStatus(String runningStatus){
            this.runningStatus = runningStatus;
        }


        private Integer operatorId;
        public Integer getOperatorId(){
            return this.operatorId;
        }
        public void setOperatorId(Integer operatorId){
            this.operatorId = operatorId;
        }



        private String operatorName;
        public String getOperatorName(){
            return this.operatorName;
        }
        public void setOperatorName(String operatorName){
            this.operatorName = operatorName;
        }


}