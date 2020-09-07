package net.onebean.gateway.vo;

public class DevOpsK8sNotificationVo {

    private String nodeName;
    private String currentVersion;
    private String nodeNamespace;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getNodeNamespace() {
        return nodeNamespace;
    }

    public void setNodeNamespace(String nodeNamespace) {
        this.nodeNamespace = nodeNamespace;
    }

}
