package net.onebean.gateway.enumModel;

public enum  DeployTypeEnum {

    //部署类型 0:物理地址部署 1:kubernetes部署
    PHYSICAL("0", "物理地址部署"),
    KUBERNETES("1", "kubernetes部署"),
            ;

    DeployTypeEnum() {
    }

    private DeployTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (DeployTypeEnum s : DeployTypeEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (DeployTypeEnum s : DeployTypeEnum.values()) {
            if (s.getKey().equals(key)) {
                return s.getValue();
            }
        }
        return "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}