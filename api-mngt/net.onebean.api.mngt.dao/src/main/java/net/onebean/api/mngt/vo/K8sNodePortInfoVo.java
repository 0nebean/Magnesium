package net.onebean.api.mngt.vo;

public class K8sNodePortInfoVo {


    public K8sNodePortInfoVo() {
    }

    private String clusterip;
    private String nodePort;

    public String getClusterip() {
        return clusterip;
    }

    public void setClusterip(String clusterip) {
        this.clusterip = clusterip;
    }

    public String getNodePort() {
        return nodePort;
    }

    public void setNodePort(String nodePort) {
        this.nodePort = nodePort;
    }
}
