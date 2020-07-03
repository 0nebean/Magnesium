package net.onebean.api.mngt.vo;

public class ServerHostNodeVo {

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

    /*是否开启ssl*/
    private String isSsl;
    public String getIsSsl() {
        return isSsl;
    }
    public void setIsSsl(String isSsl) {
        this.isSsl = isSsl;
    }

    /*监听端口号*/
    private String listenPort;
    public String getListenPort() {
        return listenPort;
    }
    public void setListenPort(String listenPort) {
        this.listenPort = listenPort;
    }

    /*ssl监听端口号*/
    private String sslListenPort;
    public String getSslListenPort() {
        return sslListenPort;
    }
    public void setSslListenPort(String sslListenPort) {
        this.sslListenPort = sslListenPort;
    }

    /*域名*/
    private String serverHost;
    public String getServerHost() {
        return serverHost;
    }
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }


    /*证书key路径*/
    private String sslCrtKeyPath;
    public String getSslCrtKeyPath() {
        return sslCrtKeyPath;
    }
    public void setSslCrtKeyPath(String sslCrtKeyPath) {
        this.sslCrtKeyPath = sslCrtKeyPath;
    }

    /*证书路径*/
    private String sslCrtPath;
    public String getSslCrtPath() {
        return sslCrtPath;
    }
    public void setSslCrtPath(String sslCrtPath) {
        this.sslCrtPath = sslCrtPath;
    }


}
