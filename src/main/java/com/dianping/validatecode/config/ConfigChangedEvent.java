package com.dianping.validatecode.config;

import java.io.Serializable;

import com.dianping.validatecode.constant.ConfigKey;



public class ConfigChangedEvent implements Serializable {
    private static final long serialVersionUID = -8691634678328369255L;
    private ConfigKey         key;
    private String            value;

    public ConfigChangedEvent() {
        super();
    }

    public ConfigChangedEvent(ConfigKey key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public ConfigKey getKey() {
        return key;
    }

    public void setKey(ConfigKey key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
