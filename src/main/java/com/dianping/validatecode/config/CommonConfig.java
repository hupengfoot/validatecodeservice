package com.dianping.validatecode.config;

import java.util.Map;

import com.dianping.validatecode.config.CommonConfig;
import com.dianping.validatecode.config.ConfigChangedEvent;
import com.dianping.validatecode.config.DynamicConfigable;
import com.dianping.validatecode.constant.ConfigKey;
import com.dianping.validatecode.utils.StringUtils;

public class CommonConfig implements DynamicConfigable {

    private static final CommonConfig instance                          = new CommonConfig();

    private static final String       DEFAULT_EXTERNAL_VALIDATE_URL     = "http://10.1.1.156/captcha?ip=";

    private static final String       DEFAULT_CLIENT_URL_VALIDATECODE   = "/deny.code";
    private static final String       DEFAULT_CLIENT_URL_STATUS         = "/dianping.firewall.client.status";
    private static final String       DEFAULT_CLIENT_URL_ENABLE         = "/dianping.firewall.client.enable";
    private static final String       DEFAULT_CLIENT_URL_DISABLE        = "/dianping.firewall.client.disable";
    private static final boolean      DEFAULT_CLIENT_HEARTBEAT_ENABLE   = false;
    private static final int          DEFAULT_CLIENT_HEARTBEAT_INTERVAL = 180;

    private static final String       DEFAULT_SERVER_URL_HEARTBEAT      = "/clientManagement/dianping.firewall.server.heartbeat";
    private static final String       DEFAULT_SERVER_URL_RELEASE        = "/clientManagement/dianping.firewall.server.release";
    private static final String       DEFAULT_SERVER_ROOT               = "http://192.168.26.38:8080";
    private static final String       DEFAULT_SERVER_URL_BLOCK_EVENT    = "/clientManagement/dianping.firewall.server.blockevent";
    private static final String       DEFAULT_SERVER_URL_ADD_HIPPO      = "/hippoReceiver/dianping.firewall.server.addhippo";

    private volatile String           externalValidateUrl               = DEFAULT_EXTERNAL_VALIDATE_URL;
    private volatile String           clientValidateCodeUrl             = DEFAULT_CLIENT_URL_VALIDATECODE;
    private volatile String           clientStatusUrl                   = DEFAULT_CLIENT_URL_STATUS;
    private volatile String           clientEnableUrl                   = DEFAULT_CLIENT_URL_ENABLE;
    private volatile String           clientDisableUrl                  = DEFAULT_CLIENT_URL_DISABLE;
    private volatile boolean          clientHeartbeatEnable             = DEFAULT_CLIENT_HEARTBEAT_ENABLE;
    private volatile int              clientHeartbeatInterval           = DEFAULT_CLIENT_HEARTBEAT_INTERVAL;
    private volatile String           serverHeartbeatUrl                = DEFAULT_SERVER_URL_HEARTBEAT;
    private volatile String           serverReleaseUrl                  = DEFAULT_SERVER_URL_RELEASE;
    private volatile String           serverRoot                        = DEFAULT_SERVER_ROOT;
    private volatile String           serverBlockEventUrl               = DEFAULT_SERVER_URL_BLOCK_EVENT;
    private volatile String           serverAddHippoUrl                 = DEFAULT_SERVER_URL_ADD_HIPPO;

    private CommonConfig() {

    }

    public static CommonConfig getInstance() {
        return instance;
    }

    public String getExternalValidateUrl() {
        return externalValidateUrl;
    }

    public String getClientValidateCodeUrl() {
        return clientValidateCodeUrl;
    }

    public String getClientStatusUrl() {
        return clientStatusUrl;
    }

    public String getClientEnableUrl() {
        return clientEnableUrl;
    }

    public String getClientDisableUrl() {
        return clientDisableUrl;
    }

    public String getServerHeartbeatUrl() {
        return serverHeartbeatUrl;
    }

    public boolean isClientHeartbeatEnable() {
        return clientHeartbeatEnable;
    }

    public int getClientHeartbeatInterval() {
        return clientHeartbeatInterval;
    }

    public String getServerReleaseUrl() {
        return serverReleaseUrl;
    }

    public String getServerRoot() {
        return serverRoot;
    }

    /**
     * @return the serverBlockEventUrl
     */
    public String getServerBlockEventUrl() {
        return serverBlockEventUrl;
    }
    
    /**
     * @return the serverAddHippoUrl
     */
    public String getServerAddHippoUrl() {
      return serverAddHippoUrl;
   }

   /*
     * (non-Javadoc)
     * 
     * @see
     * com.dianping.alpaca.client.config.DynamicConfigable#init(java.util.Map)
     */
    public void init(Map<ConfigKey, Object> keyValueMap) {
        if (keyValueMap != null) {
            for (Map.Entry<ConfigKey, Object> entry : keyValueMap.entrySet()) {
                propertySet(entry.getKey(), (String) entry.getValue());
            }

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dianping.alpaca.client.config.DynamicConfigable#onConfigChanged(com
     * .dianping.alpaca.client.config.ConfigChangedEvent)
     */
    public void onConfigChanged(ConfigChangedEvent event) {
        if (event != null) {
            propertySet(event.getKey(), event.getValue());
        }
    }

    private void propertySet(ConfigKey key, String value) {
        if (ConfigKey.EXTERNAL_VALIDATECODE_URL.equals(key)) {
            externalValidateUrl = value == null ? DEFAULT_EXTERNAL_VALIDATE_URL : value;
        } else if (ConfigKey.URL_CLIENT_DISABLE.equals(key)) {
            clientDisableUrl = value == null ? DEFAULT_CLIENT_URL_DISABLE : value;
        } else if (ConfigKey.URL_CLIENT_ENABLE.equals(key)) {
            clientEnableUrl = value == null ? DEFAULT_CLIENT_URL_ENABLE : value;
        } else if (ConfigKey.URL_CLIENT_STATUS.equals(key)) {
            clientStatusUrl = value == null ? DEFAULT_CLIENT_URL_STATUS : value;
        } else if (ConfigKey.URL_CLIENT_VALIDATECODE.equals(key)) {
            clientValidateCodeUrl = value == null ? DEFAULT_CLIENT_URL_VALIDATECODE : value;
        } else if (ConfigKey.URL_SERVER_HEARTBEAT.equals(key)) {
            serverHeartbeatUrl = value == null ? DEFAULT_SERVER_URL_HEARTBEAT : value;
        } else if (ConfigKey.URL_SERVER_ROOT.equals(key)) {
            serverRoot = value == null ? DEFAULT_SERVER_ROOT : value;
        } else if (ConfigKey.URL_SERVER_BLOCK_EVENT_NOTIFY.equals(key)) {
            serverBlockEventUrl = value == null ? DEFAULT_SERVER_URL_BLOCK_EVENT : value;
        } else if (ConfigKey.URL_SERVER_ADD_HIPPO.equals(key)){
           serverAddHippoUrl = value == null ? DEFAULT_SERVER_URL_ADD_HIPPO : value;
        } else if (ConfigKey.SWITCH_HEARTBEAT_ENABLE.equals(key)) {
            if (value == null) {
                clientHeartbeatEnable = false;
            } else if ("false".equalsIgnoreCase(value)) {
                clientHeartbeatEnable = false;
            } else if ("true".equalsIgnoreCase(value)) {
                clientHeartbeatEnable = true;
            } else {
                clientHeartbeatEnable = false;
            }
        } else if (ConfigKey.VALUE_CLIENT_HEARTBEAT_INTERVAL.equals(key)) {
            if (value == null) {
                clientHeartbeatInterval = DEFAULT_CLIENT_HEARTBEAT_INTERVAL;
            } else {
                if (StringUtils.isNumeric(value)) {
                    clientHeartbeatInterval = Integer.parseInt(value);
                } else {
                    clientHeartbeatInterval = DEFAULT_CLIENT_HEARTBEAT_INTERVAL;
                }
            }
        } else if (ConfigKey.URL_SERVER_RELEASE.equals(key)) {
            serverReleaseUrl = value == null ? DEFAULT_SERVER_URL_RELEASE : value;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dianping.alpaca.client.config.DynamicConfigable#prepare(java.util
     * .Map)
     */
    public void prepare(Map<ConfigKey, Object> keyDefaultValueMap) {
        if (keyDefaultValueMap != null) {
            keyDefaultValueMap.put(ConfigKey.EXTERNAL_VALIDATECODE_URL, DEFAULT_EXTERNAL_VALIDATE_URL);
            keyDefaultValueMap.put(ConfigKey.URL_CLIENT_VALIDATECODE, DEFAULT_CLIENT_URL_VALIDATECODE);
            keyDefaultValueMap.put(ConfigKey.URL_CLIENT_STATUS, DEFAULT_CLIENT_URL_STATUS);
            keyDefaultValueMap.put(ConfigKey.URL_CLIENT_ENABLE, DEFAULT_CLIENT_URL_ENABLE);
            keyDefaultValueMap.put(ConfigKey.URL_CLIENT_DISABLE, DEFAULT_CLIENT_URL_DISABLE);
            keyDefaultValueMap.put(ConfigKey.URL_SERVER_HEARTBEAT, DEFAULT_SERVER_URL_HEARTBEAT);
            keyDefaultValueMap
                    .put(ConfigKey.SWITCH_HEARTBEAT_ENABLE, Boolean.toString(DEFAULT_CLIENT_HEARTBEAT_ENABLE));
            keyDefaultValueMap.put(ConfigKey.URL_SERVER_RELEASE, DEFAULT_SERVER_URL_RELEASE);
            keyDefaultValueMap.put(ConfigKey.URL_SERVER_ROOT, DEFAULT_SERVER_ROOT);
            keyDefaultValueMap.put(ConfigKey.URL_SERVER_BLOCK_EVENT_NOTIFY, DEFAULT_SERVER_URL_BLOCK_EVENT);
            keyDefaultValueMap.put(ConfigKey.URL_SERVER_ADD_HIPPO, DEFAULT_SERVER_URL_ADD_HIPPO);
            keyDefaultValueMap.put(ConfigKey.VALUE_CLIENT_HEARTBEAT_INTERVAL, Integer
                    .toString(DEFAULT_CLIENT_HEARTBEAT_INTERVAL));
        }
    }
}
