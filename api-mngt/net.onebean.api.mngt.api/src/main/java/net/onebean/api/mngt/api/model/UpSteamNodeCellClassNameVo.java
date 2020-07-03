package net.onebean.api.mngt.api.model;

/**
* @author 0neBean
* @description upsteam node info model
* @date 2019-03-01 15:25:32
*/
public class UpSteamNodeCellClassNameVo {

        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }



        /**
         * 节点名称
         */
        private String nodeName;
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
         * 部署类型 0:物理地址部署 1:kubernetes部署
         */
        private String deployType;
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
        public String getRunningStatus(){
            return this.runningStatus;
        }
        public void setRunningStatus(String runningStatus){
            this.runningStatus = runningStatus;
        }


        /**
         * 创建时间
         */
        private String createTime;
        public String getCreateTime(){
            return this.createTime;
        }
        public void setCreateTime(String createTime){
            this.createTime = createTime;
        }


        /**
         * 更新时间
         */
        private String updateTime;
        public String getUpdateTime(){
            return this.updateTime;
        }
        public void setUpdateTime(String updateTime){
            this.updateTime = updateTime;
        }


        /**
         * 操作人ID
         */
        private String operatorId;
        public String getOperatorId(){
            return this.operatorId;
        }
        public void setOperatorId(String operatorId){
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