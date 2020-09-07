package net.onebean.gateway.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class SetRunningStatusDown {

    @NotEmpty(message = "id can not be empty")
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
    @NotEmpty(message = "nodeName can not be empty")
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
    @NotEmpty(message = "nodeNamespace can not be empty")
    private String nodeNamespace;
    public String getNodeNamespace(){
        return this.nodeNamespace;
    }
    public void setNodeNamespace(String nodeNamespace){
        this.nodeNamespace = nodeNamespace;
    }

    /**
     * 当前版本
     */
    @NotEmpty(message = "currentVersion can not be empty")
    private String currentVersion;
    public String getCurrentVersion(){
        return this.currentVersion;
    }
    public void setCurrentVersion(String currentVersion){
        this.currentVersion = currentVersion;
    }

}
