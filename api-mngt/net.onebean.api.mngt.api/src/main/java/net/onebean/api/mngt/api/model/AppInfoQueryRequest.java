package net.onebean.api.mngt.api.model;

/**
* @author 0neBean
* @description 应用信息 model
* @date 2019-01-03 16:14:09
*/
public class AppInfoQueryRequest {

        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        private String appName;
        public String getAppName(){
            return this.appName;
        }
        public void setAppName(String appName){
            this.appName = appName;
        }


        private String appStatus;
        public String getAppStatus(){
            return this.appStatus;
        }
        public void setAppStatus(String appStatus){
            this.appStatus = appStatus;
        }


        private String authType;
        public String getAuthType() {
            return authType;
        }
        public void setAuthType(String authType) {
            this.authType = authType;
        }
}