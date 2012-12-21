package com.dianping.validatecode.constant;

import java.util.ArrayList;
import java.util.List;

public enum ConfigKey {
    POLICY_ACCEPT_IP_PREFIX("policy.acceptIPPrefix"), 
    POLICY_ACCEPT_HTTP_METHOD("policy.acceptHttpMethod"), 
    POLICY_DENY_USERAGENT("policy.denyUserAgent"), 
    POLICY_DENY_USERAGENT_PREFIX("policy.denyUserAgentPrefix"), 
    POLICY_DENY_IP("policy.denyIPAddress"), 
    POLICY_DENY_IP_PREFIX("policy.denyIPAddressPrefix"), 
    POLICY_DENY_IP_RATE("policy.denyIPAddressRate"), 
    POLICY_DENY_USERAGENT_CONTAIN_AND("policy.denyUserAgentContainAnd"), 
    POLICY_DENY_IP_VID_RATE("policy.denyIPVidRate"),
    POLICY_DENY_NO_VISITORID_URL("policy.denyNoVisitorIdURL"),
    POLICY_DENY_NO_VISITORID_URL_NEW("policy.denyNoVisitorIdURL.new"),
    
    SWITCH_FILTER_ENABLE("filter.enable"), 
    SWITCH_HEARTBEAT_ENABLE("client.clientHeartbeatEnable"), 
    SWITCH_FILTER_PUSH_BLOCK_EVENT("filter.pushBlockEvent"),
    // filter是否挂载，这个开关是最大的开关，如果为false则整个filter功能都关闭
    SWITCH_FILTER_MOUNT("filter.mount"),
    SWITCH_FILTER_BLOCKBYVID("filter.blockByVid"),
    
    MESSAGE_DENY("message.deny"), 
    MESSAGE_DENYRATE("message.denyrate"), 
    
    EXTERNAL_VALIDATECODE_URL("externel.validatecode.url"), 
    
    URL_CLIENT_VALIDATECODE("url.clientValidateCodeUrl"), 
    URL_CLIENT_STATUS("url.clientStatusUrl"), 
    URL_CLIENT_ENABLE("url.clientEnableUrl"), 
    URL_CLIENT_DISABLE("url.clientDisableUrl"), 
    URL_SERVER_HEARTBEAT("url.serverHeartbeatUrl"), 
    URL_SERVER_RELEASE("url.serverReleaseUrl"), 
    URL_SERVER_ROOT("url.serverRootUrl"),
    URL_SERVER_BLOCK_EVENT_NOTIFY("url.serverBlockEventNotifyUrl"),
    URL_SERVER_ADD_HIPPO("url.serverAddHippoUrl"),
    
    VALUE_CLIENT_HEARTBEAT_INTERVAL("client.heartbeat.interval"),
    
    HIPPO_CHECK_IGNORE_PROJECTS("hippocheck.ingoreprojects"),
    HIPPO_CHECK_ENABLE("hippocheck.enable");
    
    private String value;

    private ConfigKey(String value) {
        this.value = "alpaca." + value;
    }

    public String value() {
        return value;
    }

    public static ConfigKey getConfigKey(String value) {
        for (ConfigKey key : ConfigKey.values()) {
            if (key.value.equals(value)) {
                return key;
            }
        }
        return null;
    }

    public static List<String> strValues() {
        List<String> result = new ArrayList<String>();
        for (ConfigKey key : ConfigKey.values()) {
            result.add(key.value);
        }
        return result;
    }

}

