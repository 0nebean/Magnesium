package net.onebean.api.mngt.api.model.enumModel;

public enum RunningStatusEnum {

    //运行状态，0运行中，1已停止
    RUNNING("0", "运行中"),
    TERMINATED("1", "已停止"),
            ;

    RunningStatusEnum() {
    }

    private RunningStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    private String key;
    private String value;

    public static String getKeyByValue(String value) {
        for (RunningStatusEnum s : RunningStatusEnum.values()) {
            if (s.getValue().equals(value)) {
                return s.getKey();
            }
        }
        return "";
    }

    public static String getValueByKey(String key) {
        for (RunningStatusEnum s : RunningStatusEnum.values()) {
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